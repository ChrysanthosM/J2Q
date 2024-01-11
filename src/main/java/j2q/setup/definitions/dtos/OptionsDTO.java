package j2q.setup.definitions.dtos;

import j2q.core.support.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definitions.design.schema.enums.DbF;
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
                    resultSet.getInt(DbF.RecID.getSystemName()),
                    resultSet.getString(DbF.OptionType.getSystemName()),
                    resultSet.getString(DbF.OptionName.getSystemName()),
                    resultSet.getString(DbF.OptionValue.getSystemName()),
                    resultSet.getString(DbF.OptionDetails.getSystemName()),
                    resultSet.getString(DbF.UserStamp.getSystemName()),
                    resultSet.getString(DbF.DateStamp.getSystemName())
            );
        }
        @Override
        public OptionsDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new OptionsDTO(
                    DtoFieldValue.getValue(DbF.RecID, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OptionType, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OptionName, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OptionValue, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OptionDetails, columnNamesValues),
                    DtoFieldValue.getValue(DbF.UserStamp, columnNamesValues),
                    DtoFieldValue.getValue(DbF.DateStamp, columnNamesValues)
            );
        }
    }
}
