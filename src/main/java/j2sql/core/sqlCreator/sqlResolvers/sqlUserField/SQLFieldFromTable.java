package j2sql.core.sqlCreator.sqlResolvers.sqlUserField;

import j2sql.core.linSQL.LinSQL;
import j2sql.core.linSQL.LinSQLCommons;
import j2sql.core.sqlCreator.sqlResolvers.SqlUserSelection;
import j2sql.core.sqlRetriever.SQLRetrieverForDBs;
import j2sql.definitions.design.schema.enums.GlobalFieldsDefinition;
import j2sql.definitions.design.schema.enums.GlobalTablesDefinition;
import j2sql.core.tds.DbFieldInstances;
import j2sql.core.tds.DbTable;
import j2sql.core.tds.DbTableInstances;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import j2sql.core.tds.DbField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public final class SQLFieldFromTable extends SqlUserSelection {
    @Override public Type getTypeOfSelection() { return this.getClass(); }

    private GlobalFieldsDefinition.DbF dbF;
    private DbField dbField = null;

    public SQLFieldFromTable(@Nonnull GlobalFieldsDefinition.DbF dbF) {
        super();
        init(null, null, dbF);
    }
    public SQLFieldFromTable(@Nonnull GlobalFieldsDefinition.DbF dbF, @Nullable String asAlias) {
        super();
        init(null, asAlias, dbF);
    }
    public SQLFieldFromTable(@Nonnull GlobalFieldsDefinition.DbF dbF, @Nullable String asAlias, @Nullable String setPrefix) {
        super();
        init(setPrefix, asAlias, dbF);
    }
    @Override public void init(@Nullable String setPrefix, @Nullable String asAlias, @Nullable Object... args) {
        assert args != null;
        this.dbF = (GlobalFieldsDefinition.DbF) args[0];
        this.dbField = DbFieldInstances.getMapTableInstance(this.dbF);
        super.setHasPrefix(setPrefix);
        super.setAsAlias(asAlias);
    }

    public GlobalFieldsDefinition.DbF getDbFieldEnum() { return this.dbF; }

    @Override public String getResolveObjectForSQL(SQLRetrieverForDBs forSQLRetrieverForDB) {
        return getResolveObjectForSQLMain(forSQLRetrieverForDB, null);
    }
    String getResolveObjectForSQLMain(SQLRetrieverForDBs forSQLRetrieverForDB, @Nullable GlobalTablesDefinition.DbT forDbt) {
        String returnName = StringUtils.defaultString(this.getHasPrefix());

        String tableHasPrefixForFields = StringUtils.EMPTY;
        if (forSQLRetrieverForDB.getTableMustPrefixFields()) {
            tableHasPrefixForFields = (forDbt == null ? StringUtils.EMPTY : StringUtils.defaultString(DbTableInstances.getMapTableInstance(forDbt).getTablePrefixForFields()));
        }
        if (forSQLRetrieverForDB.getTypeOfNamingSystemOrNormalized() == LinSQL.TypeOfNamingSystemOrNormalized.SYSTEM) {
            returnName = returnName.concat(tableHasPrefixForFields.concat(this.dbField.getDbfSystemName()));
        } else {
            if (this.dbField.getDbfNameEnum() == GlobalFieldsDefinition.DbF.ALL) {
                returnName = returnName.concat(LinSQLCommons.ASTERISK);
            } else {
                returnName = returnName.concat(this.dbField.getDbfNormalName());
            }
        }

        if (StringUtils.isBlank(this.getHasPrefix())) {
            String tableAsAlias = StringUtils.defaultString(forSQLRetrieverForDB.getWorkLInSQLBuilderParams().getWorkWithTableOnlyAsAlias());
            Set<Triple<DbTable, String, List<GlobalFieldsDefinition.DbF>>> availableTablesWithFields = forSQLRetrieverForDB.getAvailableTablesWithFields();
            if (CollectionUtils.isNotEmpty(availableTablesWithFields)) {
                for (Triple<DbTable, String, List<GlobalFieldsDefinition.DbF>> availableTableWithFields : Lists.reverse(availableTablesWithFields.stream().toList())) {
                    List<GlobalFieldsDefinition.DbF> tblFields = availableTableWithFields.getRight();
                    if (CollectionUtils.isNotEmpty(tblFields)) {
                        if (tblFields.contains(this.dbField.getDbfNameEnum())) {
                            tableAsAlias = availableTableWithFields.getMiddle();
                            break;
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(tableAsAlias)) returnName = tableAsAlias.concat(".").concat(returnName);
        }

        if (Strings.isNullOrEmpty(super.getAsAlias())) {
            if (forSQLRetrieverForDB.getWorkLInSQLBuilderParams().isApplyAutoAlias()) {
                if (!super.isIgnoreTableAsAlias()) super.setAsAlias(this.dbField.getDbfAsAlias());
            }
        }
        return LinSQLCommons.applyAsAlias(returnName, super.getAsAlias(), false, false);
    }
}
