package j2q.setup.definitions.design.repo.singles;

import j2q.core.j2sql.J2SQL;
import j2q.core.bridge.LoadJ2SQL;
import j2q.setup.definitions.design.schema.tables.TOptions;
import j2q.core.bridge.AbstractJ2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OptionsJ2SQL extends AbstractJ2<OptionsRepo.TypeOfSQL> implements OptionsRepo {
    private @Autowired TOptions tOptions;

    private OptionsJ2SQL() {
        super(TypeOfSQL.class);
    }

    @LoadJ2SQL public void loadALL() {
        addLoader(TypeOfSQL.ALL, J2SQL.create(getDefaultDataSource()).from(tOptions));
    }
    @LoadJ2SQL public void loadINSERT_ROW() {
        addLoader(TypeOfSQL.INSERT_ROW, J2SQL.create(getDefaultDataSource()).insertInto(tOptions).insertRow());
    }
    @LoadJ2SQL public void loadSPECIFIC_OPTION_TYPE() {
        addLoader(TypeOfSQL.SPECIFIC_OPTION_TYPE, J2SQL.create(getDefaultDataSource()).from(tOptions).where(tOptions.OPTION_TYPE.eq("?")));
    }
}
