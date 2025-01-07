/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.mike.infrastructure.authentication.repository;

import dev.mike.core.configuration.Configuration;
import dev.mike.infrastructure.store.ExecuteQuery;
import dev.mike.infrastructure.store.IResultQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class ConfigurationRepository {

    private final ExecuteQuery databaseConnection;

    public ConfigurationRepository(ExecuteQuery databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Configuration getConfiguration() {
        return databaseConnection.execute(new IResultQuery() {
            @Override
            public Configuration execute(Statement statement) throws SQLException {
                ResultSet rs = statement.executeQuery("SELECT * FROM configuration;");
                while (rs.next()) {
                    return new Configuration(rs.getString("pathJar"),rs.getString("pathProject"), rs.getString("serverDomain"));
                }
                return null;
            }
        });
    }

    public void updateConfiguration(Configuration configuration) {
        databaseConnection.execute(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE configuration set pathJar = ?, pathProject = ?, serverDomain = ? where ID=1;");
            preparedStatement.setString(1, configuration.getPathJar());
            preparedStatement.setString(2, configuration.getPathProject());
            preparedStatement.setString(3, configuration.getServerDomain());

            preparedStatement.executeUpdate();
        });
    }
}
