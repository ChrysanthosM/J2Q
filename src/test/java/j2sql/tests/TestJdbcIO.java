package j2sql.tests;

import j2sql.J2SQL;
import j2sql.db.JdbcIO;
import j2sql.db.datasources.DataSourceForSQLite;
import j2sql.db.loader.DbRecord;
import j2sql.definitions.design.schema.tables.TAutoNumbering;
import j2sql.definitions.dtos.AutoNumberingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestJdbcIO {
    private @Autowired DataSourceForSQLite sqliteDataSource;
    private @Autowired JdbcIO jdbcIO;
    private @Autowired TAutoNumbering tAutoNumbering;

    @Test
    public void testData() {
        long startTime = System.currentTimeMillis();
        try {
            testDataMain();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        long durationTime = System.currentTimeMillis() - startTime;
        System.out.println("Test finished in " + durationTime + " ms");
    }
    private void testDataMain() throws SQLException {
        String stmt = J2SQL.create(sqliteDataSource).from(tAutoNumbering).getSQL();

        List<AutoNumberingDTO> listSample = jdbcIO.select(sqliteDataSource.getDS(), AutoNumberingDTO.newConverter(), stmt);
        listSample.forEach(System.out::println);

        Map<Integer, DbRecord> dbRecords = jdbcIO.selectRecords(sqliteDataSource.getDS(), stmt);
        dbRecords.forEach((key, value) -> System.out.println(key.toString() + " " + value.toString()));

        listSample = jdbcIO.selectAsync(sqliteDataSource.getDS(), AutoNumberingDTO.newConverter(), stmt);
        listSample.forEach(System.out::println);

    }
}
