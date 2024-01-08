package j2q.setup.definitions.dtos;

import j2q.core.support.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record OptionsDTO(int recId, String optionType, String optionName, String optionValue, String optionDetails,
                         String userStamp, String dateStamp) {

    public static LoadDao newConverter() { return new LoadDao(); }
    public static class LoadDao extends RowLoader<OptionsDTO> {
        @Override
        public OptionsDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new OptionsDTO(
                    resultSet.getInt(GlobalFieldsDefinition.DbF.RecID.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.OptionType.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.OptionName.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.OptionValue.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.OptionDetails.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.UserStamp.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.DateStamp.getSystemName())
            );
        }
        @Override
        public OptionsDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new OptionsDTO(
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.RecID, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.OptionType, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.OptionName, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.OptionValue, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.OptionDetails, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.UserStamp, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.DateStamp, columnNamesValues)
            );
        }
    }
}
