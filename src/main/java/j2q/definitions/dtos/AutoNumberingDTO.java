package j2q.definitions.dtos;

import j2q.db.loader.DbRecord;
import j2q.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        public AutoNumberingDTO convertDbRecord(DbRecord dbRecord) {
            return new AutoNumberingDTO(
                    dbRecord.getValue(RecID.getSystemName()),
                    dbRecord.getValue(EntityType.getSystemName()),
                    dbRecord.getValue(EntityNumber.getSystemName())
            );
        }
    }
}

