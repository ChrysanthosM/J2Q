package j2sql.definitions.design.repo.singles;

import j2sql.definitions.design.repo.IRepo;

public interface OptionsRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,
        SPECIFIC_OPTION_TYPE,

    }
}
