/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2024 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

import io.questdb.griffin.FunctionFactory;

open module io.questdb {
    requires transitive jdk.unsupported;
    requires static org.jetbrains.annotations;
    requires static java.management;
    requires jdk.management;

    uses io.questdb.griffin.FunctionFactory;
    exports io.questdb;
    exports io.questdb.cairo;
    exports io.questdb.cairo.vm;
    exports io.questdb.cairo.map;
    exports io.questdb.cairo.sql;
    exports io.questdb.cairo.pool;
    exports io.questdb.cairo.pool.ex;
    exports io.questdb.cairo.security;

    exports io.questdb.cutlass;
    exports io.questdb.cutlass.http;
    exports io.questdb.cutlass.http.processors;
    exports io.questdb.cutlass.http.ex;
    exports io.questdb.cutlass.json;
    exports io.questdb.cutlass.line;
    exports io.questdb.cutlass.line.udp;
    exports io.questdb.cutlass.line.tcp;
    exports io.questdb.cutlass.line.http;
    exports io.questdb.cutlass.pgwire;
    exports io.questdb.cutlass.text;
    exports io.questdb.cutlass.text.types;

    exports io.questdb.griffin;
    exports io.questdb.griffin.engine;
    exports io.questdb.griffin.model;
    exports io.questdb.griffin.engine.functions;
    exports io.questdb.griffin.engine.functions.rnd;
    exports io.questdb.griffin.engine.functions.bind;
    exports io.questdb.griffin.engine.functions.bool;
    exports io.questdb.griffin.engine.functions.cast;
    exports io.questdb.griffin.engine.functions.catalogue;
    exports io.questdb.griffin.engine.functions.columns;
    exports io.questdb.griffin.engine.functions.conditional;
    exports io.questdb.griffin.engine.functions.constants;
    exports io.questdb.griffin.engine.functions.date;
    exports io.questdb.griffin.engine.functions.eq;
    exports io.questdb.griffin.engine.functions.groupby;
    exports io.questdb.griffin.engine.functions.lt;
    exports io.questdb.griffin.engine.functions.math;
    exports io.questdb.griffin.engine.functions.regex;
    exports io.questdb.griffin.engine.functions.str;
    exports io.questdb.griffin.engine.functions.test;
    exports io.questdb.griffin.engine.functions.geohash;
    exports io.questdb.griffin.engine.functions.bin;
    exports io.questdb.griffin.engine.functions.lock;
    exports io.questdb.griffin.engine.functions.window;
    exports io.questdb.griffin.engine.functions.table;
    exports io.questdb.griffin.engine.groupby;
    exports io.questdb.griffin.engine.groupby.vect;
    exports io.questdb.griffin.engine.orderby;
    exports io.questdb.griffin.engine.window;
    exports io.questdb.griffin.engine.table;
    exports io.questdb.jit;
    exports io.questdb.std;
    exports io.questdb.std.datetime;
    exports io.questdb.std.datetime.microtime;
    exports io.questdb.std.datetime.millitime;
    exports io.questdb.std.str;
    exports io.questdb.std.ex;
    exports io.questdb.std.fastdouble;
    exports io.questdb.network;
    exports io.questdb.log;
    exports io.questdb.mp;
    exports io.questdb.tasks;
    exports io.questdb.metrics;
    exports io.questdb.cairo.vm.api;
    exports io.questdb.cairo.mig;
    exports io.questdb.griffin.engine.join;
    exports io.questdb.griffin.engine.ops;
    exports io.questdb.cairo.sql.async;
    exports io.questdb.cutlass.http.client;
    exports io.questdb.griffin.engine.functions.long128;
    exports io.questdb.cairo.wal;
    exports io.questdb.cairo.wal.seq;
    exports io.questdb.cutlass.auth;
    exports io.questdb.cutlass.line.tcp.auth;
    exports io.questdb.cairo.frm;
    exports io.questdb.cairo.frm.file;
    exports io.questdb.std.histogram.org.HdrHistogram;
    exports io.questdb.client;
    exports io.questdb.std.bytes;
    exports io.questdb.std.histogram.org.HdrHistogram.packedarray;
    exports io.questdb.client.impl;
    exports io.questdb.griffin.engine.groupby.hyperloglog;
    exports io.questdb.griffin.engine.functions.finance;
    exports io.questdb.std.json;
    exports io.questdb.griffin.engine.functions.json;
    exports io.questdb.std.filewatch;
    exports io.questdb.griffin.engine.table.parquet;

