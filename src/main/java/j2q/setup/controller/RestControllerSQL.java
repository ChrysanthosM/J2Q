package j2q.setup.controller;

import j2q.setup.definition.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definition.design.repo.singles.OptionsSQL;
import j2q.setup.definition.design.repo.singles.UsersSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import j2q.setup.definition.design.repo.singles.AutoNumberingSQL;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class RestControllerSQL {
    private @Autowired UsersSQL usersSQL;
    private @Autowired AutoNumberingSQL autoNumberingSQL;
    private @Autowired OptionsSQL optionsSQL;

    //localhost:8080/AutoNumberingSQL?type=ALL
    @GetMapping("/AutoNumberingSQL")
    public String getAutoNumberingSQL(@RequestParam(name = "type") AutoNumberingRepo.TypeOfSQL type) {
        return autoNumberingSQL.getSQL(type);
    }

}
