package main.java.ru.valya.serveback.dto.yandex;

import lombok.Data;
import ru.matveyakulov.markoservcomeback.UserRole;

import java.util.List;

@Data
public class CompletionRequest {

    private String modelUri;
    private CompletionOption completionOptions = new CompletionOption();
    private List<Message> messages;

    public CompletionRequest(String folderId, String text, UserRole userRole) {
        this.modelUri = String.format("gpt://%s/yandexgpt/rc", folderId);
        this.messages = List.of(new Message(userRole, text));
    }

    @Data
    public static class CompletionOption {
        private final int maxTokens = 500;
        private final double temperature = 0.3;
    }

    @Data
    public static class Message {
        private String role;
        private String text;

        public Message(UserRole role, String text) {
            this.role = role.getValue();
            this.text = text;
        }
    }
}
