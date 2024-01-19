package j2q.setup.controllers;

import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public final class RestAutoNumberingController implements IAutoNumbering {
    @Autowired private J2Data j2Data;

    @GetMapping("/AutoNumberingList") //localhost:8080/AutoNumberingList?type=ALL
    @Override public List<AutoNumberingDTO> getAutoNumberingList(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return j2Data.getAutoNumberingList(type);
    }
    @GetMapping("/AutoNumberingListAsync") //localhost:8080/AutoNumberingListAsync?type=ALL
    @Override public List<AutoNumberingDTO> getAutoNumberingListAsync(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return j2Data.getAutoNumberingListAsync(type);
    }

}
