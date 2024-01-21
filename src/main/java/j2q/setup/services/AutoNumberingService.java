package j2q.setup.services;

import j2q.core.bridge.AbstractService;
import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.design.repo.singles.AutoNumberingSQL;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public final class AutoNumberingService extends AbstractService<AutoNumberingDTO> {
    private @Autowired AutoNumberingSQL autoNumberingSQL;

    public List<AutoNumberingDTO> getAutoNumberingList(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(type));
    }
    public List<AutoNumberingDTO> getAutoNumberingListAsync(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return getJdbcIO().selectAsync(getDefaultDataSource(), AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(type));
    }

}
