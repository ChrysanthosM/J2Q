package j2q.setup.definitions.design.repo.singles;

import j2q.core.face.J2SQL;
import j2q.core.support.AbstractJ2;
import j2q.setup.definitions.design.schema.tables.TAutoNumbering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static j2q.core.face.J2SQLShared.MAX;

@Component
public class AutoNumberingJ2SQL extends AbstractJ2<AutoNumberingRepo.TypeOfSQL> implements AutoNumberingRepo {
    @Autowired private TAutoNumbering tAutoNumbering;

    protected AutoNumberingJ2SQL() {
        super(TypeOfSQL.class);
    }

    public void loadALL() {
        addLoader(TypeOfSQL.ALL, J2SQL.create(getDefaultDataSource()).from(tAutoNumbering));
    }
    public void loadINSERT_ROW() {
        addLoader(TypeOfSQL.INSERT_ROW, J2SQL.create(getDefaultDataSource()).insertInto(tAutoNumbering).insertRow());
    }
    public void loadSPECIFIC_ENTITY() {
        addLoader(TypeOfSQL.SPECIFIC_ENTITY, J2SQL.create(getDefaultDataSource()).from(tAutoNumbering).where(tAutoNumbering.ENTITY_TYPE.eq("?")));
    }
    public void loadMAX_NUMBER_PER_ENTITY() {
        addLoader(TypeOfSQL.MAX_NUMBER_PER_ENTITY, J2SQL.create(getDefaultDataSource()).from(tAutoNumbering).
                select(tAutoNumbering.ENTITY_TYPE, MAX(tAutoNumbering.ENTITY_NUMBER))
                .groupBy(tAutoNumbering.ENTITY_TYPE)
                .orderBy(tAutoNumbering.ENTITY_TYPE));
    }
}
