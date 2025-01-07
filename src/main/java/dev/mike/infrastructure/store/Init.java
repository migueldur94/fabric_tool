package dev.mike.infrastructure.store;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Init implements IExecuteQuery {

    @Override
    public void execute(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        createUserTable(statement);
        createConfigurationTable(statement);
    }

    private void createUserTable(Statement statement) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS user "
                + "(id INT PRIMARY KEY     NOT NULL,"
                + " user           CHAR(200), "
                + " password           CHAR(200), "
                + " token           CHAR(2000), "
                + " cookie       CHAR(500))";
        statement.executeUpdate(sql);

        ResultSet rs = statement.executeQuery("SELECT * FROM user;");
        if (rs.next()) {
            return;
        }

        statement.executeUpdate("INSERT INTO user (id,user,password, token, cookie) "
                + "VALUES (1, NULL, NULL, NULL, NULL);");
    }
    
    private void createConfigurationTable(Statement statement) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS configuration "
                + "(id INT PRIMARY KEY     NOT NULL,"
                + " pathJar           CHAR(500), "
                + " pathProject           CHAR(500), "
                + " serverDomain       CHAR(500))";
        statement.executeUpdate(sql);

        ResultSet rs = statement.executeQuery("SELECT * FROM configuration;");
        if (rs.next()) {
            return;
        }

        statement.executeUpdate("INSERT INTO configuration (id,pathJar,pathProject,serverDomain) "
                + "VALUES (1, NULL, NULL, NULL);");
    }
}