    provides FunctionFactory with

            // finance
            io.questdb.griffin.engine.functions.finance.LevelTwoPriceFunctionFactory,
            io.questdb.griffin.engine.functions.finance.SpreadFunctionFactory,
            io.questdb.griffin.engine.functions.finance.MidPriceFunctionFactory,
            io.questdb.griffin.engine.functions.finance.WeightedMidPriceFunctionFactory,


            // query activity functions
            io.questdb.griffin.engine.functions.activity.CancelQueryFunctionFactory,
            io.questdb.griffin.engine.functions.activity.QueryActivityFunctionFactory,

            // test functions
            io.questdb.griffin.engine.functions.test.TestDataUnavailableFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestMatchFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestOwnerCountingFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestLatchedCounterFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestSumXDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestNPEFactory,
            io.questdb.griffin.engine.functions.test.TestSumTDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestSumStringGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.test.TestSleepFunctionFactory,

            // bool
            io.questdb.griffin.engine.functions.bool.OrFunctionFactory,
            io.questdb.griffin.engine.functions.bool.AndFunctionFactory,
            io.questdb.griffin.engine.functions.bool.NotFunctionFactory,

            // [] operators
            io.questdb.griffin.engine.functions.array.StrArrayDereferenceFunctionFactory,
            io.questdb.griffin.engine.functions.array.IntArrayDereferenceHackFunctionFactory,
            // '=' operators
            io.questdb.griffin.engine.functions.eq.EqStrFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqVarcharStrFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqByteFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqShortFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqIntFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqLongFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqLong128FunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqLong256StrFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqLong256FunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqStrCharFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqSymStrFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqSymCharFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqSymLongFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqSymTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqTimestampCursorFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqCharCharFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqIntStrCFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqDateFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqBooleanCharFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqBinaryFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqGeoHashGeoHashFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqGeoHashStrFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqUuidFunctionFactory,
            io.questdb.griffin.engine.functions.eq.EqUuidStrFunctionFactory,

            //contains
            io.questdb.griffin.engine.functions.eq.ContainsIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.eq.ContainsEqIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.eq.NegContainsEqIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.eq.NegContainsIPv4FunctionFactory,

            //nullif
            io.questdb.griffin.engine.functions.conditional.NullIfCharFunctionFactory,
            io.questdb.griffin.engine.functions.conditional.NullIfDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.conditional.NullIfIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.conditional.NullIfIntFunctionFactory,
            io.questdb.griffin.engine.functions.conditional.NullIfLongFunctionFactory,
            io.questdb.griffin.engine.functions.conditional.NullIfStrFunctionFactory,

//                   '<' operator
            io.questdb.griffin.engine.functions.lt.LtDoubleVVFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtDateFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtIntFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtStrIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtCharFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtStrFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtLongFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtLong256FunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtStrVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtVarcharStrFunctionFactory,
            io.questdb.griffin.engine.functions.lt.LtVarcharFunctionFactory,

//                   '+' operator
            io.questdb.griffin.engine.functions.math.AddIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.AddLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.AddFloatFunctionFactory,
            io.questdb.griffin.engine.functions.math.AddDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.AddLong256FunctionFactory,
            io.questdb.griffin.engine.functions.date.AddLongToTimestampFunctionFactory,
//                     '-' operator,
            io.questdb.griffin.engine.functions.math.NegIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.NegDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.NegFloatFunctionFactory,
            io.questdb.griffin.engine.functions.math.NegLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.NegShortFunctionFactory,
            io.questdb.griffin.engine.functions.math.NegByteFunctionFactory,

