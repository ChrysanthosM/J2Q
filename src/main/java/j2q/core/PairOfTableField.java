package j2q.core;

import j2q.setup.definition.design.schema.sqlite.enums.DbF;
import j2q.setup.definition.design.schema.sqlite.enums.DbT;
import jakarta.annotation.Nonnull;
import lombok.Getter;


@Getter
public final class PairOfTableField implements IDeployFilters, IDeployOrdering, IProvideDataTypeForSQL {
    public static PairOfTableField of(DbT dbt, DbF dbf) { return new PairOfTableField(dbt, dbf); }

    private final DbT dbt;
    private final DbF dbf;
    private final DbField dbField;
    private PairOfTableField(DbT dbt, DbF dbf) {
        this.dbt = dbt;
        this.dbf = dbf;
        this.dbField = DbFieldInstances.getMapTableInstance(this.dbf);
    }

    public SQLFieldObject as(@Nonnull String asAlias) { return new SQLFieldObject(this, asAlias, null); }

    @Override
    public Boolean getInQuotesRequirement() {
        return this.dbf.getFieldDataType().getInQuotesRequirement();
    }
}
