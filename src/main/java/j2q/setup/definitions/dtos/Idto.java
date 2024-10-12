package j2q.setup.definitions.dtos;

import j2q.core.TTable;

import java.util.List;

public interface Idto {
    List<Object> listValues();

    default <T extends Idto> List<Object> getInsertValues(TTable tTable, T fromPojo) {
        return tTable.getAutoIncrease() ? fromPojo.listValues().stream().skip(1).toList() : fromPojo.listValues();
    }
}