            io.questdb.griffin.engine.functions.math.SubDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.SubIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.SubLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.SubTimestampFunctionFactory,
//                     '/' operator,
            io.questdb.griffin.engine.functions.math.DivDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.DivFloatFunctionFactory,
            io.questdb.griffin.engine.functions.math.DivLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.DivIntFunctionFactory,
//                     '%' operator,
            io.questdb.griffin.engine.functions.math.RemIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.RemLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.RemDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.RemFloatFunctionFactory,
//                     '*' operator,
            io.questdb.griffin.engine.functions.math.MulFloatFunctionFactory,
            io.questdb.griffin.engine.functions.math.MulDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.MulLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.MulIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.AbsIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.AbsShortFunctionFactory,
            io.questdb.griffin.engine.functions.math.AbsLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.AbsDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.LnDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.LogDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.SqrtDoubleFunctionFactory,
//                     'trigonometric'
            io.questdb.griffin.engine.functions.math.SinDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.CosDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.TanDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.CotDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.AsinDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.AcosDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.AtanDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.Atan2DoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.PIDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.RadiansDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.DegreesDoubleFunctionFactory,
//                     '~=',
            io.questdb.griffin.engine.functions.regex.MatchStrFunctionFactory,
            io.questdb.griffin.engine.functions.regex.MatchCharFunctionFactory,
            io.questdb.griffin.engine.functions.regex.MatchVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.regex.MatchSymbolFunctionFactory,
//                    like
            io.questdb.griffin.engine.functions.regex.LikeStrFunctionFactory,
            io.questdb.griffin.engine.functions.regex.LikeVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.regex.LikeSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.regex.ILikeStrFunctionFactory,
            io.questdb.griffin.engine.functions.regex.ILikeVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.regex.ILikeSymbolFunctionFactory,
//                     '!~',
            io.questdb.griffin.engine.functions.regex.NotMatchStrFunctionFactory,
            io.questdb.griffin.engine.functions.regex.NotMatchVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.regex.NotMatchCharFunctionFactory,
//                     'to_char',
            io.questdb.griffin.engine.functions.date.ToStrDateFunctionFactory,
            io.questdb.griffin.engine.functions.date.ToStrTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.str.ToCharBinFunctionFactory,
//                     'length',
            io.questdb.griffin.engine.functions.str.LengthStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.LengthVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.LengthSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.str.LengthBinFunctionFactory,
//                     random generator functions,
            io.questdb.griffin.engine.functions.rnd.LongSequenceFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndIntFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndIPv4CCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndIntCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndStrFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndStrRndListFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndVarcharListFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndDoubleCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndFloatCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndShortCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndShortFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndDateCCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndLongCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndLongFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndUuidFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndUUIDCFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampSequenceFunctionFactory,
            io.questdb.griffin.engine.functions.long128.LongsToLong128FunctionFactory,
            io.questdb.griffin.engine.functions.long256.LongsToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.uuid.LongsToUuidFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampShuffleFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampFloorFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampFloorOffsetFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampCeilFunctionFactory,
            io.questdb.griffin.engine.functions.date.DateTruncFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndByteCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndBinCCCFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndSymbolListFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndStringListFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndCharFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndLong256FunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndLong256NFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndByteFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndFloatFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndBinFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndDateFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.ListFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndGeoHashFunctionFactory,
            io.questdb.griffin.engine.functions.rnd.RndLogFunctionFactory,
//                  date conversion functions,
            io.questdb.griffin.engine.functions.date.SysdateFunctionFactory,
            io.questdb.griffin.engine.functions.date.ToTimestampVCFunctionFactory,
            io.questdb.griffin.engine.functions.date.VarcharToTimestampVCFunctionFactory,
            io.questdb.griffin.engine.functions.date.ToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.date.VarcharToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.date.SystimestampFunctionFactory,
            io.questdb.griffin.engine.functions.date.NowFunctionFactory,
            io.questdb.griffin.engine.functions.date.HourOfDayFunctionFactory,
            io.questdb.griffin.engine.functions.date.DayOfMonthFunctionFactory,
            io.questdb.griffin.engine.functions.date.DayOfWeekFunctionFactory,
            io.questdb.griffin.engine.functions.date.DayOfWeekSundayFirstFunctionFactory,
            io.questdb.griffin.engine.functions.date.MinuteOfHourFunctionFactory,
            io.questdb.griffin.engine.functions.date.SecondOfMinuteFunctionFactory,
            io.questdb.griffin.engine.functions.date.WeekOfYearFunctionFactory,
            io.questdb.griffin.engine.functions.date.YearFunctionFactory,
            io.questdb.griffin.engine.functions.date.MonthOfYearFunctionFactory,
            io.questdb.griffin.engine.functions.date.DaysPerMonthFunctionFactory,
            io.questdb.griffin.engine.functions.date.ExtractFromTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.date.MicrosOfSecondFunctionFactory,
            io.questdb.griffin.engine.functions.date.MillisOfSecondFunctionFactory,
            io.questdb.griffin.engine.functions.date.IsLeapYearFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampDiffFunctionFactory,
            io.questdb.griffin.engine.functions.date.TimestampAddFunctionFactory,
            io.questdb.griffin.engine.functions.date.ToDateFunctionFactory,
            io.questdb.griffin.engine.functions.date.VarcharToDateFunctionFactory,
            io.questdb.griffin.engine.functions.date.ToPgDateFunctionFactory,
            io.questdb.griffin.engine.functions.date.VarcharToPgDateFunctionFactory,
            io.questdb.griffin.engine.functions.date.PgPostmasterStartTimeFunctionFactory,
//                  cast functions,
//                  cast double to ...,
            io.questdb.griffin.engine.functions.cast.CastDoubleToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDoubleToTimestampFunctionFactory,
//                  cast float to ...,
            io.questdb.griffin.engine.functions.cast.CastFloatToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastFloatToTimestampFunctionFactory,
//                  cast short to ...,
            io.questdb.griffin.engine.functions.cast.CastShortToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastShortToBooleanFunctionFactory,
//                  cast int to ...,
            io.questdb.griffin.engine.functions.cast.CastIntToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIntToIPv4FunctionFactory,
//                  cast ipv4 to ...
            io.questdb.griffin.engine.functions.cast.CastIPv4ToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIPv4ToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastIPv4ToIntFunctionFactory,
//                  cast long to ...,
            io.questdb.griffin.engine.functions.cast.CastLongToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastNullTypeFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLongToGeoHashFunctionFactory,
//                  cast long256 to ...,
            io.questdb.griffin.engine.functions.cast.CastLong256ToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastLong256ToBooleanFunctionFactory,
//                  cast date to ...,
            io.questdb.griffin.engine.functions.cast.CastDateToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastDateToBooleanFunctionFactory,
//                  cast timestamp to ...,
            io.questdb.griffin.engine.functions.cast.CastTimestampToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastTimestampToBooleanFunctionFactory,
//                  cast byte to ...,
            io.questdb.griffin.engine.functions.cast.CastByteToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastByteToBooleanFunctionFactory,
//                  cast boolean to ...,
            io.questdb.griffin.engine.functions.cast.CastBooleanToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastBooleanToBooleanFunctionFactory,
//                  cast char to ...,
            io.questdb.griffin.engine.functions.cast.CastCharToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToTimestampFunctionFactory,
//                  cast str to ...,
            io.questdb.griffin.engine.functions.cast.CastStrToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastCharToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToRegClassFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToRegProcedureFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToStrArrayFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToBinaryFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToGeoHashFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastStrToUuidFunctionFactory,

//                  cast varchar to ...,
            io.questdb.griffin.engine.functions.cast.CastVarcharToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToBooleanFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToGeoHashFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToUuidFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastVarcharToSymbolFunctionFactory,

//                  cast symbol to ...
            io.questdb.griffin.engine.functions.cast.CastSymbolToIntFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToFloatFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToLong256FunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToLongFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToShortFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToByteFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToCharFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToDateFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastSymbolToTimestampFunctionFactory,

//                  cast uuid to ...
            io.questdb.griffin.engine.functions.cast.CastUuidToStrFunctionFactory,
            io.questdb.griffin.engine.functions.cast.CastUuidToVarcharFunctionFactory,

//                  cast geohash to ...
            io.questdb.griffin.engine.functions.cast.CastGeoHashToGeoHashFunctionFactory,


