package dev.mike.infrastructure.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ExecuteQuery {


    public <T> T execute(IResultQuery executeQuery) {
        Object object = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:temenos.db");

            Statement statement = connection.createStatement();

            object = executeQuery.execute(statement);

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return (T) object;
    }

    public void execute(IExecuteQuery executeQuery) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:temenos.db");

            executeQuery.execute(connection);

            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

}
