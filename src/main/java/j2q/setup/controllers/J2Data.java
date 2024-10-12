package j2q.setup.controllers;

import j2q.setup.definitions.design.repo.singles.AutoNumberingRepo;
import j2q.setup.definitions.dtos.AutoNumberingDTO;
import j2q.setup.services.AutoNumberingService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public final class J2Data implements IJ2DataExtensions {
    private @Autowired AutoNumberingService autoNumberingService;

    @Override public List<AutoNumberingDTO> getAutoNumberingList(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return autoNumberingService.getAutoNumberingList(type);
    }
    @Override public List<AutoNumberingDTO> getAutoNumberingListAsync(AutoNumberingRepo.TypeOfSQL type) throws SQLException {
        return autoNumberingService.getAutoNumberingListAsync(type);
    }

    public boolean insertBulk(List<AutoNumberingDTO> insertRows) throws SQLException {
        if (CollectionUtils.isEmpty(insertRows)) return true;
        return autoNumberingService.insertBulk(insertRows);
    }
}
