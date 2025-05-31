package j2q.setup.definition.design.repo.singles;

import j2q.setup.definition.design.repo.IRepo;

public interface OptionsRepo extends IRepo {
    enum TypeOfSQL {
        LIST,
        FIND,
        INSERT,
    }
}
