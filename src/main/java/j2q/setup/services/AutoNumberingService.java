package j2q.setup.services;

import com.google.common.collect.Lists;
import j2q.core.AbstractService;
import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.design.repo.singles.AutoNumberingSQL;
import j2q.setup.definitions.design.schema.tables.TAutoNumbering;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public final class AutoNumberingService extends AbstractService<AutoNumberingDTO> {
    private @Autowired AutoNumberingSQL autoNumberingSQL;
    private @Autowired TAutoNumbering tAutoNumbering;

    public List<AutoNumberingDTO> getList(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return getJdbcIO().select(AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(type));
    }

    public boolean insertBulk(List<AutoNumberingDTO> insertRows) throws SQLException {
        final String query = autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.INSERT);
        final ImmutablePair<String, List<Object>> queryAndValues = getBulkInsertQueryAndValues(query, Lists.newArrayList(insertRows), tAutoNumbering);
        return getJdbcIO().executeQuery(queryAndValues.left, queryAndValues.right.toArray());
    }

    public boolean cleanTable() throws SQLException {
        return getJdbcIO().executeQuery(autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.DELETE_ALL));
    }

}
