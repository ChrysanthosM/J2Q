package j2q.setup.definition.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AutoNumberingSQL {
    private @Autowired AutoNumberingJ2SQL j2AutoNumbering;
    public String getSQL(AutoNumberingRepo.TypeOfSQL type) { return j2AutoNumbering.getSQL(type); }
}