            // cast helpers
            io.questdb.griffin.engine.functions.cast.VarcharCastHelperFunctionFactory,
//                  'in'
            io.questdb.griffin.engine.functions.bool.InSymbolCursorFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InStrFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InCharFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InLongFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InSymbolFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InTimestampStrFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InTimestampVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InTimestampTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.bool.BetweenTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.bool.InUuidFunctionFactory,
//                  'all'
            io.questdb.griffin.engine.functions.bool.AllNotEqStrFunctionFactory,
            io.questdb.griffin.engine.functions.bool.AllNotEqVarcharFunctionFactory,
//                  'agg' group by function
            io.questdb.griffin.engine.functions.groupby.StringAggGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.StringAggVarcharGroupByFunctionFactory,
//                  'sum' group by function
            io.questdb.griffin.engine.functions.groupby.SumDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.SumFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.SumIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.SumLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.SumLong256GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.KSumDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.NSumDoubleGroupByFunctionFactory,
//                  'last' group by function
            io.questdb.griffin.engine.functions.groupby.LastDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastCharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastShortGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastBooleanGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastByteGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastSymbolGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastVarcharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastTimestampGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastDateGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastGeoHashGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastUuidGroupByFunctionFactory,
//                  'last_not_null' group by function
            io.questdb.griffin.engine.functions.groupby.LastNotNullCharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullDateGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullGeoHashGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullVarcharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullSymbolGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullTimestampGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.LastNotNullUuidGroupByFunctionFactory,
//                  'first' group by function
            io.questdb.griffin.engine.functions.groupby.FirstDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstCharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstShortGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstBooleanGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstByteGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstTimestampGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstDateGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstGeoHashGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstUuidGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstSymbolGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstVarcharGroupByFunctionFactory,
//                  'first_not_null' group by function
            io.questdb.griffin.engine.functions.groupby.FirstNotNullCharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullDateGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullGeoHashGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullVarcharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullSymbolGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullTimestampGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.FirstNotNullUuidGroupByFunctionFactory,
//                  'max' group
            io.questdb.griffin.engine.functions.groupby.MaxDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxBooleanGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxCharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxTimestampGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxDateGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MaxVarcharGroupByFunctionFactory,
//                  'min' group
            io.questdb.griffin.engine.functions.groupby.MinDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinBooleanGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinCharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinTimestampGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinDateGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.MinVarcharGroupByFunctionFactory,
//                  'count' group by function
            io.questdb.griffin.engine.functions.groupby.CountGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountFloatGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountGeoHashGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountLong256GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountStrGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountVarcharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountSymbolGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctStringGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctVarcharGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctSymbolGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctLong256GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctIPv4GroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.CountDistinctUuidGroupByFunctionFactory,
//                  'approx_count_distinct' group by function
            io.questdb.griffin.engine.functions.groupby.ApproxCountDistinctIntGroupByDefaultFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxCountDistinctIntGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxCountDistinctLongGroupByDefaultFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxCountDistinctLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxCountDistinctIPv4GroupByDefaultFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxCountDistinctIPv4GroupByFunctionFactory,
            //      'haversine_dist_degree' group by function
            io.questdb.griffin.engine.functions.groupby.HaversineDistDegreeGroupByFunctionFactory,
            //      'approx_percentile' group by function
            io.questdb.griffin.engine.functions.groupby.ApproxPercentileDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxPercentileDoubleGroupByDefaultFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxPercentileLongGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.ApproxPercentileLongGroupByDefaultFunctionFactory,
//                  'isOrdered'
            io.questdb.griffin.engine.functions.groupby.IsIPv4OrderedGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.IsLongOrderedGroupByFunctionFactory,
//                  round()
            io.questdb.griffin.engine.functions.math.RoundDoubleZeroScaleFunctionFactory,
            io.questdb.griffin.engine.functions.math.RoundDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.RoundDownDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.RoundUpDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.RoundHalfEvenDoubleFunctionFactory,
//                  ceil()
            io.questdb.griffin.engine.functions.math.CeilDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.CeilFloatFunctionFactory,
//                  ceil()
            io.questdb.griffin.engine.functions.math.CeilingDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.CeilingFloatFunctionFactory,
//                  exp()
            io.questdb.griffin.engine.functions.math.ExpDoubleFunctionFactory,
//                  floor()
            io.questdb.griffin.engine.functions.math.FloorDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.FloorFloatFunctionFactory,
//                  sign()
            io.questdb.griffin.engine.functions.math.SignByteFunctionFactory,
            io.questdb.griffin.engine.functions.math.SignDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.math.SignFloatFunctionFactory,
            io.questdb.griffin.engine.functions.math.SignIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.SignLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.SignShortFunctionFactory,
//                  case conditional statement
            io.questdb.griffin.engine.functions.conditional.CaseFunctionFactory,
            io.questdb.griffin.engine.functions.conditional.SwitchFunctionFactory,
            io.questdb.griffin.engine.functions.conditional.CoalesceFunctionFactory,
//                  PostgreSQL catalogue functions
            io.questdb.griffin.engine.functions.catalogue.PgAttrDefFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgAttrDefFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgAttributeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgAttributeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgClassFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgClassFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgIndexFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgIndexFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgRolesFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgRolesFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.InformationSchemaFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.InformationSchemaColumnsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.InformationSchemaTablesFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgTypeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgDescriptionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgNamespaceFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgNamespaceFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgLocksFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgLocksFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.IsTableVisibleCatalogueFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.UserByIdCatalogueFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgTypeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.VersionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CurrentDatabaseFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedCurrentDatabaseFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CurrentSchemasFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedCurrentSchemasFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CurrentSettingFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CurrentSchemaFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedCurrentSchemaFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CurrentUserFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CursorDereferenceFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgDescriptionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgExtensionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgExtensionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgInheritsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgInheritsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.SessionUserFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgGetPartKeyDefFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgGetPartKeyDefFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgGetSITExprFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgGetSITExprFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgGetSIExprFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgGetSIExprFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.FormatTypeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgProcFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgRangeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgGetKeywordsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgGetKeywordsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.ShowTablesFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.KeywordsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.FunctionListFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.WalTableListFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.WalTransactionsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.DumpMemoryUsageFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.DumpThreadStacksFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.FlushQueryCacheFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedAgeFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgIsInRecoveryFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgIsInRecoveryFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.TxIDCurrentFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedTxIDCurrentFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgDatabaseFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedPgDatabaseFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PgShDescriptionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.SimulateCrashFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.SimulateWarningsFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.PrefixedVersionFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.TypeOfFunctionFactory,
            io.questdb.griffin.engine.functions.catalogue.CheckpointStatusFunctionFactory,

//            PostgreSQL advisory locks functions
            io.questdb.griffin.engine.functions.lock.AdvisoryUnlockAll,
//                  concat()
            io.questdb.griffin.engine.functions.str.ConcatFunctionFactory,
            // replace()
            io.questdb.griffin.engine.functions.str.ReplaceStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.ReplaceVarcharFunctionFactory,

