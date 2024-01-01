package j2sql.definitions.dtos;

import j2sql.db.loader.DbRecord;
import j2sql.db.loader.RowLoader;

import java.sql.ResultSet;
import java.sql.SQLException;

import static j2sql.definitions.design.schema.enums.GlobalFieldsDefinition.DbF.*;

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
        public UsersDTO convertDbRecord(DbRecord dbRecord) {
            return new UsersDTO(
                    dbRecord.getValue(RecID.getSystemName()),
                    dbRecord.getValue(UserName.getSystemName()),
                    dbRecord.getValue(UserPassword.getSystemName())
            );
        }
    }
}
