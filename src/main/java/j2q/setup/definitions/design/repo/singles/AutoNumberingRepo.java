package j2q.setup.definitions.design.repo.singles;

import j2q.core.support.IRepo;

public interface AutoNumberingRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,
        SPECIFIC_ENTITY,
        MAX_NUMBER_PER_ENTITY,
    }
}
