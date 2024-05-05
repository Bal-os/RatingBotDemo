package os.balashov.ratingbot.infrastructure.telegram.services;

public interface TextEditor {
    String updateMessage(String text);

    String updateMessage(String text, double rating);
}
