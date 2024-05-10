package os.balashov.ratingbot.infrastructure.telegram.usecases;

public interface TextEditor {
    String updateMessage(String text);

    String updateMessage(String text, double rating);
}
