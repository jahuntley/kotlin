plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    compile(project(":kotlin-native:kotlin-native-library-reader"))

    compileOnly(project(":idea:idea-gradle"))
    compileOnly(project(":idea:idea-native"))

    compileOnly(project(":idea")) { isTransitive = false }
    compileOnly(project(":idea:idea-jvm"))
    compile(project(":idea:kotlin-gradle-tooling"))

    compile(project(":compiler:frontend"))
    compile(project(":compiler:frontend.java"))
    compile(project(":compiler:frontend.script"))

    compile(project(":js:js.frontend"))

    compileOnly(intellijDep())
    compileOnly(intellijPluginDep("gradle"))
    compileOnly(intellijPluginDep("Groovy"))
    compileOnly(intellijPluginDep("junit"))

    testCompile(projectTests(":idea"))
    testCompile(projectTests(":idea:idea-gradle"))
    testCompile(projectTests(":idea:idea-test-framework"))

    testCompile(intellijPluginDep("gradle"))
    testCompileOnly(intellijPluginDep("Groovy"))
    testCompileOnly(intellijDep())

    testRuntime(project(":plugins:kapt3-idea"))
    testRuntime(project(":sam-with-receiver-ide-plugin"))
    testRuntime(project(":allopen-ide-plugin"))
    testRuntime(project(":noarg-ide-plugin"))
    testRuntime(project(":kotlin-scripting-idea"))
    testRuntime(project(":kotlinx-serialization-ide-plugin"))

    // TODO: the order of the plugins matters here, consider avoiding order-dependency
    testRuntime(intellijPluginDep("junit"))
    testRuntime(intellijPluginDep("gradle"))
    testRuntime(intellijPluginDep("Groovy"))

}

sourceSets {
    "main" { projectDefault() }
    "test" { projectDefault() }
}

testsJar()

projectTest {
    workingDir = rootDir
}

configureInstrumentation()
