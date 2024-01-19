package j2q.tests;

import j2q.setup.controllers.J2Data;
import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class TestJ2Data {
    private @Autowired J2Data j2Data;

    @Test
    public void testJ2Data() throws SQLException {
        List<AutoNumberingDTO> listSample;

        listSample = j2Data.getAutoNumberingListAsync(AutoNumberingRepo.TypeOfSQL.ALL);
        listSample.forEach(System.out::println);

        listSample = j2Data.getAutoNumberingList(AutoNumberingRepo.TypeOfSQL.ALL);
        listSample.forEach(System.out::println);

    }
}
