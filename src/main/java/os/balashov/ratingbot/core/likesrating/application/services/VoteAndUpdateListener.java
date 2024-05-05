package os.balashov.ratingbot.core.likesrating.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import os.balashov.ratingbot.core.common.logging.Loggable;
import os.balashov.ratingbot.core.likesrating.application.usecases.ChangePostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.FindPostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SavePostRating;
import os.balashov.ratingbot.core.likesrating.application.usecases.SaveUserVote;
import os.balashov.ratingbot.core.likesrating.domain.ports.CalculateRating;
import os.balashov.ratingbot.core.likesrating.ports.ChatMemberCounter;
import os.balashov.ratingbot.core.likesrating.ports.CheckUserVote;
import os.balashov.ratingbot.core.likesrating.ports.CreatePostRating;
import os.balashov.ratingbot.core.likesrating.ports.ProcessVoteAndUpdate;
import os.balashov.ratingbot.core.likesrating.ports.dtos.Marks;
import os.balashov.ratingbot.core.likesrating.ports.dtos.PostRating;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteAndUpdateListener implements ProcessVoteAndUpdate {
    private final CheckUserVote checkUserVote;
    private final CalculateRating calculateRatingService;
    private final SaveUserVote saveUserVote;
    private final ChatMemberCounter chatMemberCounter;
    private final FindPostRating findPostRating;
    private final SavePostRating savePostRating;
    private final ChangePostRating changePostRating;
    private final CreatePostRating createPostRating;

    @Override
    @Loggable(message = "Application Service: Try to process vote and update for message {1} in chat {2} by user {3} with vote {4}, check if post rating exists")
    public double processVoteAndUpdate(int messageId, long chatId, long userId, Marks vote) {
        Optional<PostRating> postRatingOptional = findPostRating.find(messageId, chatId);
        if (postRatingOptional.isEmpty()) {
            PostRating postRating = createPostRating.createEmpty();
            return handleNewUserVote(messageId, chatId, userId, vote, postRating);
        }
        return processExistingPostRating(messageId, chatId, userId, vote, postRatingOptional.get());
    }

    @Loggable(message = "Application Service: Try to process existing post rating for message {1} in chat {2} by user {3} with vote {4}, check if user already voted")
    private double processExistingPostRating(int messageId, long chatId, long userId, Marks vote, PostRating postRating) {
        if (checkUserVote.isUserVoted(messageId, chatId, userId)) {
            return handleExistingUserVote(messageId, chatId, userId, vote, postRating);
        }
        return handleNewUserVote(messageId, chatId, userId, vote, postRating);
    }

    @Loggable(message = "Application Service: User {3} voted for message {1} again with data {4}, check if vote is changed")
    private double handleExistingUserVote(int messageId, long chatId, long userId, Marks vote, PostRating postRating) {
        if (checkUserVote.isVoteNotChanged(messageId, chatId, userId, vote)) {
            return postRating.rating();
        }
        postRating = changePostRating.swapRatingCounts(vote, postRating);
        return saveUserVoteAndCalculateRating(messageId, chatId, userId, vote, postRating);
    }

    @Loggable(message = "Application Service: User {3} firstly voted for message {1} with data {4}, handle new vote")
    private double handleNewUserVote(int messageId, long chatId, long userId, Marks vote, PostRating postRating) {
        postRating = changePostRating.incrementRatingCounts(vote, postRating);
        return saveUserVoteAndCalculateRating(messageId, chatId, userId, vote, postRating);
    }

    @Loggable(message = "Application Service: Save user vote and calculate rating for message {1} in chat {2} by user {3} with vote {4}")
    private double saveUserVoteAndCalculateRating(int messageId, long chatId, long userId, Marks vote, PostRating postRating) {
        saveUserVote.save(messageId, chatId, userId, vote);
        return computeAndPersistRating(messageId, chatId, postRating);
    }

    @Loggable(message = "Application Service: Try to compute and persist rating for message {1} in chat {2} by user {3} with vote {4}")
    private double computeAndPersistRating(int messageId, long chatId, PostRating postRating) {
        int chatMembers = chatMemberCounter.countChatMembers(chatId);
        double newRating = calculateRatingService.calculateRating(postRating.likes(), postRating.dislikes(), chatMembers);
        postRating = changePostRating.setNewRating(postRating, newRating);
        savePostRating.save(messageId, chatId, postRating);
        return newRating;
    }
}


