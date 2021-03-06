package co.anitrend.arch.buildSrc.plugin.components

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.get
import org.jetbrains.dokka.gradle.DokkaTask
import co.anitrend.arch.buildSrc.module.Modules
import co.anitrend.arch.buildSrc.plugin.extensions.*
import co.anitrend.arch.buildSrc.common.Versions
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import java.io.File
import java.net.URL

private fun Project.dependenciesOfProject(): List<Modules.Module> {
    return when {
        project.isCoreModule() -> listOf(
            Modules.Support.Extensions,
            Modules.Support.Data,
            Modules.Support.Domain
        )
        project.isDataModule() -> listOf(
            Modules.Support.Extensions,
            Modules.Support.Domain
        )
        project.isRecyclerModule() -> listOf(
            Modules.Support.Extensions,
            Modules.Support.Core,
            Modules.Support.Theme,
            Modules.Support.Domain
        )
        project.isUiModule() -> listOf(
            Modules.Support.Extensions,
            Modules.Support.Core,
            Modules.Support.Theme,
            Modules.Support.Domain,
            Modules.Support.Recycler
        )
        else -> emptyList()
    }
}

@Suppress("UnstableApiUsage")
internal fun Project.configureOptions() {
    if (containsBasePlugin()) {
        println("Applying extension options for ${project.path}")

        val baseExt = baseExtension()
        val mainSourceSet = baseExt.sourceSets["main"].java.srcDirs

        println("Applying additional tasks options for dokka and javadoc on ${project.path}")

        val dokka = tasks.named<DokkaTask>("dokkaHtml") {
            outputDirectory.set(buildDir.resolve("docs/javadoc"))

            // Set module name displayed in the final output
            moduleName.set(project.name)

            // Use default or set to custom path to cache directory
            // to enable package-list caching
            // When this is set to default, caches are stored in $USER_HOME/.cache/dokka
            //cacheRoot.set(file("default"))

            dokkaSourceSets {
                configureEach { // Or source set name, for single-platform the default source sets are `main` and `test`
                    // Used when configuring source sets manually for declaring which source sets this one depends on
                    dependsOn(dependenciesOfProject().map(Modules.Module::path))

                    // Used to remove a source set from documentation, test source sets are suppressed by default
                    suppress.set(false)

                    // Used to prevent resolving package-lists online. When this option is set to true, only local files are resolved
                    offlineMode.set(false)

                    // Use to include or exclude non public members
                    includeNonPublic.set(false)

                    // Do not output deprecated members. Applies globally, can be overridden by packageOptions
                    skipDeprecated.set(false)

                    // Emit warnings about not documented members. Applies globally, also can be overridden by packageOptions
                    reportUndocumented.set(true)

                    // Do not create index pages for empty packages
                    skipEmptyPackages.set(true)

                    // This name will be shown in the final output
                    displayName.set("JVM")

                    // Platform used for code analysis. See the "Platforms" section of this readme
                    platform.set(org.jetbrains.dokka.Platform.jvm)

                    // Property used for manual addition of files to the classpath
                    // This property does not override the classpath collected automatically but appends to it
                    // classpath.from(file("libs/dependency.jar"))

                    // List of files with module and package documentation
                    // https://kotlinlang.org/docs/reference/kotlin-doc.html#module-and-package-documentation
                    //includes.from("packages.md", "extra.md")

                    // List of files or directories containing sample code (referenced with @sample tags)
                    //samples.from("samples/basic.kt", "samples/advanced.kt")

                    // By default, sourceRoots are taken from Kotlin Plugin and kotlinTasks, following roots will be appended to them
                    // Repeat for multiple sourceRoots
                    sourceRoot(file("src"))

                    // Specifies the location of the project source code on the Web.
                    // If provided, Dokka generates "source" links for each declaration.
                    // Repeat for multiple mappings
                    sourceLink {
                        // Unix based directory relative path to the root of the project (where you execute gradle respectively).
                        localDirectory.set(file("src/main/kotlin"))

                        val repository = "https://github.com/anitrend/support-arch/tree/develop"
                        // URL showing where the source code can be accessed through the web browser
                        remoteUrl.set(URL("$repository/${project.name}/src/main/kotlin"))
                        // Suffix which is used to append the line number to the URL. Use #L for GitHub
                        remoteLineSuffix.set("#L")
                    }

                    // Used for linking to JDK documentation
                    jdkVersion.set(8)

                    // Disable linking to online kotlin-stdlib documentation
                    noStdlibLink.set(false)

                    // Disable linking to online JDK documentation
                    noJdkLink.set(false)

                    // Disable linking to online Android documentation (only applicable for Android projects)
                    noAndroidSdkLink.set(false)

                    // Allows linking to documentation of the project"s dependencies (generated with Javadoc or Dokka)
                    // Repeat for multiple links
                    externalDocumentationLink {
                        // Root URL of the generated documentation to link with. The trailing slash is required!
                        url.set(URL("https://developer.android.com/reference/kotlin/"))

                        // If package-list file is located in non-standard location
                        packageListUrl.set(URL("https://developer.android.com/reference/androidx/package-list"))
                    }

                    // Allows to customize documentation generation options on a per-package basis
                    // Repeat for multiple packageOptions
                    // If multiple packages match the same matchingRegex, the longuest matchingRegex will be used
                    perPackageOption {
                        matchingRegex.set("kotlin($|\\.).*") // will match kotlin and all sub-packages of it
                        // All options are optional, default values are below:
                        skipDeprecated.set(false)
                        reportUndocumented.set(true) // Emit warnings about not documented members
                        includeNonPublic.set(false)
                    }
                    // Suppress a package
                    perPackageOption {
                        matchingRegex.set(".*\\.internal.*") // will match all .internal packages and sub-packages
                        suppress.set(true)
                    }
                }
            }
        }

        val dokkaJar by tasks.register("dokkaJar", Jar::class.java) {
            archiveClassifier.set("javadoc")
            from(dokka)
        }

        val sourcesJar by tasks.register("sourcesJar", Jar::class.java) {
            archiveClassifier.set("sources")
            from(mainSourceSet)
        }

        val classesJar by tasks.register("classesJar", Jar::class.java) {
            from("${project.buildDir}/intermediates/classes/release")
        }

        val javadoc = tasks.create("javadoc", Javadoc::class.java) {
            //setSource(mainSourceSet)
            classpath += project.files(baseExt.bootClasspath.joinToString(File.pathSeparator))
            libraryExtension().libraryVariants.forEach { variant ->
                if (variant.name == "release") {
                    classpath += variant.javaCompileProvider.get().classpath
                }
            }
            // exclude("**/R.html", "**/R.*.html", "**/index.html")
            // allow index html to be packaged in javadoc
            exclude("**/R.html", "**/R.*.html")
        }

        val javadocJar = tasks.create("javadocJar", Jar::class.java) {
            dependsOn(javadoc, dokka)
            archiveClassifier.set("javadoc")
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            includeEmptyDirs = false
            from(javadoc.destinationDir, dokka.flatMap { it.outputDirectory })
        }

        artifacts {
            add("archives", dokkaJar)
            add("archives", classesJar)
            add("archives", sourcesJar)
        }

        publishingExtension().publications {
            val component = components.findByName("android")

            println("Configuring maven publication options for ${project.path}:maven with component -> ${component?.name}")
            create("maven", MavenPublication::class.java) {
                groupId = "co.anitrend.arch"
                artifactId = project.name
                version = Versions.versionName

                artifact(javadocJar)
                artifact(sourcesJar)
                artifact("${project.buildDir}/outputs/aar/${project.name}-release.aar")
                from(component)

                pom {
                    name.set("support-arch")
                    description.set("A multi-module template library that attempts to make clean arch apps easier to build")
                    url.set("https://github.com/anitrend/support-arch")
                    licenses {
                        license {
                            name.set("Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("wax911")
                            name.set("Maxwell Mapako")
                            organizationUrl.set("https://github.com/anitrend")
                        }
                    }
                }
            }
        }
    }
}