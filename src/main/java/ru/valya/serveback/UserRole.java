package main.java.ru.valya.serveback;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String value;
}
