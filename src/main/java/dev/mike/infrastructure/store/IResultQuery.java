package dev.mike.infrastructure.store;

import java.sql.SQLException;
import java.sql.Statement;

public interface IResultQuery {
    <T> T execute(Statement statement) throws SQLException;
}
