package j2q.setup.controller;

import j2q.setup.definition.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definition.dto.AutoNumberingDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

public sealed interface IAutoNumbering permits IJ2DataExtensions, RestAutoNumberingController {
    List<AutoNumberingDTO> getAutoNumberingList(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) throws SQLException;
    boolean insertAutoNumberingBulk(List<AutoNumberingDTO> insertRows) throws SQLException;
    boolean cleanAutoNumberingTable() throws SQLException;
}
