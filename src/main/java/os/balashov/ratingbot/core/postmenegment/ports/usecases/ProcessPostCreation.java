package os.balashov.ratingbot.core.postmenegment.ports.usecases;

public interface ProcessPostCreation {
    void addPost(int messageId, long chatId, long userId, String text);
}
