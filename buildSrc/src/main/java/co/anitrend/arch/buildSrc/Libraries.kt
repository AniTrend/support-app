package co.anitrend.arch.buildSrc

import co.anitrend.arch.buildSrc.common.Versions

@Suppress("SpellCheckingInspection")
object Libraries {

    const val threeTenBp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenBp}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    object Android {

        object Tools {
            private const val version = "4.1.3"
            const val buildGradle = "com.android.tools.build:gradle:${version}"
        }
    }

    object AndroidX {

        object Core {
            private const val version = "1.5.0-rc01"
            const val core = "androidx.core:core:$version"
            const val coreKtx = "androidx.core:core-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.4.0-alpha01"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val runTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveDataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:$version"
        }

        object Paging {
            private const val version = "2.1.2"
            const val common = "androidx.paging:paging-common-ktx:$version"
            const val runtime = "androidx.paging:paging-runtime:$version"
            const val runtimeKtx = "androidx.paging:paging-runtime-ktx:$version"
        }

        object Preference {
            private const val version = "1.1.1"
            const val preference = "androidx.preference:preference:$version"
            const val preferenceKtx = "androidx.preference:preference-ktx:$version"
        }

        object Recycler {
            private const val version = "1.2.0-rc01"
            const val recyclerView = "androidx.recyclerview:recyclerview:$version"
            const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:$version"
        }

        object StartUp {
            private const val version = "1.0.0"
            const val startUpRuntime = "androidx.startup:startup-runtime:$version"
        }

        object SwipeRefresh {
            private const val version = "1.2.0-alpha01"
            const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$version"
        }

        object Test {
            private const val version = "1.3.0"
            const val core = "androidx.test:core:$version"
            const val coreKtx = "androidx.test:core-ktx:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Extension {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit:$version"
                const val junitKtx = "androidx.test.ext:junit-ktx:$version"
            }
        }

        object Work {
            private const val version = "2.7.0-alpha02"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
            const val runtime = "androidx.work:work-runtime:$version"
            const val test = "androidx.work:work-test:$version"
        }
    }

    object CashApp {
        object Turbine {
            private const val version = "0.4.1"
            const val turbine = "app.cash.turbine:turbine:$version"
        }
    }

    object Google {
        private const val version = "1.4.0-alpha01"
        const val material = "com.google.android.material:material:$version"
    }

    object JetBrains {
        object Dokka {
            private const val version = "1.4.30"
            const val gradlePlugin = "org.jetbrains.dokka:dokka-gradle-plugin:$version"
        }

        object Kotlin {
            private const val version = "1.5.0"
            const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
            const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"

            object Gradle {
                const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
            }

            object Android {
                const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
            }
        }

        object KotlinX {
            object Coroutines {
                private const val version = "1.4.3"
                const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
                const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
                const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
            }
        }
    }
}