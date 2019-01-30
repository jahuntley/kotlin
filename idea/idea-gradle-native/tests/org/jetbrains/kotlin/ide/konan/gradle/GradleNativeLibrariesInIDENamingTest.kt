/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ide.konan.gradle

import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.LibraryOrderEntry
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.libraries.Library
import com.intellij.openapi.vfs.VfsUtilCore.urlToPath
import com.intellij.util.io.readText
import org.jetbrains.kotlin.ide.konan.NativeLibraryKind
import org.jetbrains.kotlin.ide.konan.gradle.GradleNativeLibrariesInIDENamingTest.Companion.NATIVE_LINUX_LIBRARIES
import org.jetbrains.kotlin.ide.konan.gradle.GradleNativeLibrariesInIDENamingTest.Companion.NATIVE_MACOS_LIBRARIES
import org.jetbrains.kotlin.ide.konan.gradle.GradleNativeLibrariesInIDENamingTest.Companion.NATIVE_MINGW_LIBRARIES
import org.jetbrains.kotlin.idea.codeInsight.gradle.GradleImportingTestCase
import org.jetbrains.kotlin.idea.configuration.externalCompilerVersion
import org.jetbrains.kotlin.idea.framework.detectLibraryKind
import org.jetbrains.kotlin.idea.project.platform
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.konan.library.konanCommonLibraryPath
import org.jetbrains.kotlin.konan.library.konanPlatformLibraryPath
import org.jetbrains.kotlin.platform.impl.isKotlinNative
import org.jetbrains.plugins.gradle.util.GradleConstants
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runners.Parameterized
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class GradleNativeLibrariesInIDENamingTest : GradleImportingTestCase() {

    // Test naming of Kotlin/Native libraries in projects with Gradle plugin 1.3.21+
    @Test
    fun testLibrariesNaming() {
        configureProject()
        importProject()

        myProject.allModules().forEach { assertValidModule(it, projectPath) }
    }

    // Test naming of Kotlin/Native libraries in projects with Gradle plugin 1.3.20 or earlier
    @Test
    fun testLegacyLibrariesNaming() {
        configureProject()
        importProject()

        myProject.allModules().forEach { assertValidModule(it, projectPath) }
    }

    override fun getExternalSystemConfigFileName() = GradleConstants.KOTLIN_DSL_SCRIPT_NAME

    override fun testDataDirName() = "nativeLibraries"

    private fun configureProject() {
        configureByFiles()

        // include data dir with fake Kotlin/Native libraries
        val testSuiteDataDir = testDataDirectory().toPath().parent
        val kotlinNativeHome = testSuiteDataDir.resolve(FAKE_KOTLIN_NATIVE_HOME_RELATIVE_PATH)

        Files.walk(kotlinNativeHome)
            .filter { Files.isRegularFile(it) }
            .forEach { createProjectSubFile(it.toRelativePath(testSuiteDataDir).toString(), it.readText()) }
    }

    companion object {
        @Parameterized.Parameters(name = "{index}: with Gradle-{0}")
        @Throws(Throwable::class)
        @JvmStatic
        fun data() = listOf(arrayOf("4.10.2"))

        internal val NATIVE_LINUX_LIBRARIES = listOf(
            "Kotlin/Native {version} - stdlib",
            "Kotlin/Native {version} - linux [linux_x64]",
            "Kotlin/Native {version} - posix [linux_x64]",
            "Kotlin/Native {version} - zlib [linux_x64]"
        )

        internal val NATIVE_MACOS_LIBRARIES = listOf(
            "Kotlin/Native {version} - stdlib",
            "Kotlin/Native {version} - Foundation [macos_x64]",
            "Kotlin/Native {version} - UIKit [macos_x64]",
            "Kotlin/Native {version} - objc [macos_x64]"
        )

        internal val NATIVE_MINGW_LIBRARIES = listOf(
            "Kotlin/Native {version} - stdlib",
            "Kotlin/Native {version} - iconv [mingw_x64]",
            "Kotlin/Native {version} - opengl32 [mingw_x64]",
            "Kotlin/Native {version} - windows [mingw_x64]"
        )
    }
}

private val FAKE_KOTLIN_NATIVE_HOME_RELATIVE_PATH = Paths.get("kotlin-native-data-dir", "kotlin-native-PLATFORM-VERSION")
private val NATIVE_LIBRARY_NAME_REGEX = Regex("^Kotlin/Native ([\\d\\w\\.-]+) - ([\\w\\d]+)( \\[([\\w\\d_]+)\\])?$")

private fun Path.toRelativePath(basePath: Path) = basePath.relativize(this)

private fun String.toRelativePath(basePath: String) = Paths.get(this).toRelativePath(Paths.get(basePath))

private val Module.libraries
    get() = ModuleRootManager.getInstance(this).orderEntries
        .asSequence()
        .filterIsInstance<LibraryOrderEntry>()
        .mapNotNull { it.library }

private fun assertValidModule(module: Module, projectPath: String) {
    val (nativeLibraries, otherLibraries) = module.libraries.partition { library ->
        detectLibraryKind(library.getFiles(OrderRootType.CLASSES)) == NativeLibraryKind
    }

    if (module.platform.isKotlinNative) {
        assertFalse("No Kotlin/Native libraries in $module", nativeLibraries.isEmpty())
        nativeLibraries.forEach { assertValidNativeLibrary(it, projectPath) }

        val kotlinVersion = requireNotNull(module.externalCompilerVersion) {
            "External compiler version shoul not be null"
        }

        val expectedNativeLibraryNames = when {
            module.name.contains("linux", ignoreCase = true) -> NATIVE_LINUX_LIBRARIES
            module.name.contains("macos", ignoreCase = true) -> NATIVE_MACOS_LIBRARIES
            module.name.contains("mingw", ignoreCase = true) -> NATIVE_MINGW_LIBRARIES
            else -> emptyList()
        }.map { it.replace("{version}", kotlinVersion) }.sorted()

        val actualNativeLibraryNames = nativeLibraries.map { it.name.orEmpty() }.sorted()

        assertEquals("Different set of Kotlin/Native libraries in $module", expectedNativeLibraryNames, actualNativeLibraryNames)

    } else {
        assertTrue("Unexpected Kotlin/Native libraries in $module: $nativeLibraries", nativeLibraries.isEmpty())
    }

    otherLibraries.forEach(::assertValidNonNativeLibrary)
}

private fun assertValidNativeLibrary(library: Library, projectPath: String) {
    val fullName = library.name.orEmpty()

    val result = NATIVE_LIBRARY_NAME_REGEX.matchEntire(fullName)
    assertTrue("Invalid Kotlin/Native library name: $fullName", result?.groups?.size == 5)

    val (_, name, _, platform) = result!!.destructured

    val urls = library.getUrls(OrderRootType.CLASSES).toList()
    assertTrue("Kotlin/Native library $fullName has multiple roots (${urls.size}): $urls", urls.size == 1)

    val actualShortPath = urlToPath(urls.single()).toRelativePath(projectPath).toRelativePath(FAKE_KOTLIN_NATIVE_HOME_RELATIVE_PATH)
    val expectedShortPath = if (platform.isEmpty()) konanCommonLibraryPath(name) else konanPlatformLibraryPath(name, platform)
    assertEquals("The short path of $fullName does not match its location", expectedShortPath, actualShortPath)
}

private fun assertValidNonNativeLibrary(library: Library) {
    val name = library.name.orEmpty()
    assertFalse("Invalid non-native library name: $name", name.contains("Kotlin/Native"))
}
