package j2q.j2data.Services;

import j2q.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.definitions.design.repo.singles.AutoNumberingSQL;
import j2q.definitions.dtos.AutoNumberingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public final class AutoNumberingService extends AbstractService {
    @Autowired private AutoNumberingSQL autoNumberingSQL;

    public List<AutoNumberingDTO> getAutoNumberingList(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return getJdbcIO().select(getDefaultDataSource(), AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(type));
    }
    public List<AutoNumberingDTO> getAutoNumberingListAsync(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return getJdbcIO().selectAsync(getDefaultDataSource(), AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(type));
    }
}
