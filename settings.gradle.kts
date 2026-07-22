pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

if (!file(".git").exists()) {
    val errorText = """

        =====================[ ERROR ]=====================
         The Crisp project directory is not a properly cloned Git repository.

         In order to build Crisp from source you must clone
         the Crisp repository using Git, not download a code
         zip from GitHub.

         Built Crisp jars are available for download from the
         GitHub releases at https://github.com/madebywaffle/Crisp/releases

         See CONTRIBUTING.md for further information on building
         and modifying Crisp.
        ===================================================
    """.trimIndent()
    error(errorText)
}

rootProject.name = "crisp"

for (name in listOf("crisp-api", "crisp-server")) {
    include(name)
    file(name).mkdirs()
}

// Git can't track empty directories; create the patch directories on a fresh
// checkout so paperweight reads them as empty rather than missing.
listOf(
    "crisp-api/paper-patches",
    "crisp-server/paper-patches",
    "crisp-server/minecraft-patches/features",
    "crisp-server/minecraft-patches/sources",
).forEach { file(it).mkdirs() }
