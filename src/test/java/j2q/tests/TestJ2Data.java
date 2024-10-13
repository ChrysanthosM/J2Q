package j2q.tests;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import j2q.setup.controllers.J2Data;
import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class TestJ2Data {
    private @Autowired J2Data j2Data;

    public void insertTest() throws SQLException {
        List<AutoNumberingDTO> insertRowsWithValues = Lists.newArrayList();
        for (int i = 0; i < 999; i++) {
            insertRowsWithValues.add(new AutoNumberingDTO(0, StringUtils.leftPad(String.valueOf(i), 3, '0'), i));
        }
        boolean inserted = j2Data.insertBulk(insertRowsWithValues);
    }

    public void deleteTest() throws SQLException {
        boolean cleaned = j2Data.cleanTable();
    }

    @Test
    public void testJ2Data() throws SQLException {
        deleteTest();
        insertTest();

        List<AutoNumberingDTO> listSample;

        listSample = j2Data.getAutoNumberingListAsync(AutoNumberingRepo.TypeOfSQL.ALL);
        listSample.forEach(System.out::println);

//        listSample = j2Data.getAutoNumberingList(AutoNumberingRepo.TypeOfSQL.ALL);
//        listSample.forEach(System.out::println);

    }
}
