package j2q.setup.definitions.design.repo.singles;

import j2q.core.AbstractJ2;
import j2q.core.J2SQL;
import j2q.core.LoadJ2SQL;
import j2q.setup.definitions.design.schema.tables.TAutoNumbering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static j2q.core.J2SQLShared.MAX;

@Component
public final class AutoNumberingJ2SQL extends AbstractJ2<AutoNumberingRepo.TypeOfSQL> implements AutoNumberingRepo {
    private @Autowired TAutoNumbering tAutoNumbering;

    private AutoNumberingJ2SQL() {
        super(TypeOfSQL.class);
    }

    @LoadJ2SQL public void loadList() {
        addLoader(TypeOfSQL.LIST, J2SQL.create(getWorkWithDataSource()).from(tAutoNumbering));
    }
    @LoadJ2SQL public void loadInsert() {
        addLoader(TypeOfSQL.INSERT, J2SQL.create(getWorkWithDataSource()).insertInto(tAutoNumbering).insertRow());
    }
    @LoadJ2SQL public void loadFind() {
        addLoader(TypeOfSQL.FIND, J2SQL.create(getWorkWithDataSource()).from(tAutoNumbering).where(tAutoNumbering.ENTITY_TYPE.eq("?")));
    }
    @LoadJ2SQL public void loadMaxNumberPerEntity() {
        addLoader(TypeOfSQL.MAX_NUMBER_PER_ENTITY, J2SQL.create(getWorkWithDataSource()).from(tAutoNumbering).
                select(tAutoNumbering.ENTITY_TYPE, MAX(tAutoNumbering.ENTITY_NUMBER))
                .groupBy(tAutoNumbering.ENTITY_TYPE)
                .orderBy(tAutoNumbering.ENTITY_TYPE));
    }

    @LoadJ2SQL public void loadDeleteAll() {
        addLoader(TypeOfSQL.DELETE_ALL, J2SQL.create(getWorkWithDataSource()).deleteFrom(tAutoNumbering));
    }

}
