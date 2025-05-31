package j2q.setup.definition.design.repo.singles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OptionsSQL {
    private @Autowired OptionsJ2SQL j2Options;
    public String getSQL(OptionsRepo.TypeOfSQL type) { return j2Options.getSQL(type); }
}
