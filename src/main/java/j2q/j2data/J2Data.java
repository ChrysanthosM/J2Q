package j2q.j2data;

import j2q.AppConfig;
import j2q.db.JdbcIO;
import j2q.db.datasources.IDataSource;
import j2q.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.definitions.design.repo.singles.AutoNumberingSQL;
import j2q.definitions.design.repo.singles.OptionsSQL;
import j2q.definitions.design.repo.singles.UsersSQL;
import j2q.definitions.dtos.AutoNumberingDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Component
public final class J2Data {
    @Autowired private ApplicationContext context;
    private DataSource defaultDataSource;
    @Autowired private JdbcIO jdbcIO;

    @Autowired private UsersSQL usersSQL;
    @Autowired private AutoNumberingSQL autoNumberingSQL;
    @Autowired private OptionsSQL optionsSQL;

    @PostConstruct
    private void init() {
        defaultDataSource = context.getBean(AppConfig.class).getDefaultDataSource().getDS();
    }

    public List<AutoNumberingDTO> getAutoNumberingList() throws SQLException {
        return jdbcIO.select(defaultDataSource, AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.ALL));
    }
    public List<AutoNumberingDTO> getAutoNumberingListAsync() throws SQLException {
        return jdbcIO.selectAsync(defaultDataSource, AutoNumberingDTO.newConverter(), autoNumberingSQL.getSQL(AutoNumberingRepo.TypeOfSQL.ALL));
    }


}
