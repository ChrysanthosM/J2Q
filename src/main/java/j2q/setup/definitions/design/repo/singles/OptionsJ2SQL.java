package j2q.setup.definitions.design.repo.singles;

import j2q.core.face.J2SQL;
import j2q.setup.definitions.design.schema.tables.TOptions;
import j2q.core.support.AbstractJ2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptionsJ2SQL extends AbstractJ2<OptionsRepo.TypeOfSQL> implements OptionsRepo {
    private @Autowired TOptions tOptions;

    protected OptionsJ2SQL() {
        super(TypeOfSQL.class);
    }

    public void loadALL() {
        addLoader(TypeOfSQL.ALL, J2SQL.create(getDefaultDataSource()).from(tOptions));
    }
    public void loadINSERT_ROW() {
        addLoader(TypeOfSQL.INSERT_ROW, J2SQL.create(getDefaultDataSource()).insertInto(tOptions).insertRow());
    }
    public void loadSPECIFIC_OPTION_TYPE() {
        addLoader(TypeOfSQL.SPECIFIC_OPTION_TYPE, J2SQL.create(getDefaultDataSource()).from(tOptions).where(tOptions.OPTION_TYPE.eq("?")));
    }
}
