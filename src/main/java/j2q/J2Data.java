package j2q;

import j2q.setup.controller.J2DataExtension1;
import j2q.setup.definition.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definition.dto.AutoNumberingDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public final class J2Data {
    private @Autowired J2DataExtension1 j2DataExtension1;

    public List<AutoNumberingDTO> getAutoNumberingList(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return j2DataExtension1.getAutoNumberingList(type);
    }

    public boolean insertAutoNumberingBulk(List<AutoNumberingDTO> insertRows) throws SQLException {
        if (CollectionUtils.isEmpty(insertRows)) return true;
        return j2DataExtension1.insertAutoNumberingBulk(insertRows);
    }
    public boolean cleanAutoNumberingTable() throws SQLException {
        return j2DataExtension1.cleanAutoNumberingTable();
    }
}
