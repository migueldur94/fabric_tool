package dev.mike.infrastructure.store;

import java.sql.Connection;
import java.sql.SQLException;

public interface IExecuteQuery {
    void execute(Connection statement) throws SQLException;
}
