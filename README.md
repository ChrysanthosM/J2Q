J2Q = Java to Query

SpringBoot based


for example:

J2SQL.create(workDataSource, normalizeNames)
                        .from(tAutoNumbering.as(T0))
                        .select(tAutoNumbering.ENTITY_NUMBER.as("asEntNum"))
                        .where(tAutoNumbering.ENTITY_NUMBER.gt(1)).andNot(tAutoNumbering.ENTITY_TYPE.like("Α%")).and(tAutoNumbering.ENTITY_TYPE.notLike("B%"))
                        .and(tAutoNumbering.ENTITY_NUMBER.between(2, 2000))
                        .and(tAutoNumbering.ENTITY_TYPE.inSubSelect(J2SQL.create(workDataSource, normalizeNames).from(tAutoNumbering).select(tAutoNumbering.ENTITY_TYPE).getSQL()))
                        .and(tAutoNumbering.ENTITY_NUMBER.eq(1), tAutoNumbering.ENTITY_NUMBER.eq(2), tAutoNumbering.ENTITY_NUMBER.eq(3), tAutoNumbering.ENTITY_NUMBER.eq(4))
                        .setApplyAutoAlias()
                        .getSQL()

= "SELECT T0.EntityNumber AS \"asEntNum\" FROM $.AutoNumbering AS T0  " +
                        "WHERE (T0.EntityNumber > 1) " +
                        "AND NOT (T0.EntityType LIKE 'Α%') " +
                        "AND (T0.EntityType NOT LIKE 'B%') " +
                        "AND (T0.EntityNumber BETWEEN 2 AND 2000) " +
                        "AND (T0.EntityType IN (SELECT EntityType FROM $.AutoNumbering)) " +
                        "AND (T0.EntityNumber = 1 AND T0.EntityNumber = 2 AND T0.EntityNumber = 3 AND T0.EntityNumber = 4)"
