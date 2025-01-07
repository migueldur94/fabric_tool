package dev.mike.tool.validation;

import java.util.List;

public class WrongValuesException extends RuntimeException {
    private final List<String> messages;

    public WrongValuesException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
