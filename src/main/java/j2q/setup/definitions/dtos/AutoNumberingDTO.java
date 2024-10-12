package j2q.setup.definitions.dtos;

import j2q.core.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definitions.design.schema.enums.DbF;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record AutoNumberingDTO(int recId, String entityType, int entityNumber) implements Idto {
    @Override
    public List<Object> listValues() {
        return List.of(recId, entityType, entityNumber);
    }

    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<AutoNumberingDTO> {
        @Override
        public AutoNumberingDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new AutoNumberingDTO(
                    resultSet.getInt(DbF.RecID.getSystemName()),
                    resultSet.getString(DbF.EntityType.getSystemName()),
                    resultSet.getInt(DbF.EntityNumber.getSystemName())
            );
        }
        @Override
        public AutoNumberingDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new AutoNumberingDTO(
                    DtoFieldValue.getValue(DbF.RecID, columnNamesValues),
                    DtoFieldValue.getValue(DbF.EntityType, columnNamesValues),
                    DtoFieldValue.getValue(DbF.EntityNumber, columnNamesValues)
            );
        }
    }

}

