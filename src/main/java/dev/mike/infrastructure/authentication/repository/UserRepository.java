package dev.mike.infrastructure.authentication.repository;

import dev.mike.core.authentication.User;
import dev.mike.infrastructure.store.ExecuteQuery;
import dev.mike.infrastructure.store.IResultQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository {

    private final ExecuteQuery databaseConnection;

    public UserRepository(ExecuteQuery databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public User getUser() {
        return databaseConnection.execute(new IResultQuery() {
            @Override
            public User execute(Statement statement) throws SQLException {
                ResultSet rs = statement.executeQuery("SELECT * FROM user;");
                while (rs.next()) {
                    String user = rs.getString("user");
                    if (user == null) {
                        return User.empty();
                    }
                    return User.load(user, rs.getString("password"), rs.getString("token"), rs.getString("cookie"));
                }
                return null;
            }
        });
    }

    public void updateUser(User user) {
        databaseConnection.execute(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user set user = ?, password = ? where ID=1;");
            preparedStatement.setString(1, user.getUser());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();
        });
    }

    public void updateToken(String token, String cookie) {
        databaseConnection.execute(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user set token = ?, cookie = ? where ID=1;");
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, cookie);

            preparedStatement.executeUpdate();
        });
    }

}
