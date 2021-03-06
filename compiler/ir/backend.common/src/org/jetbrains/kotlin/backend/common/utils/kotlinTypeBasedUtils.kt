/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.common.utils

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.toIrType
import org.jetbrains.kotlin.ir.types.toKotlinType
import org.jetbrains.kotlin.ir.util.SymbolTable
import org.jetbrains.kotlin.resolve.DescriptorUtils
import org.jetbrains.kotlin.types.CommonSupertypes
import org.jetbrains.kotlin.types.typeUtil.isSubtypeOf

// TODO: implement pure Ir-based function (see IrTypeUtils.kt)

@Deprecated("Use pure Ir helper")
fun IrType.isPrimitiveArray() = KotlinBuiltIns.isPrimitiveArray(toKotlinType())

@Deprecated("Use pure Ir helper")
fun IrType.getPrimitiveArrayElementType() = KotlinBuiltIns.getPrimitiveArrayElementType(toKotlinType())

@Deprecated("Use pure Ir helper")
fun List<IrType>.commonSupertype(symbolTable: SymbolTable) =
    CommonSupertypes.commonSupertype(map(IrType::toKotlinType)).toIrType(symbolTable)!!

@Deprecated("Use pure Ir helper")
fun IrType.isSubtypeOf(superType: IrType) = toKotlinType().isSubtypeOf(superType.toKotlinType())

@Deprecated("Use pure Ir helper")
fun IrType.isSubtypeOfClass(superClass: IrClassSymbol) = DescriptorUtils.isSubtypeOfClass(toKotlinType(), superClass.descriptor)
