package j2q.db.jdbc;

import com.google.common.collect.ImmutableList;
import j2q.db.datasources.WorkWithDataSource;
import j2q.db.loader.IRowLoader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@ThreadSafe
@Component
@Validated
public class JdbcIO {
    private @Autowired WorkWithDataSource workDataSource;

    public <T> List<T> select(@NotNull DataSource dataSource, @NotNull IRowLoader<T> rowLoader,
                              @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                List<T> returnList = new ArrayList<>(resultSet.getMetaData().getColumnCount());
                while (resultSet.next()) {
                    T pojo = rowLoader.convertResultSet(resultSet);
                    if (pojo != null) returnList.add(pojo);
                }
                return ImmutableList.copyOf(returnList);
            }
        }
    }

    public <T> Optional<T> selectOne(@NotNull DataSource dataSource, @NotNull IRowLoader<T> rowLoader,
                                     @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(rowLoader.convertResultSet(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Long> selectNumeric(@NotNull DataSource dataSource,
                                        @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getLong(1));
                }
            }
        }
        return Optional.empty();
    }

    @Transactional
    public int[] addBatch(@NotNull DataSource dataSource,
                          @NotNull String query, @Nullable List<List<Object>> params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            if (CollectionUtils.isNotEmpty(params)) {
                params.forEach(p -> {
                    try {
                        setParamsMain(stmt, p.toArray());
                        stmt.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return stmt.executeBatch();
        }
    }

    @Transactional
    public long[] addLargeBatch(@NotNull DataSource dataSource,
                                @NotNull String query, @Nullable List<List<Object>> params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            if (CollectionUtils.isNotEmpty(params)) {
                params.forEach(p -> {
                    try {
                        setParamsMain(stmt, p.toArray());
                        stmt.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return stmt.executeLargeBatch();
        }
    }


    @Transactional
    public boolean executeQuery(@NotNull DataSource dataSource,
                                @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);
            return stmt.execute();
        }
    }

    @Transactional
    public int executeUpdate(@NotNull DataSource dataSource,
                             @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);
            return stmt.executeUpdate();
        }
    }
    @Transactional
    public long executeLargeUpdate(@NotNull DataSource dataSource,
                                   @NotNull String query, @Nullable Object... params) throws SQLException {
        try (Connection conn = workDataSource.getDefaultDataSourceProvider().getDS().getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            setParamsMain(stmt, params);
            return stmt.executeLargeUpdate();
        }
    }

    private void setParams(AddBatchInfo addBatchInfo, PreparedStatement stmt) throws SQLException {
        for (List<Object> params : Objects.requireNonNull(addBatchInfo.params())) {
            setParamsMain(stmt, params.toArray());
            stmt.addBatch();
        }
    }
    private void setParamsMain(PreparedStatement toStmt, Object... params) throws SQLException {
        if (ArrayUtils.isNotEmpty(params)) {
            int i = 1;
            for (Object param : params) {
                if (param == null) throw new SQLException("Null Param not allowed");
                toStmt.setObject(i++, param);
            }
        }
    }
}