            // json_extract()
            io.questdb.griffin.engine.functions.json.JsonExtractVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.json.JsonExtractTypedFunctionFactory,

            // regexp_replace()
            io.questdb.griffin.engine.functions.regex.RegexpReplaceStrFunctionFactory,
            io.questdb.griffin.engine.functions.regex.RegexpReplaceVarcharFunctionFactory,
//                  avg()
            io.questdb.griffin.engine.functions.groupby.AvgDoubleGroupByFunctionFactory,
            io.questdb.griffin.engine.functions.groupby.AvgBooleanGroupByFunctionFactory,
//                  vwap()
            io.questdb.griffin.engine.functions.groupby.VwapDoubleGroupByFunctionFactory,
//                 stddev()
            io.questdb.griffin.engine.functions.groupby.StdDevGroupByFunctionFactory,
//                 stddev_samp()
            io.questdb.griffin.engine.functions.groupby.StdDevSampleGroupByFunctionFactory,
//                 stddev_pop()
            io.questdb.griffin.engine.functions.groupby.StdDevPopGroupByFunctionFactory,
//                 variance()
            io.questdb.griffin.engine.functions.groupby.VarGroupByFunctionFactory,
//                 var_samp()
            io.questdb.griffin.engine.functions.groupby.VarSampleGroupByFunctionFactory,
//                 var_pop()
            io.questdb.griffin.engine.functions.groupby.VarPopGroupByFunctionFactory,
//                 covar_samp()
            io.questdb.griffin.engine.functions.groupby.CovarSampleGroupByFunctionFactory,
//                 covar_pop()
            io.questdb.griffin.engine.functions.groupby.CovarPopGroupByFunctionFactory,
//                 corr()
            io.questdb.griffin.engine.functions.groupby.CorrGroupByFunctionFactory,
//                  ^
            io.questdb.griffin.engine.functions.math.PowDoubleFunctionFactory,
            io.questdb.griffin.engine.functions.table.AllTablesFunctionFactory,
            io.questdb.griffin.engine.functions.table.TableColumnsFunctionFactory,
            io.questdb.griffin.engine.functions.table.TablePartitionsFunctionFactory,
            io.questdb.griffin.engine.functions.table.TableStorageFunctionFactory,
            io.questdb.griffin.engine.functions.table.TouchTableFunctionFactory,
            io.questdb.griffin.engine.functions.table.ReaderPoolFunctionFactory,
            io.questdb.griffin.engine.functions.table.WriterPoolFunctionFactory,
            io.questdb.griffin.engine.functions.table.TableWriterMetricsFunctionFactory,
            io.questdb.griffin.engine.functions.table.MemoryMetricsFunctionFactory,
            io.questdb.griffin.engine.functions.table.ReadParquetFunctionFactory,
            io.questdb.griffin.engine.functions.table.ParquetScanFunctionFactory,

