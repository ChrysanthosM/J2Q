package j2q.definitions.dtos;

import j2q.db.loader.RowLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static j2q.definitions.design.schema.enums.GlobalFieldsDefinition.DbF.*;

public record UsersDTO(int recId, String userName, String userPassword) {

    public static LoadDao newConverter() { return new LoadDao(); }
    public static class LoadDao extends RowLoader<UsersDTO> {
        @Override
        public UsersDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new UsersDTO(
                    resultSet.getInt(RecID.getSystemName()),
                    resultSet.getString(UserName.getSystemName()),
                    resultSet.getString(UserPassword.getSystemName())
            );
        }
        @Override
        public UsersDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new UsersDTO(
                    DtoFieldValue.getValue(RecID, columnNamesValues),
                    DtoFieldValue.getValue(UserName, columnNamesValues),
                    DtoFieldValue.getValue(UserPassword, columnNamesValues)
            );
        }
    }
}
