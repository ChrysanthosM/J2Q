package j2q.definitions.dtos;

import j2q.db.loader.RowLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static j2q.definitions.design.schema.enums.GlobalFieldsDefinition.DbF.*;

public record AutoNumberingDTO(int recId, String entityType, int entityNumber) {

    public static LoadDao newConverter() { return new LoadDao(); }
    public static class LoadDao extends RowLoader<AutoNumberingDTO> {
        @Override
        public AutoNumberingDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new AutoNumberingDTO(
                    resultSet.getInt(RecID.getSystemName()),
                    resultSet.getString(EntityType.getSystemName()),
                    resultSet.getInt(EntityNumber.getSystemName())
            );
        }
        @Override
        public AutoNumberingDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new AutoNumberingDTO(
                    DtoFieldValue.getValue(RecID, columnNamesValues),
                    DtoFieldValue.getValue(EntityType, columnNamesValues),
                    DtoFieldValue.getValue(EntityNumber, columnNamesValues)
            );
        }
    }
}

