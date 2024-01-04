package j2q.definitions.design.repo.singles;

import j2q.definitions.design.repo.IRepo;

public interface OptionsRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,
        SPECIFIC_OPTION_TYPE,

    }
}
