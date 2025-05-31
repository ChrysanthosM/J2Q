package j2q.tests;

import com.google.common.collect.Lists;
import j2q.J2Data;
import j2q.setup.definition.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definition.dto.AutoNumberingDTO;
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
        int intValue = 1;
        for (char firstLetter = 'A'; firstLetter <= 'Z'; firstLetter++) {
            for (char secondLetter = 'A'; secondLetter <= 'Z'; secondLetter++) {
                for (char thirdLetter = 'A'; thirdLetter <= 'Z'; thirdLetter++) {
                        String combination = StringUtils.join(firstLetter, secondLetter, thirdLetter);
                        insertRowsWithValues.add(new AutoNumberingDTO(0, combination, intValue++));
                }
            }
        }
        boolean inserted = j2Data.insertAutoNumberingBulk(insertRowsWithValues);
    }

    public void deleteTest() throws SQLException {
        boolean cleaned = j2Data.cleanAutoNumberingTable();
    }

    @Test
    public void testJ2Data() throws SQLException {
        long startTime, durationTime;
        List<AutoNumberingDTO> listSample;

        deleteTest();
        insertTest();

        startTime = System.currentTimeMillis();
        listSample = j2Data.getAutoNumberingList(AutoNumberingRepo.TypeOfSQL.LIST);
        durationTime = System.currentTimeMillis() - startTime;
        System.out.println("getAutoNumberingList finished in " + durationTime + " ms");
//        listSample.forEach(System.out::println);

        deleteTest();
    }
}
