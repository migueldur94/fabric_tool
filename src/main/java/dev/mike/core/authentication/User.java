package dev.mike.core.authentication;

import dev.mike.tool.validation.StringEmpty;
import dev.mike.tool.validation.ValidateGroup;

public class User {

    private final String user;
    private final String password;
    private final String token;
    private final String cookie;

    private User(String user, String password, String token, String cookie) {
        this.user = user;
        this.password = password;
        this.token = token;
        this.cookie = cookie;
    }

    public static User empty() {
        return new User(null, null, null, null);
    }

    public static User load(String user, String password, String token, String cookie) {
        ValidateGroup.validate(
                StringEmpty.isEmpty(user, "User must not be empty."),
                StringEmpty.isEmpty(password, "Password must not be empty.")
        );
        return new User(user, password, token, cookie);
    }

    public static User authenticate(String user, String password) {
        ValidateGroup.validate(
                StringEmpty.isEmpty(user, "User must not be empty."),
                StringEmpty.isEmpty(password, "Password must not be empty.")
        );
        return new User(user, password, null, null);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getCookie() {
        return cookie;
    }
}
