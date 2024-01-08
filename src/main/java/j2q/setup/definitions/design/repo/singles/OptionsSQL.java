package j2q.setup.definitions.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OptionsSQL {
    @Autowired private OptionsJ2SQL j2Options;
    public String getSQL(OptionsRepo.TypeOfSQL type) { return j2Options.getSQL(type); }
}
