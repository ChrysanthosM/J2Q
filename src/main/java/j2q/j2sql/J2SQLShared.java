package j2q.j2sql;

import j2q.core.linSQL.LinSQL;
import j2q.core.sqlCreator.sqlResolvers.sqlFilters.*;
import j2q.core.sqlCreator.sqlResolvers.sqlUserField.SQLFieldFromPairOfTableField;
import j2q.definitions.design.schema.PairOfTableField;
import j2q.db.model.GlobalFieldModelDefinition;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import j2q.core.sqlCreator.sqlResolvers.sqlFunctions.SQLFunction;
import j2q.core.sqlRetriever.IDeploySQLFunctions;
import jdk.jfr.Description;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface J2SQLShared {
    enum TypeOfSQLStatement {
        SQL_SELECT,
        SQL_DELETE,
        SQL_UPDATE,
        SQL_INSERT
    }

    enum PFX {
        A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, AA, AB, AC, AD, AE, AF, AG, AH, AI, AJ, AK, AL, AM, AN, AO, AP, AQ, AR, AS, AT, AU, AV, AW, AX, AY, AZ,
        T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, TA, TB, TC, TD, TE, TF, TG, TH, TI, TJ, TK, TL, TM, TN, TO, TP, TQ, TR, TS, TT, TU, TV, TW, TX, TY, TZ,
        J0, J1, J2, J3, J4, J5, J6, J7, J8, J9, JA, JB, JC, JD, JE, JF, JG, JH, JI, JJ, JK, JL, JM, JN, JO, JP, JQ, JR, JS, JT, JU, JV, JW, JX, JY, JZ,
        ;
        public static SQLFieldFromPairOfTableField a0(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A0.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a1(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A1.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a2(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A2.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a3(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A3.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a4(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A4.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a5(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A5.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a6(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A6.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a7(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A7.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a8(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A8.name().concat(".")); }
        public static SQLFieldFromPairOfTableField a9(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.A9.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aa(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AA.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ab(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AB.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ac(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AC.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ad(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AD.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ae(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AE.name().concat(".")); }
        public static SQLFieldFromPairOfTableField af(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AF.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ag(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AG.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ah(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AH.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ai(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AI.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aj(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AJ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ak(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AK.name().concat(".")); }
        public static SQLFieldFromPairOfTableField al(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AL.name().concat(".")); }
        public static SQLFieldFromPairOfTableField am(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AM.name().concat(".")); }
        public static SQLFieldFromPairOfTableField an(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AN.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ao(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AO.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ap(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AP.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aq(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AQ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ar(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AR.name().concat(".")); }
        public static SQLFieldFromPairOfTableField as(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AS.name().concat(".")); }
        public static SQLFieldFromPairOfTableField at(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AT.name().concat(".")); }
        public static SQLFieldFromPairOfTableField au(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AU.name().concat(".")); }
        public static SQLFieldFromPairOfTableField av(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AV.name().concat(".")); }
        public static SQLFieldFromPairOfTableField aw(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AW.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ax(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AX.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ay(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AY.name().concat(".")); }
        public static SQLFieldFromPairOfTableField az(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.AZ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t0(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T0.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t1(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T1.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t2(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T2.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t3(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T3.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t4(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T4.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t5(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T5.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t6(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T6.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t7(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T7.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t8(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T8.name().concat(".")); }
        public static SQLFieldFromPairOfTableField t9(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.T9.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ta(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TA.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tb(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TB.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tc(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TC.name().concat(".")); }
        public static SQLFieldFromPairOfTableField td(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TD.name().concat(".")); }
        public static SQLFieldFromPairOfTableField te(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TE.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tf(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TF.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tg(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TG.name().concat(".")); }
        public static SQLFieldFromPairOfTableField th(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TH.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ti(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TI.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tj(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TJ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tk(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TK.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tl(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TL.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tm(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TM.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tn(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TN.name().concat(".")); }
        public static SQLFieldFromPairOfTableField to(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TO.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tp(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TP.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tq(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TQ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tr(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TR.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ts(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TS.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tt(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TT.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tu(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TU.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tv(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TV.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tw(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TW.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tx(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TX.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ty(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TY.name().concat(".")); }
        public static SQLFieldFromPairOfTableField tz(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.TZ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j0(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J0.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j1(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J1.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j2(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J2.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j3(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J3.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j4(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J4.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j5(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J5.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j6(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J6.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j7(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J7.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j8(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J8.name().concat(".")); }
        public static SQLFieldFromPairOfTableField j9(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.J9.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ja(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JA.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jb(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JB.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jc(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JC.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jd(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JD.name().concat(".")); }
        public static SQLFieldFromPairOfTableField je(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JE.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jf(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JF.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jg(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JG.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jh(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JH.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ji(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JI.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jj(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JJ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jk(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JK.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jl(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JL.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jm(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JM.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jn(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JN.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jo(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JO.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jp(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JP.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jq(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JQ.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jr(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JR.name().concat(".")); }
        public static SQLFieldFromPairOfTableField js(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JS.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jt(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JT.name().concat(".")); }
        public static SQLFieldFromPairOfTableField ju(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JU.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jv(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JV.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jw(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JW.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jx(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JX.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jy(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JY.name().concat(".")); }
        public static SQLFieldFromPairOfTableField jz(@Nonnull PairOfTableField prefixedDbF) { return new SQLFieldFromPairOfTableField(prefixedDbF, null, PFX.JZ.name().concat(".")); }
    }

    static IWhere not(@Nonnull IWhere filter) {
        ((AbstractFilter) filter).setInvertSelection(true);
        return filter;
    }


    //-------Create Filters
    final class Filter {
        @Description("Filter Where Value")
        public static IWhere whereValue(@Nonnull Object whereObject, @Nullable LinSQL.TypeOfComparison typeOfComparison, @Nullable Object compareValue) {
            return new ValueWhere(whereObject, typeOfComparison, compareValue);
        }
//        @Description("Filter Where Value with Prefixed Where")
//        public static IWhere whereValue(@Nonnull J2SQL.PFX wherePrefix, @Nonnull GlobalFieldsDefinition.DbF whereDbField, @Nullable J2SQL.TypeOfComparison typeOfComparison, @Nullable Object compareValue) {
//            return new ValuePrefixedWhere(wherePrefix, whereDbField, typeOfComparison, null, compareValue);
//        }
//        @Description("Filter Where Value with Prefixed Value")
//        public static IWhere whereValue(@Nonnull Object whereObject, @Nullable J2SQL.TypeOfComparison typeOfComparison, @Nonnull J2SQL.PFX comparePrefix, @Nullable GlobalFieldsDefinition.DbF compareValue) {
//            return new ValuePrefixedWhere(null, whereObject, typeOfComparison, comparePrefix, compareValue);
//        }
//        @Description("Filter Where Value with Prefixed Where and Value")
//        public static IWhere whereValue(@Nonnull J2SQL.PFX wherePrefix, @Nonnull GlobalFieldsDefinition.DbF whereDbField, @Nullable J2SQL.TypeOfComparison typeOfComparison, @Nonnull J2SQL.PFX comparePrefix, @Nullable GlobalFieldsDefinition.DbF compareValue) {
//            return new ValuePrefixedWhere(wherePrefix, whereDbField, typeOfComparison, comparePrefix, compareValue);
//        }

        @Description("Filter Where IN Values")
        public static IWhere whereInValues(@Nonnull Object whereObject, @Nonnull List<Object> inValues) {
            return new InValuesWhere(whereObject, inValues);
        }
        @Description("Filter Where IN Values")
        public static IWhere whereInValues(@Nonnull Object whereObject, @Nonnull Object... inValues) {
            return new InValuesWhere(whereObject, List.of(inValues));
        }

        @Description("Filter Where BETWEEN Values")
        public static IWhere whereBetweenValues(@Nonnull Object whereObject, @Nonnull Object fromObject, @Nonnull Object toObject) {
            return new BetweenValuesWhere(whereObject, MutablePair.of(fromObject, toObject));
        }

        @Description("Filter Where LIKE Values")
        public static IWhere whereLikeValue(@Nonnull Object whereObject, @Nullable String compareValue) {
            return new LikeValueWhere(whereObject, compareValue, null, null);
        }
        @Description("Filter Where LIKE Values")
        public static IWhere whereLikeValue(@Nonnull Object whereObject, @Nullable String compareValue, @Nullable String escapeLeft, @Nullable String escapeRight) {
            return new LikeValueWhere(whereObject, compareValue, escapeLeft, escapeRight);
        }

        @Description("Filter Where IN SubSelect")
        public static IWhere whereInSubSelect(@Nonnull Object whereObject, @Nonnull String inSubSelect) {
            return new InSubSelectWhere(whereObject, inSubSelect);
        }

        @Description("Filter Where EXISTS")
        public static IWhere whereExists(@Nonnull String existQuery) {
            return new ExistsWhere(existQuery);
        }
        @Description("Filter Where EXISTS")
        public static IWhere whereExists(@Nonnull LinSQL existQuery) {
            return new ExistsWhere(existQuery.getSQL());
        }
    }


    //-------SQLFunctions
    final class SQLFunctionObject implements IDeployFilters, IProvideDataTypeForSQL {
        private final SQLFunction sqlFunction;
        private static SQLFunctionObject of(SQLFunction sqlFunction) { return new SQLFunctionObject(sqlFunction); }
        private SQLFunctionObject(SQLFunction sqlFunction) {
            this.sqlFunction = sqlFunction;
        }
        public Object getSqlFunction() { return sqlFunction; }

        @Override
        public GlobalFieldModelDefinition.DataTypeForSQL getDataTypeForSQL() {
            return this.sqlFunction.getTypeOfSQLFunction().getDataTypeForSQL();
        }
    }

    static SQLFunctionObject TRIM(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.TRIM, arg)); }
    static SQLFunctionObject LTRIM(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.LTRIM, arg)); }
    static SQLFunctionObject RTRIM(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.RTRIM, arg)); }
    static SQLFunctionObject LENGTH(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.LENGTH, arg)); }
    static SQLFunctionObject UPPER(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.UPPER, arg)); }
    static SQLFunctionObject LOWER(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.LOWER, arg)); }
    static SQLFunctionObject INITCAP(@Nonnull Object arg) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.INITCAP, arg)); }
    static SQLFunctionObject SPACE(int numOfSpaces) {
        Preconditions.checkArgument(numOfSpaces >= 0);
        return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.SPACE, Integer.valueOf(numOfSpaces)));
    }
    static SQLFunctionObject INSTR(@Nonnull Object arg, @Nonnull Object find) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.INSTR, Stream.of(arg, find).toArray())); }
    static SQLFunctionObject LEFT(@Nonnull Object arg, int len) {
        Preconditions.checkArgument(len > 0);
        return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.LEFT, Stream.of(arg, len).toArray()));
    }
    static SQLFunctionObject RIGHT(@Nonnull Object arg, int len) {
        Preconditions.checkArgument(len > 0);
        return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.RIGHT, Stream.of(arg, len).toArray()));
    }
    static SQLFunctionObject REPEAT(@Nonnull Object arg, int times) {
        Preconditions.checkArgument(times > 0);
        return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.REPEAT, Stream.of(arg, times).toArray()));
    }
    static SQLFunctionObject REPLACE(@Nonnull Object arg, @Nonnull Object find, @Nonnull Object replaceWith) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.REPLACE, Stream.of(arg, find, replaceWith).toArray())); }
    static SQLFunctionObject SUBSTR(@Nonnull Object arg, int from, int len) {
        Preconditions.checkArgument(from > 0);
        Preconditions.checkArgument(len > 0);
        return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.SUBSTR, Stream.of(arg, from, len).toArray()));
    }
    static SQLFunctionObject LPAD(@Nonnull Object arg, @Nonnull Object totalLength, @Nonnull Object leadingChar) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.RPAD, Stream.of(arg, totalLength, leadingChar).toArray())); }
    static SQLFunctionObject RPAD(@Nonnull Object arg, @Nonnull Object totalLength, @Nonnull Object trailingChar) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.RPAD, Stream.of(arg, totalLength, trailingChar).toArray())); }
    static SQLFunctionObject CONCAT(@Nonnull Object... args) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.CONCAT, args)); }
    static SQLFunctionObject TRANSLATE(@Nonnull Object arg, @Nonnull Object find, @Nonnull Object replaceWith, @Nullable Object pad) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.TRANSLATE, Stream.of(arg, find, replaceWith, pad).toArray())); }

    static SQLFunctionObject CASE(GlobalFieldModelDefinition.DataTypeForSQL dataTypeForSQL, Object caseExpression, Object elseExpression, IWhen... args) {
        List<Object> argList = Lists.newArrayList(dataTypeForSQL, Optional.ofNullable(caseExpression), elseExpression);
        argList.addAll(Lists.newArrayList(args));
        return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.CASE, argList.toArray()));
    }
    static SQLFunctionObject CASE1s(@Nullable Object elseExpression, @Nonnull IWhen... args) { return CASE(GlobalFieldModelDefinition.DataTypeForSQL.TEXT, null, elseExpression, args); }
    static SQLFunctionObject CASE1n(@Nullable Object elseExpression, @Nonnull IWhen... args) { return CASE(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC, null, elseExpression, args); }
    static IWhen WHEN(@Nonnull IWhere searchCondition, @Nonnull Object thenExpression) { return new WhenThenSearched(searchCondition, thenExpression); }
    static SQLFunctionObject CASE2s(@Nonnull Object caseExpression, @Nullable Object elseExpression, @Nonnull IWhen... args) { return CASE(GlobalFieldModelDefinition.DataTypeForSQL.TEXT, caseExpression, elseExpression, args); }
    static SQLFunctionObject CASE2n(@Nonnull Object caseExpression, @Nullable Object elseExpression, @Nonnull IWhen... args) { return CASE(GlobalFieldModelDefinition.DataTypeForSQL.NUMERIC, caseExpression, elseExpression, args); }
    static IWhen WHEN(@Nonnull Object whenExpression, @Nonnull Object thenExpression) { return new WhenThenSimple(whenExpression, thenExpression); }

    static SQLFunctionObject MIN(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.MIN, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject MIN(boolean distinct) { return MIN(null, distinct); }
    static SQLFunctionObject MIN(@Nullable Object arg) { return MIN(arg, false); }
    static SQLFunctionObject MIN() { return MIN(null, false); }
    static SQLFunctionObject MAX(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.MAX, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject MAX(boolean distinct) { return MAX(null, distinct); }
    static SQLFunctionObject MAX(@Nullable Object arg) { return MAX(arg, false); }
    static SQLFunctionObject MAX() { return MAX(null, false); }
    static SQLFunctionObject AVG(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.AVG, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject AVG(boolean distinct) { return AVG(null, distinct); }
    static SQLFunctionObject AVG(@Nullable Object arg) { return AVG(arg, false); }
    static SQLFunctionObject AVG() { return AVG(null, false); }
    static SQLFunctionObject COUNT(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.COUNT, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject COUNT(boolean distinct) { return COUNT(null, distinct); }
    static SQLFunctionObject COUNT(@Nullable Object arg) { return COUNT(arg, false); }
    static SQLFunctionObject COUNT() { return COUNT(null, false); }
    static SQLFunctionObject COUNT_BIG(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.COUNT_BIG, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject COUNT_BIG(boolean distinct) { return COUNT_BIG(null, distinct); }
    static SQLFunctionObject COUNT_BIG(@Nullable Object arg) { return COUNT_BIG(arg, false); }
    static SQLFunctionObject COUNT_BIG() { return COUNT_BIG(null, false); }
    static SQLFunctionObject SUM(@Nullable Object arg, boolean distinct) { return SQLFunctionObject.of(IDeploySQLFunctions.create(IDeploySQLFunctions.TypeOfSQLFunction.SUM, Stream.of(distinct, arg).toArray())); }
    static SQLFunctionObject SUM(boolean distinct) { return SUM(null, distinct); }
    static SQLFunctionObject SUM(@Nullable Object arg) { return SUM(arg, false); }
    static SQLFunctionObject SUM() { return SUM(null, false); }

}
