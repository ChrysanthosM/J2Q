package j2q.setup.definitions.dtos;

import j2q.core.support.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record AutoNumberingDTO(int recId, String entityType, int entityNumber) {

    public static LoadDao newConverter() { return new LoadDao(); }
    public static class LoadDao extends RowLoader<AutoNumberingDTO> {
        @Override
        public AutoNumberingDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new AutoNumberingDTO(
                    resultSet.getInt(GlobalFieldsDefinition.DbF.RecID.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.EntityType.getSystemName()),
                    resultSet.getInt(GlobalFieldsDefinition.DbF.EntityNumber.getSystemName())
            );
        }
        @Override
        public AutoNumberingDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new AutoNumberingDTO(
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.RecID, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.EntityType, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.EntityNumber, columnNamesValues)
            );
        }
    }
}

