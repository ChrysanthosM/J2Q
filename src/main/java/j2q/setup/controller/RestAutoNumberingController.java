package j2q.setup.controller;

import j2q.J2Data;
import j2q.setup.definition.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definition.dto.AutoNumberingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public final class RestAutoNumberingController implements IAutoNumbering {
    private @Autowired J2Data j2Data;

    @GetMapping("/AutoNumberingList") //localhost:8080/AutoNumberingList?type=ALL
    @Override public List<AutoNumberingDTO> getAutoNumberingList(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return j2Data.getAutoNumberingList(type);
    }


    @GetMapping("/insertBulk")
    @Override
    public boolean insertAutoNumberingBulk(List<AutoNumberingDTO> insertRows) throws SQLException {
        return j2Data.insertAutoNumberingBulk(insertRows);
    }

    @GetMapping("/cleanTable")
    @Override
    public boolean cleanAutoNumberingTable() throws SQLException {
        return j2Data.cleanAutoNumberingTable();
    }

}
