package j2q.setup.definitions.dtos;

import j2q.core.support.DtoFieldValue;
import j2q.db.loader.RowLoader;
import j2q.setup.definitions.design.schema.enums.GlobalFieldsDefinition;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public record UsersDTO(int recId, String userName, String userPassword) {

    public static LoadDao newConverter() { return new LoadDao(); }
    public static class LoadDao extends RowLoader<UsersDTO> {
        @Override
        public UsersDTO convertResultSet(ResultSet resultSet) throws SQLException {
            return new UsersDTO(
                    resultSet.getInt(GlobalFieldsDefinition.DbF.RecID.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.UserName.getSystemName()),
                    resultSet.getString(GlobalFieldsDefinition.DbF.UserPassword.getSystemName())
            );
        }
        @Override
        public UsersDTO convertResultSet(List<Pair<String, Object>> columnNamesValues) throws NoSuchElementException {
            return new UsersDTO(
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.RecID, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.UserName, columnNamesValues),
                    DtoFieldValue.getValue(GlobalFieldsDefinition.DbF.UserPassword, columnNamesValues)
            );
        }
    }
}
