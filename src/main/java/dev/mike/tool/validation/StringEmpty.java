package dev.mike.tool.validation;

public class StringEmpty implements Validator {

    private final String value;
    private final String message;

    public StringEmpty(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public static StringEmpty isEmpty(String value, String message) {
        return new StringEmpty(value, message);
    }


    @Override
    public boolean isWrong() {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
