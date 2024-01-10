package j2q.tests;

import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.controllers.J2Data;
import j2q.core.face.J2SQL;
import j2q.db.JdbcIO;
import j2q.db.datasources.DataSourceForSQLite;
import j2q.setup.definitions.design.schema.tables.TAutoNumbering;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class TestJdbcIO {
    private @Autowired J2Data j2Data;

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

        listSample = jdbcIO.selectAsync(sqliteDataSource.getDS(), AutoNumberingDTO.newConverter(), stmt);
        listSample.forEach(System.out::println);
    }

    @Test
    public void testJ2Data() throws SQLException {
        List<AutoNumberingDTO> listSample = j2Data.getAutoNumberingList(AutoNumberingRepo.TypeOfSQL.ALL);
        listSample.forEach(System.out::println);
        listSample = j2Data.getAutoNumberingListAsync(AutoNumberingRepo.TypeOfSQL.ALL);
        listSample.forEach(System.out::println);


    }
}