            // strpos
            io.questdb.griffin.engine.functions.str.StrPosFunctionFactory,
            io.questdb.griffin.engine.functions.str.StrPosCharFunctionFactory,
            io.questdb.griffin.engine.functions.str.StrPosVarcharFunctionFactory,
            // position
            io.questdb.griffin.engine.functions.str.PositionFunctionFactory,
            io.questdb.griffin.engine.functions.str.PositionVarcharFunctionFactory,
            // Change string case
            io.questdb.griffin.engine.functions.str.ToUppercaseFunctionFactory,
            io.questdb.griffin.engine.functions.str.ToUppercaseVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.ToLowercaseFunctionFactory,
            io.questdb.griffin.engine.functions.str.ToLowercaseVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.LowerFunctionFactory,
            io.questdb.griffin.engine.functions.str.LowerVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.UpperFunctionFactory,
            io.questdb.griffin.engine.functions.str.UpperVarcharFunctionFactory,
            // left/right
            io.questdb.griffin.engine.functions.str.LeftStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.LeftVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.RightStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.RightVarcharFunctionFactory,
            // Pad strings
            io.questdb.griffin.engine.functions.str.LPadFunctionFactory,
            io.questdb.griffin.engine.functions.str.LPadStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.RPadFunctionFactory,
            io.questdb.griffin.engine.functions.str.RPadStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.LPadVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.LPadVarcharVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.RPadVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.RPadVarcharVarcharFunctionFactory,
            // size_pretty
            io.questdb.griffin.engine.functions.str.SizePrettyFunctionFactory,
            // substring
            io.questdb.griffin.engine.functions.str.SubStringFunctionFactory,
            io.questdb.griffin.engine.functions.str.SubStringVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.QuoteIdentFunctionFactory,
            io.questdb.griffin.engine.functions.str.QuoteIdentVarcharFunctionFactory,

