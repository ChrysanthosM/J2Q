package j2q.setup.definition.dto;

import j2q.core.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definition.design.schema.enums.DbF;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record OptionsDTO(int recId, String optionType, String optionName, String optionValue, String optionDetails,
                         String userStamp, String dateStamp) implements Idto {
    @Override
    public List<Object> listValues() {
        return List.of(recId, optionType, optionName, optionValue, optionDetails, userStamp, dateStamp);
    }

    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<OptionsDTO> {
        @Override
        public OptionsDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new OptionsDTO(
                    resultSet.getInt(DbF.REC_ID.getSystemName()),
                    resultSet.getString(DbF.OPTION_TYPE.getSystemName()),
                    resultSet.getString(DbF.OPTION_NAME.getSystemName()),
                    resultSet.getString(DbF.OPTION_VALUE.getSystemName()),
                    resultSet.getString(DbF.OPTION_DETAILS.getSystemName()),
                    resultSet.getString(DbF.USER_STAMP.getSystemName()),
                    resultSet.getString(DbF.DATE_STAMP.getSystemName())
            );
        }
        @Override
        public OptionsDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new OptionsDTO(
                    DtoFieldValue.getValue(DbF.REC_ID, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OPTION_TYPE, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OPTION_NAME, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OPTION_VALUE, columnNamesValues),
                    DtoFieldValue.getValue(DbF.OPTION_DETAILS, columnNamesValues),
                    DtoFieldValue.getValue(DbF.USER_STAMP, columnNamesValues),
                    DtoFieldValue.getValue(DbF.DATE_STAMP, columnNamesValues)
            );
        }
    }
}
