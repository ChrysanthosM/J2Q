package j2q.tests;

import com.google.common.collect.Lists;
import j2q.core.J2SQL;
import j2q.db.datasources.DataSourceForDB2i;
import j2q.setup.definitions.design.schema.enums.DbFValues;
import j2q.setup.definitions.design.schema.tables.TAutoNumbering;
import j2q.setup.definitions.design.schema.tables.TOptions;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static j2q.core.J2SQL.*;
import static j2q.core.J2SQLShared.*;
import static j2q.core.J2SQLShared.PFX.*;


@SpringBootTest
public class TestSQLStatements {
    private final List<String> stmts = Lists.newArrayList();
    private @Autowired DataSourceForDB2i workDataSource;
    private @Autowired TAutoNumbering tAutoNumbering;
    private @Autowired TOptions tOptions;

    private static String checkResult(String stmt, String shouldBe) {
        return Objects.equals(StringUtils.trimToEmpty(stmt), shouldBe) + " - " + stmt;
    }

    @Test
    public void testSQLStatements() {
        long startTime = System.currentTimeMillis();
        testSQLStatementsMain(true);
        testSQLStatementsMain(false);
        long durationTime = System.currentTimeMillis() - startTime;
        stmts.forEach(System.out::println);
        System.out.println("Test finished in " + durationTime + " ms");
    }
    private void testSQLStatementsMain(boolean normalizeNames) {
        stmts.add("****************************** Start Test with normalizeNames: " + normalizeNames + " ******************************");

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).setApplyAutoAlias().insertInto(tAutoNumbering).insertRow("AAA", 1)
                        .getSQL(),
                normalizeNames
                        ? "INSERT INTO $.AutoNumbering (EntityType, EntityNumber) VALUES ('AAA', 1)"
                        : "INSERT INTO $.Sys_AutoNumbering (AASys_EntityType, AASys_EntityNumber) VALUES ('AAA', 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).insertInto(tAutoNumbering).insertRows(List.of("AAA", 1), List.of("BBB", 2), List.of("CCC", 3))
                        .getSQL(),
                normalizeNames
                        ? "INSERT INTO $.AutoNumbering (EntityType, EntityNumber) VALUES ('AAA', 1), ('BBB', 2), ('CCC', 3)"
                        : "INSERT INTO $.Sys_AutoNumbering (AASys_EntityType, AASys_EntityNumber) VALUES ('AAA', 1), ('BBB', 2), ('CCC', 3)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).insertInto(tAutoNumbering).insertRowsFrom(
                        J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).where(tAutoNumbering.ENTITY_NUMBER.eq(1)))
                        .getSQL(),
                normalizeNames
                        ? "INSERT INTO $.AutoNumbering SELECT RecID, EntityType, EntityNumber FROM $.AutoNumbering  WHERE (EntityNumber = 1)"
                        : "INSERT INTO $.Sys_AutoNumbering SELECT AASys_RecID, AASys_EntityType, AASys_EntityNumber FROM $.Sys_AutoNumbering  WHERE (AASys_EntityNumber = 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).distinct().setApplyAutoAlias()
                        .where(tAutoNumbering.ENTITY_TYPE.eq(1))
                        .getSQL(),
                normalizeNames
                        ? "SELECT DISTINCT RecID AS \"Rec_ID\", EntityType AS \"Entity_Type\", EntityNumber AS \"Entity_Number\" FROM $.AutoNumbering  WHERE (EntityType = '1')"
                        : "SELECT DISTINCT AASys_RecID AS \"Rec_ID\", AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"Entity_Number\" FROM $.Sys_AutoNumbering  WHERE (AASys_EntityType = '1')"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE, tAutoNumbering.ENTITY_NUMBER, operation(tAutoNumbering.ENTITY_NUMBER, "+1")).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT EntityType AS \"Entity_Type\", EntityNumber AS \"Entity_Number\", EntityNumber  +1 FROM $.AutoNumbering"
                        : "SELECT AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"Entity_Number\", AASys_EntityNumber  +1 FROM $.Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE.as("asTheAlias"))
                        .getSQL(),
                normalizeNames
                        ? "SELECT EntityType AS \"asTheAlias\" FROM $.AutoNumbering"
                        : "SELECT AASys_EntityType AS \"asTheAlias\" FROM $.Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0)).selectAll()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.* FROM $.AutoNumbering AS T0"
                        : "SELECT T0.* FROM $.Sys_AutoNumbering AS T0"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0)).select(tAutoNumbering.ALL)
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.* FROM $.AutoNumbering AS T0"
                        : "SELECT T0.* FROM $.Sys_AutoNumbering AS T0"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).deleteFrom(tAutoNumbering).where(tAutoNumbering.ENTITY_NUMBER.eq(1))
                        .getSQL(),
                normalizeNames
                        ? "DELETE FROM $.AutoNumbering WHERE (EntityNumber = 1)"
                        : "DELETE FROM $.Sys_AutoNumbering WHERE (AASys_EntityNumber = 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0))
                        .whereExists(J2SQL.create(workDataSource, normalizeNames).from(tOptions.as(A1)).selectAll().where(t0(tAutoNumbering.ENTITY_TYPE).eq(a1(tOptions.OPTION_TYPE)))).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.RecID AS \"Rec_ID\", T0.EntityType AS \"Entity_Type\", T0.EntityNumber AS \"Entity_Number\" FROM $.AutoNumbering AS T0  " +
                        "WHERE EXISTS (SELECT A1.* FROM $.Options AS A1  WHERE (T0.EntityType = A1.OptionType))"
                        : "SELECT T0.AASys_RecID AS \"Rec_ID\", T0.AASys_EntityType AS \"Entity_Type\", T0.AASys_EntityNumber AS \"Entity_Number\" FROM $.Sys_AutoNumbering AS T0  " +
                        "WHERE EXISTS (SELECT A1.* FROM $.Sys_Options AS A1  WHERE (T0.AASys_EntityType = A1.ABSys_OptionType))"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(tAutoNumbering)
                        .select(tAutoNumbering.ENTITY_TYPE, tAutoNumbering.ENTITY_NUMBER.as("asAlias"), "const1", 2, 4.5,
                                asAlias(5, "asAlias5"), CONCAT(tAutoNumbering.ENTITY_TYPE, "AAA", 1, LTRIM(tAutoNumbering.ENTITY_TYPE)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT EntityType AS \"Entity_Type\", EntityNumber AS \"asAlias\", 'const1', '2', '4.5', '5' AS \"asAlias5\", " +
                        "CONCAT(CONCAT(CONCAT(EntityType AS \"Entity_Type\", 'AAA'), '1'), " +
                        "LTRIM(EntityType AS \"Entity_Type\")) " +
                        "FROM $.AutoNumbering"
                        : "SELECT AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"asAlias\", 'const1', '2', '4.5', '5' AS \"asAlias5\", " +
                        "CONCAT(CONCAT(CONCAT(AASys_EntityType AS \"Entity_Type\", 'AAA'), '1'), " +
                        "LTRIM(AASys_EntityType AS \"Entity_Type\")) " +
                        "FROM $.Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(tAutoNumbering.as("T"))
                        .select(asAlias(MIN(tAutoNumbering.ENTITY_TYPE), "functionAsAlias"))
                        .orderBy(tAutoNumbering.ENTITY_TYPE).limitOffset(1, 2)
                        .getSQL(),
                normalizeNames
                        ? "SELECT (MIN(T.EntityType) AS \"functionAsAlias\") FROM $.AutoNumbering AS T   ORDER BY T.EntityType ASC  LIMIT 1 OFFSET 2"
                        : "SELECT (MIN(T.AASys_EntityType) AS \"functionAsAlias\") FROM $.Sys_AutoNumbering AS T   ORDER BY T.AASys_EntityType ASC  LIMIT 1 OFFSET 2"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering)
                        .select(tAutoNumbering.ENTITY_TYPE, COUNT())
                        .groupBy(tAutoNumbering.ENTITY_TYPE).having(COUNT().gt(1))
                        .orderBy(tAutoNumbering.ENTITY_TYPE.desc(), tAutoNumbering.ENTITY_NUMBER.asc())
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT EntityType AS \"Entity_Type\", COUNT(*) FROM $.AutoNumbering  GROUP BY EntityType  HAVING COUNT(*) > 1 ORDER BY EntityType DESC, EntityNumber ASC"
                        : "SELECT AASys_EntityType AS \"Entity_Type\", COUNT(*) FROM $.Sys_AutoNumbering  GROUP BY AASys_EntityType  HAVING COUNT(*) > 1 ORDER BY AASys_EntityType DESC, AASys_EntityNumber ASC"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0)).selectAll()
                        .leftJoin(tOptions.as(J1)).fromJoinSelectOnly(tOptions.ALL).on(j1(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.*, J1.* FROM $.AutoNumbering AS T0 LEFT JOIN $.Options AS J1 ON (J1.EntityType = T0.OptionType)"
                        : "SELECT T0.*, J1.* FROM $.Sys_AutoNumbering AS T0 LEFT JOIN $.Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0))
                        .leftJoin(tOptions.as(J1)).on(j1(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.RecID AS \"Rec_ID\", T0.EntityType AS \"Entity_Type\", T0.EntityNumber AS \"Entity_Number\", J1.RecID AS \"Rec_ID\", J1.OptionType AS \"Option_Type\", J1.OptionName AS \"Option_Name\", J1.OptionValue AS \"Option_Value\", J1.OptionDetails AS \"Option_Details\" FROM $.AutoNumbering AS T0 LEFT JOIN $.Options AS J1 ON (J1.EntityType = T0.OptionType)"
                        : "SELECT T0.AASys_RecID AS \"Rec_ID\", T0.AASys_EntityType AS \"Entity_Type\", T0.AASys_EntityNumber AS \"Entity_Number\", J1.ABSys_RecID AS \"Rec_ID\", J1.ABSys_OptionType AS \"Option_Type\", J1.ABSys_OptionName AS \"Option_Name\", J1.ABSys_OptionValue AS \"Option_Value\", J1.ABSys_OptionDetails AS \"Option_Details\" FROM $.Sys_AutoNumbering AS T0 LEFT JOIN $.Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0)).select(tAutoNumbering.ALL)
                        .leftJoin(tOptions.as(J1)).fromJoinSelectOnly(tOptions.ALL).on(j1(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE))).addJoinFilters(tOptions.OPTION_TYPE.gt(1).and(tOptions.OPTION_TYPE.lt(1000)))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.*, J1.* FROM $.AutoNumbering AS T0 LEFT JOIN $.Options AS J1 ON (J1.EntityType = T0.OptionType) WHERE (J1.OptionType > '1' AND J1.OptionType < '1000')"
                        : "SELECT T0.*, J1.* FROM $.Sys_AutoNumbering AS T0 LEFT JOIN $.Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType) WHERE (J1.ABSys_OptionType > '1' AND J1.ABSys_OptionType < '1000')"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0)).select(tAutoNumbering.ALL)
                        .leftJoin(tOptions.as(J1)).fromJoinSelectOnly(tOptions.ALL).on(j1(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE))).addJoinFilters(tOptions.OPTION_TYPE.gt(1).and(tOptions.OPTION_TYPE.lt(1000)))
                        .leftJoin(tOptions.as(J2)).fromJoinSelectOnly(tOptions.ALL).on(j2(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE))).addJoinFilters(tOptions.OPTION_TYPE.gt(1001).and(tOptions.OPTION_TYPE.lt(2000)))
                        .leftJoin(tOptions.as(J3)).fromJoinSelectOnly(tOptions.ALL).on(j3(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE))).addJoinFilters(tOptions.OPTION_TYPE.gt(2001).and(tOptions.OPTION_TYPE.lt(3000)))
                        .leftJoin(tOptions.as(J4)).fromJoinSelectOnly(tOptions.ALL).on(j4(tAutoNumbering.ENTITY_TYPE).eq(t0(tOptions.OPTION_TYPE))).addJoinFilters(tOptions.OPTION_TYPE.gt(3001).and(tOptions.OPTION_TYPE.lt(4000)))
                        .where(tAutoNumbering.ENTITY_NUMBER.gt(0))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.*, J1.*, J2.*, J3.*, J4.* FROM $.AutoNumbering AS T0 " +
                        "LEFT JOIN $.Options AS J1 ON (J1.EntityType = T0.OptionType) " +
                        "LEFT JOIN $.Options AS J2 ON (J2.EntityType = T0.OptionType) " +
                        "LEFT JOIN $.Options AS J3 ON (J3.EntityType = T0.OptionType) " +
                        "LEFT JOIN $.Options AS J4 ON (J4.EntityType = T0.OptionType) " +
                        "WHERE (T0.EntityNumber > 0) AND ((J1.OptionType > '1' AND J1.OptionType < '1000')  AND (J2.OptionType > '1001' AND J2.OptionType < '2000')  AND (J3.OptionType > '2001' AND J3.OptionType < '3000')  AND (J4.OptionType > '3001' AND J4.OptionType < '4000'))"
                        : "SELECT T0.*, J1.*, J2.*, J3.*, J4.* FROM $.Sys_AutoNumbering AS T0 " +
                        "LEFT JOIN $.Sys_Options AS J1 ON (J1.AASys_EntityType = T0.ABSys_OptionType) " +
                        "LEFT JOIN $.Sys_Options AS J2 ON (J2.AASys_EntityType = T0.ABSys_OptionType) " +
                        "LEFT JOIN $.Sys_Options AS J3 ON (J3.AASys_EntityType = T0.ABSys_OptionType) " +
                        "LEFT JOIN $.Sys_Options AS J4 ON (J4.AASys_EntityType = T0.ABSys_OptionType) " +
                        "WHERE (T0.AASys_EntityNumber > 0) AND ((J1.ABSys_OptionType > '1' AND J1.ABSys_OptionType < '1000')  AND (J2.ABSys_OptionType > '1001' AND J2.ABSys_OptionType < '2000')  AND (J3.ABSys_OptionType > '2001' AND J3.ABSys_OptionType < '3000')  AND (J4.ABSys_OptionType > '3001' AND J4.ABSys_OptionType < '4000'))"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).where(tAutoNumbering.ENTITY_TYPE.eq("?")).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT RecID AS \"Rec_ID\", EntityType AS \"Entity_Type\", EntityNumber AS \"Entity_Number\" FROM $.AutoNumbering  WHERE (EntityType = ?)"
                        : "SELECT AASys_RecID AS \"Rec_ID\", AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"Entity_Number\" FROM $.Sys_AutoNumbering  WHERE (AASys_EntityType = ?)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).where(tAutoNumbering.ENTITY_TYPE.eq("?")).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT RecID AS \"Rec_ID\", EntityType AS \"Entity_Type\", EntityNumber AS \"Entity_Number\" FROM $.AutoNumbering  WHERE (EntityType = ?)"
                        : "SELECT AASys_RecID AS \"Rec_ID\", AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"Entity_Number\" FROM $.Sys_AutoNumbering  WHERE (AASys_EntityType = ?)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE)
                        .UNION(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE))
                        .UNION(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT EntityType AS \"Entity_Type\" FROM $.AutoNumbering     " +
                        "UNION SELECT EntityType AS \"Entity_Type\" FROM $.AutoNumbering      " +
                        "UNION SELECT EntityType AS \"Entity_Type\" FROM $.AutoNumbering"
                        : "SELECT AASys_EntityType AS \"Entity_Type\" FROM $.Sys_AutoNumbering     " +
                        "UNION SELECT AASys_EntityType AS \"Entity_Type\" FROM $.Sys_AutoNumbering      " +
                        "UNION SELECT AASys_EntityType AS \"Entity_Type\" FROM $.Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering.as(T0)).select(tAutoNumbering.ALL).attachComments("sample comment")
                        .setApplyAutoAlias().getSQL(),
                normalizeNames
                        ? "SELECT T0.* FROM $.AutoNumbering AS T0      /*sample comment*/"
                        : "SELECT T0.* FROM $.Sys_AutoNumbering AS T0      /*sample comment*/"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).distinct().from(tAutoNumbering).where(tAutoNumbering.ENTITY_NUMBER.eq(1)).setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT DISTINCT RecID AS \"Rec_ID\", EntityType AS \"Entity_Type\", EntityNumber AS \"Entity_Number\" FROM $.AutoNumbering  WHERE (EntityNumber = 1)"
                        : "SELECT DISTINCT AASys_RecID AS \"Rec_ID\", AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"Entity_Number\" FROM $.Sys_AutoNumbering  WHERE (AASys_EntityNumber = 1)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering)
                        .where(tAutoNumbering.ENTITY_TYPE.eq(DbFValues.ValuesForEntityType.SURROGATE_NUM))
                        .and(tAutoNumbering.ENTITY_TYPE.inSubSelect(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE).getSQL()))
                        .and(not(tAutoNumbering.ENTITY_TYPE.like("AB%")))
                        .and(tAutoNumbering.ENTITY_TYPE.between(11, 22).or(tAutoNumbering.ENTITY_NUMBER.between(1, 2)))
                        .or(tAutoNumbering.ENTITY_TYPE.in(List.of(1, 2)))
                        .or(tAutoNumbering.ENTITY_TYPE.in(3, 4))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT RecID AS \"Rec_ID\", EntityType AS \"Entity_Type\", EntityNumber AS \"Entity_Number\" FROM $.AutoNumbering  " +
                        "WHERE (EntityType = 'E02') " +
                        "AND (EntityType IN (SELECT EntityType FROM $.AutoNumbering)) " +
                        "AND (EntityType NOT LIKE 'AB%') " +
                        "AND (EntityType BETWEEN '11' AND '22' OR EntityNumber BETWEEN 1 AND 2) " +
                        "OR (EntityType IN ('1', '2')) " +
                        "OR (EntityType IN ('3', '4'))"
                        : "SELECT AASys_RecID AS \"Rec_ID\", AASys_EntityType AS \"Entity_Type\", AASys_EntityNumber AS \"Entity_Number\" FROM $.Sys_AutoNumbering  " +
                        "WHERE (AASys_EntityType = 'E02') " +
                        "AND (AASys_EntityType IN (SELECT AASys_EntityType FROM $.Sys_AutoNumbering)) " +
                        "AND (AASys_EntityType NOT LIKE 'AB%') " +
                        "AND (AASys_EntityType BETWEEN '11' AND '22' OR AASys_EntityNumber BETWEEN 1 AND 2) " +
                        "OR (AASys_EntityType IN ('1', '2')) " +
                        "OR (AASys_EntityType IN ('3', '4'))"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(tAutoNumbering.as(T0))
                        .select(tAutoNumbering.ENTITY_NUMBER.as("asEntNum"))
                        .where(tAutoNumbering.ENTITY_NUMBER.gt(1)).andNot(tAutoNumbering.ENTITY_TYPE.like("Α%")).and(tAutoNumbering.ENTITY_TYPE.notLike("B%"))
                        .and(tAutoNumbering.ENTITY_NUMBER.between(2, 2000))
                        .and(tAutoNumbering.ENTITY_TYPE.inSubSelect(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE).getSQL()))
                        .and(tAutoNumbering.ENTITY_NUMBER.eq(1), tAutoNumbering.ENTITY_NUMBER.eq(2), tAutoNumbering.ENTITY_NUMBER.eq(3), tAutoNumbering.ENTITY_NUMBER.eq(4))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.EntityNumber AS \"asEntNum\" FROM $.AutoNumbering AS T0  " +
                        "WHERE (T0.EntityNumber > 1) " +
                        "AND NOT (T0.EntityType LIKE 'Α%') " +
                        "AND (T0.EntityType NOT LIKE 'B%') " +
                        "AND (T0.EntityNumber BETWEEN 2 AND 2000) " +
                        "AND (T0.EntityType IN (SELECT EntityType FROM $.AutoNumbering)) " +
                        "AND (T0.EntityNumber = 1 AND T0.EntityNumber = 2 AND T0.EntityNumber = 3 AND T0.EntityNumber = 4)"
                        : "SELECT T0.AASys_EntityNumber AS \"asEntNum\" FROM $.Sys_AutoNumbering AS T0  " +
                        "WHERE (T0.AASys_EntityNumber > 1) " +
                        "AND NOT (T0.AASys_EntityType LIKE 'Α%') " +
                        "AND (T0.AASys_EntityType NOT LIKE 'B%') " +
                        "AND (T0.AASys_EntityNumber BETWEEN 2 AND 2000) " +
                        "AND (T0.AASys_EntityType IN (SELECT AASys_EntityType FROM $.Sys_AutoNumbering)) " +
                        "AND (T0.AASys_EntityNumber = 1 AND T0.AASys_EntityNumber = 2 AND T0.AASys_EntityNumber = 3 AND T0.AASys_EntityNumber = 4)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames)
                        .from(tAutoNumbering.as(T0))
                        .select(tAutoNumbering.ENTITY_NUMBER.as("asEntNum"))
                        .where(tAutoNumbering.ENTITY_TYPE.like("Α%").or(tAutoNumbering.ENTITY_TYPE.like("B%").or(tAutoNumbering.ENTITY_TYPE.like("C%").or(tAutoNumbering.ENTITY_TYPE.like("D%").or(tAutoNumbering.ENTITY_TYPE.like("E%"))))))
                        .or(tAutoNumbering.ENTITY_TYPE.eq(1).or(tAutoNumbering.ENTITY_TYPE.eq(2).and(tAutoNumbering.ENTITY_NUMBER.gt(0))))
                        .getSQL(),
                normalizeNames
                        ? "SELECT T0.EntityNumber AS \"asEntNum\" FROM $.AutoNumbering AS T0  " +
                        "WHERE (T0.EntityType LIKE 'Α%' OR T0.EntityType LIKE 'B%' OR T0.EntityType LIKE 'C%' OR T0.EntityType LIKE 'D%' OR T0.EntityType LIKE 'E%') " +
                        "OR (T0.EntityType = '1' OR T0.EntityType = '2' AND T0.EntityNumber > 0)"
                        : "SELECT T0.AASys_EntityNumber AS \"asEntNum\" FROM $.Sys_AutoNumbering AS T0  " +
                        "WHERE (T0.AASys_EntityType LIKE 'Α%' OR T0.AASys_EntityType LIKE 'B%' OR T0.AASys_EntityType LIKE 'C%' OR T0.AASys_EntityType LIKE 'D%' OR T0.AASys_EntityType LIKE 'E%') " +
                        "OR (T0.AASys_EntityType = '1' OR T0.AASys_EntityType = '2' AND T0.AASys_EntityNumber > 0)"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering)
                        .select(CASE1n(11,
                                WHEN(tAutoNumbering.ENTITY_TYPE.eq(DbFValues.ValuesForEntityType.SURROGATE_NUM), 22),
                                WHEN(tAutoNumbering.ENTITY_TYPE.lt(DbFValues.ValuesForEntityType.SURROGATE_NUM), 33)))
                        .select(CASE2s(tAutoNumbering.ENTITY_TYPE, "11",
                                WHEN(2, "22"),
                                WHEN(3, "33")))
                        .setApplyAutoAlias()
                        .getSQL(),
                normalizeNames
                        ? "SELECT CASE  " +
                            "WHEN EntityType = 'E02' THEN 22 " +
                            "WHEN EntityType < 'E02' THEN 33 " +
                            "ELSE 11 END, " +
                        "CASE EntityType AS \"Entity_Type\" " +
                            "WHEN '2' THEN '22' " +
                            "WHEN '3' THEN '33' " +
                            "ELSE '11' END " +
                        "FROM $.AutoNumbering"
                        : "SELECT CASE  " +
                            "WHEN AASys_EntityType = 'E02' THEN 22 " +
                            "WHEN AASys_EntityType < 'E02' THEN 33 " +
                            "ELSE 11 END, " +
                        "CASE AASys_EntityType AS \"Entity_Type\" " +
                            "WHEN '2' THEN '22' " +
                            "WHEN '3' THEN '33' " +
                            "ELSE '11' END FROM $.Sys_AutoNumbering"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).updateInto(tAutoNumbering).updateFieldSetValue(tAutoNumbering.ENTITY_NUMBER, tOptions.OPTION_VALUE)
                        .getSQL(),
                normalizeNames
                        ? "UPDATE $.AutoNumbering SET EntityNumber = OptionValue"
                        : "UPDATE $.Sys_AutoNumbering SET AASys_EntityNumber = ABSys_OptionValue"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).updateInto(tAutoNumbering).updateFieldSetValue(tAutoNumbering.ENTITY_NUMBER, 1)
                        .getSQL(),
                normalizeNames
                        ? "UPDATE $.AutoNumbering SET EntityNumber = 1"
                        : "UPDATE $.Sys_AutoNumbering SET AASys_EntityNumber = 1"));

        stmts.add(checkResult(J2SQL.create(workDataSource, normalizeNames).updateInto(tAutoNumbering).updateFieldSetValue(tAutoNumbering.ENTITY_NUMBER, operation(tAutoNumbering.ENTITY_NUMBER, "+1"))
                        .getSQL(),
                normalizeNames
                        ? "UPDATE $.AutoNumbering SET EntityNumber = EntityNumber  +1"
                        : "UPDATE $.Sys_AutoNumbering SET AASys_EntityNumber = AASys_EntityNumber  +1"));


    }

}
