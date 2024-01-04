package j2q.definitions.design.repo.singles;

import j2q.definitions.design.repo.IRepo;

public interface AutoNumberingRepo extends IRepo {
    enum TypeOfSQL {
        ALL,
        INSERT_ROW,
        SPECIFIC_ENTITY,
        MAX_NUMBER_PER_ENTITY,
    }
}
