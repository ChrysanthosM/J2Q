package j2sql.definitions.dtos;

import j2sql.db.loader.DbRecord;
import j2sql.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

import static j2sql.definitions.design.schema.enums.GlobalFieldsDefinition.DbF.*;

public record OptionsDTO(int recId, String optionType, String optionName, String optionValue, String optionDetails,
                         String userStamp, String dateStamp) {

    public static LoadDao newConverter() { return new LoadDao(); }
    public static class LoadDao extends RowLoader<OptionsDTO> {
        @Override
        public OptionsDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new OptionsDTO(
                    resultSet.getInt(RecID.getSystemName()),
                    resultSet.getString(OptionType.getSystemName()),
                    resultSet.getString(OptionName.getSystemName()),
                    resultSet.getString(OptionValue.getSystemName()),
                    resultSet.getString(OptionDetails.getSystemName()),
                    resultSet.getString(UserStamp.getSystemName()),
                    resultSet.getString(DateStamp.getSystemName())
            );
        }
        @Override
        public OptionsDTO convertDbRecord(DbRecord dbRecord) {
            return new OptionsDTO(
                    dbRecord.getValue(RecID.getSystemName()),
                    dbRecord.getValue(OptionType.getSystemName()),
                    dbRecord.getValue(OptionName.getSystemName()),
                    dbRecord.getValue(OptionValue.getSystemName()),
                    dbRecord.getValue(OptionDetails.getSystemName()),
                    dbRecord.getValue(UserStamp.getSystemName()),
                    dbRecord.getValue(DateStamp.getSystemName())
            );
        }
    }
}