            // trim
            io.questdb.griffin.engine.functions.str.TrimStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.LTrimStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.RTrimStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.TrimVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.LTrimVarcharFunctionFactory,
            io.questdb.griffin.engine.functions.str.RTrimVarcharFunctionFactory,

            // starts_with
            io.questdb.griffin.engine.functions.str.StartsWithStrFunctionFactory,
            io.questdb.griffin.engine.functions.str.StartsWithVarcharFunctionFactory,
            // split_part
            io.questdb.griffin.engine.functions.str.SplitPartFunctionFactory,
            io.questdb.griffin.engine.functions.str.SplitPartCharFunctionFactory,
            io.questdb.griffin.engine.functions.str.SplitPartVarcharFunctionFactory,

            // window functions
            io.questdb.griffin.engine.functions.window.RowNumberFunctionFactory,
            io.questdb.griffin.engine.functions.window.RankFunctionFactory,
            io.questdb.griffin.engine.functions.window.AvgDoubleWindowFunctionFactory,
            io.questdb.griffin.engine.functions.window.FirstValueDoubleWindowFunctionFactory,
            io.questdb.griffin.engine.functions.window.SumDoubleWindowFunctionFactory,

            // metadata functions
            io.questdb.griffin.engine.functions.metadata.BuildFunctionFactory,
            io.questdb.griffin.engine.functions.metadata.InstanceNameFunctionFactory,
            io.questdb.griffin.engine.functions.metadata.InstanceRGBFunctionFactory,
            // geohash functions
            io.questdb.griffin.engine.functions.geohash.GeoHashFromCoordinatesFunctionFactory,
            // bin functions
            io.questdb.griffin.engine.functions.bin.Base64FunctionFactory,
            // bit operations
            io.questdb.griffin.engine.functions.math.BitwiseAndLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseOrLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseNotLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseXorLongFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseAndIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseAndIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseAndIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseAndStrIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseOrIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseOrIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseOrStrIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseOrIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseNotIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseNotIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseNotIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.BitwiseXorIntFunctionFactory,

            // ipv4 operators
            io.questdb.griffin.engine.functions.math.IPv4PlusIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.IntPlusIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4MinusIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4StrMinusIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4MinusIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4StrPlusIntFunctionFactory,
            io.questdb.griffin.engine.functions.math.IntPlusIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4StrMinusIPv4StrFunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4StrMinusIPv4FunctionFactory,
            io.questdb.griffin.engine.functions.math.IPv4MinusIPv4StrFunctionFactory,

            // ipv4 functions
            io.questdb.griffin.engine.functions.math.IPv4StrNetmaskFunctionFactory,

            io.questdb.griffin.engine.functions.date.ToTimezoneTimestampFunctionFactory,
            io.questdb.griffin.engine.functions.date.ToUTCTimestampFunctionFactory
            ;
}
