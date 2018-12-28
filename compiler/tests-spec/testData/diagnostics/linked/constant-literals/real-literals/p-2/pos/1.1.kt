/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: constant-literals, real-literals -> paragraph 2 -> sentence 1
 * NUMBER: 1
 * DESCRIPTION: Simple real literals with an exponent mark.
 */

// TESTCASE NUMBER: 1
val value_1 = 0.0e0

// TESTCASE NUMBER: 2
val value_2 = 0.0e-00

// TESTCASE NUMBER: 3
val value_3 = 0.0E000

// TESTCASE NUMBER: 4
val value_4 = 0.0E+0000

// TESTCASE NUMBER: 5
val value_5 = 00.0e+0

// TESTCASE NUMBER: 6
val value_6 = 000.00e00

// TESTCASE NUMBER: 7
val value_7 = 0000.000E-000

// TESTCASE NUMBER: 8
val value_8 = 1.0E+1

// TESTCASE NUMBER: 9
val value_9 = 22.00e22

// TESTCASE NUMBER: 10
val value_10 = <!FLOAT_LITERAL_CONFORMS_ZERO!>333.000e-333<!>

// TESTCASE NUMBER: 11
val value_11 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>4444.0000e4444<!>

// TESTCASE NUMBER: 12
val value_12 = <!FLOAT_LITERAL_CONFORMS_ZERO!>55555.0E-55555<!>

// TESTCASE NUMBER: 13
val value_13 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>666666.00e666666<!>

// TESTCASE NUMBER: 14
val value_14 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>7777777.000E7777777<!>

// TESTCASE NUMBER: 15
val value_15 = <!FLOAT_LITERAL_CONFORMS_ZERO!>88888888.0000e-88888888<!>

// TESTCASE NUMBER: 16
val value_16 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>999999999.0E+999999999<!>

// TESTCASE NUMBER: 17
val value_17 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>0000000000.1234567890E999999999<!>

// TESTCASE NUMBER: 18
val value_18 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>123456789.23456789E+123456789<!>

// TESTCASE NUMBER: 19
val value_19 = 2345678.345678e00000000001

// TESTCASE NUMBER: 20
val value_20 = <!FLOAT_LITERAL_CONFORMS_ZERO!>34567.4567E-100000000000<!>

// TESTCASE NUMBER: 21
val value_21 = 456.56e-0

// TESTCASE NUMBER: 22
val value_22 = 5.65e000000000000

// TESTCASE NUMBER: 23
val value_23 = 654.7654E+010

// TESTCASE NUMBER: 24
val value_24 = 76543.876543E1

// TESTCASE NUMBER: 25
val value_25 = 8765432.98765432e-2

// TESTCASE NUMBER: 26
val value_26 = 987654321.0987654321E-3

// TESTCASE NUMBER: 27
val value_27 = 0.1111e4

// TESTCASE NUMBER: 28
val value_28 = 1.22222E-5

// TESTCASE NUMBER: 29
val value_29 = 9.33333e+6

// TESTCASE NUMBER: 30
val value_30 = 9.444444E7

// TESTCASE NUMBER: 31
val value_31 = 8.5555555e8

// TESTCASE NUMBER: 32
val value_32 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>2.66666666e308<!>

// TESTCASE NUMBER: 33
val value_33 = 3.777777777E-308

// TESTCASE NUMBER: 34
val value_34 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>7.8888888888e+309<!>

// TESTCASE NUMBER: 35
val value_35 = 6.99999999999e-309

// TESTCASE NUMBER: 36
val value_36 = <!FLOAT_LITERAL_CONFORMS_INFINITY!>7.888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888e+111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111<!>

// TESTCASE NUMBER: 37
val value_37 = 0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000

// TESTCASE NUMBER: 38
val value_38 = 0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e-000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000

// TESTCASE NUMBER: 39
val value_39 = 0.000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e+000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
