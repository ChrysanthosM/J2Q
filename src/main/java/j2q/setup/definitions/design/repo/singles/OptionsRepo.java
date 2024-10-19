package j2q.setup.definitions.design.repo.singles;

import j2q.setup.definitions.design.repo.IRepo;

public interface OptionsRepo extends IRepo {
    enum TypeOfSQL {
        LIST,
        FIND,
        INSERT,
    }
}
