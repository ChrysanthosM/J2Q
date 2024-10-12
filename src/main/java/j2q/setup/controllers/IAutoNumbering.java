package j2q.setup.controllers;

import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

public sealed interface IAutoNumbering permits IJ2DataExtensions, RestAutoNumberingController {
    List<AutoNumberingDTO> getAutoNumberingList(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) throws SQLException;
    List<AutoNumberingDTO> getAutoNumberingListAsync(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) throws SQLException;
}
