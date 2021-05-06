/**
 * Copyright 2021 AniTrend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.anitrend.arch.extension.settings

import android.content.SharedPreferences
import android.content.res.Resources
import co.anitrend.arch.extension.ext.UNSAFE
import co.anitrend.arch.extension.preference.BooleanPreference
import co.anitrend.arch.extension.preference.EnumPreference
import co.anitrend.arch.extension.preference.FloatPreference
import co.anitrend.arch.extension.preference.IntPreference
import co.anitrend.arch.extension.preference.LongPreference
import co.anitrend.arch.extension.preference.NullableStringPreference
import co.anitrend.arch.extension.preference.StringPreference
import co.anitrend.arch.extension.settings.contract.AbstractSetting
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

private typealias SettingsListener = SharedPreferences.OnSharedPreferenceChangeListener

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class EnumSetting<T : Enum<*>>(
    key: Int,
    default: T,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<T>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by EnumPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class BooleanSetting(
    key: Int,
    default: Boolean,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<Boolean>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by BooleanPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class IntSetting(
    key: Int,
    default: Int,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<Int>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by IntPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class FloatSetting(
    key: Int,
    default: Float,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<Float>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by FloatPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class LongSetting(
    key: Int,
    default: Long,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<Long>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by LongPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class StringSetting(
    key: Int,
    default: String,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<String>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by StringPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}

/**
 * @since v1.3.0
 * @see AbstractSetting
 */
class NullableStringSetting(
    key: Int,
    default: String?,
    resources: Resources,
    preference: SharedPreferences
) : AbstractSetting<String?>(preference, default) {

    override val identifier = resources.getString(key)

    override var value by NullableStringPreference(identifier, default)

    override val flow by lazy(UNSAFE) {
        callbackFlow {
            val listener = SettingsListener { _, id ->
                if (id == identifier)
                    trySendBlocking(value)
            }
            preference.registerOnSharedPreferenceChangeListener(listener)
            awaitClose { preference.unregisterOnSharedPreferenceChangeListener(listener) }
        }
    }
}
