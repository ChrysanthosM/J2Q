package j2q.setup.definition.design.repo.singles;

import j2q.core.J2SQL;
import j2q.core.LoadJ2SQL;
import j2q.setup.definition.design.schema.table.TOptions;
import j2q.core.AbstractJ2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OptionsJ2SQL extends AbstractJ2<OptionsRepo.TypeOfSQL> implements OptionsRepo {
    private @Autowired TOptions tOptions;

    private OptionsJ2SQL() {
        super(TypeOfSQL.class);
    }

    @LoadJ2SQL public void loadList() {
        addLoader(TypeOfSQL.LIST, J2SQL.create(getWorkWithDataSource()).from(tOptions));
    }
    @LoadJ2SQL public void loadInsert() {
        addLoader(TypeOfSQL.INSERT, J2SQL.create(getWorkWithDataSource()).insertInto(tOptions).insertRow());
    }
    @LoadJ2SQL public void loadFind() {
        addLoader(TypeOfSQL.FIND, J2SQL.create(getWorkWithDataSource()).from(tOptions).where(tOptions.OPTION_TYPE.eq("?")));
    }
}
