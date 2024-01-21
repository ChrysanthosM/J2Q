package j2q.setup.definitions.dtos;

import j2q.core.bridge.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definitions.design.schema.enums.DbF;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record UsersDTO(int recId, String userName, String userPassword) {

    public static LoadDTO newConverter() { return new LoadDTO(); }
    public static class LoadDTO extends RowLoader<UsersDTO> {
        @Override
        public UsersDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new UsersDTO(
                    resultSet.getInt(DbF.RecID.getSystemName()),
                    resultSet.getString(DbF.UserName.getSystemName()),
                    resultSet.getString(DbF.UserPassword.getSystemName())
            );
        }
        @Override
        public UsersDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new UsersDTO(
                    DtoFieldValue.getValue(DbF.RecID, columnNamesValues),
                    DtoFieldValue.getValue(DbF.UserName, columnNamesValues),
                    DtoFieldValue.getValue(DbF.UserPassword, columnNamesValues)
            );
        }
    }
}
