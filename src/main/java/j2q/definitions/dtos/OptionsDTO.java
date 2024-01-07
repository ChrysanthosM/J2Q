package j2q.definitions.dtos;

import j2q.db.loader.RowLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static j2q.definitions.design.schema.enums.GlobalFieldsDefinition.DbF.*;

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
        public OptionsDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new OptionsDTO(
                    DtoFieldValue.getValue(RecID, columnNamesValues),
                    DtoFieldValue.getValue(OptionType, columnNamesValues),
                    DtoFieldValue.getValue(OptionName, columnNamesValues),
                    DtoFieldValue.getValue(OptionValue, columnNamesValues),
                    DtoFieldValue.getValue(OptionDetails, columnNamesValues),
                    DtoFieldValue.getValue(UserStamp, columnNamesValues),
                    DtoFieldValue.getValue(DateStamp, columnNamesValues)
            );
        }
    }
}
