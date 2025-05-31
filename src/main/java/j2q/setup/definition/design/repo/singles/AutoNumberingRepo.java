package j2q.setup.definition.design.repo.singles;

import j2q.setup.definition.design.repo.IRepo;

public interface AutoNumberingRepo extends IRepo {
    enum TypeOfSQL {
        LIST,
        INSERT,
        DELETE_ALL,
        FIND,
        MAX_NUMBER_PER_ENTITY,
    }
}
