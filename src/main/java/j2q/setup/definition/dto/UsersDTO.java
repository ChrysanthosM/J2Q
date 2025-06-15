package j2q.setup.definition.dto;

import j2q.core.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definition.design.schema.enums.DbF;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record UsersDTO(int recId, String userName, String userPassword) implements Idto {
    @Override
    public List<Object> listValues() {
        return List.of(recId, userName, userPassword);
    }

    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<UsersDTO> {
        @Override
        public UsersDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new UsersDTO(
                    resultSet.getInt(DbF.REC_ID.getSystemName()),
                    resultSet.getString(DbF.USER_NAME.getSystemName()),
                    resultSet.getString(DbF.USER_PASSWORD.getSystemName())
            );
        }
        @Override
        public UsersDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new UsersDTO(
                    DtoFieldValue.getValue(DbF.REC_ID, columnNamesValues),
                    DtoFieldValue.getValue(DbF.USER_NAME, columnNamesValues),
                    DtoFieldValue.getValue(DbF.USER_PASSWORD, columnNamesValues)
            );
        }
    }
}
