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

package co.anitrend.arch.extension.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import co.anitrend.arch.extension.network.contract.ISupportConnectivity
import co.anitrend.arch.extension.network.model.ConnectivityState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Inspired by [ConnectivityChecker](https://github.com/android/plaid/blob/master/core/src/main/java/io/plaidapp/core/ui/ConnectivityChecker.kt)
 *
 * Lifecycle aware connectivity checker that exposes the network connected status via a [StateFlow].
 *
 * The loss of connectivity when the activity is resumed should be a blocker for the user
 * in [onResume], we should get the connectivity status.
 *
 * @since v1.2.0
 */
class SupportConnectivity(
    private val connectivityManager: ConnectivityManager?,
    private val connectivityCapabilities: Int = NetworkCapabilities.NET_CAPABILITY_INTERNET
) : ISupportConnectivity {

    /**
     * Check if the device is connected to any network with internet capabilities, this is only
     * a snapshot of the state at the time of request
     *
     * @return true if a internet activity is present otherwise false
     */
    override val isConnected
        get() = (
            connectivityManager?.allNetworks?.filter {
                val network = connectivityManager.getNetworkCapabilities(it)
                network?.hasCapability(connectivityCapabilities) ?: false
            }?.size ?: 0
            ) > 0

    /**
     * Connection state flow, allows us to monitor changes on the network connectivity
     *
     * @see ConnectivityState
     */
    override val connectivityStateFlow = callbackFlow<ConnectivityState> {
        val callback = object : ConnectivityManager.NetworkCallback() {
            /**
             * Called when the framework connects and has declared a new network ready for use.
             *
             *
             * For callbacks registered with [.registerNetworkCallback], multiple networks may
             * be available at the same time, and onAvailable will be called for each of these as they
             * appear.
             *
             *
             * For callbacks registered with [.requestNetwork] and
             * [.registerDefaultNetworkCallback], this means the network passed as an argument
             * is the new best network for this request and is now tracked by this callback ; this
             * callback will no longer receive method calls about other networks that may have been
             * passed to this method previously. The previously-best network may have disconnected, or
             * it may still be around and the newly-best network may simply be better.
             *
             *
             * Starting with [android.os.Build.VERSION_CODES.O], this will always immediately
             * be followed by a call to [.onCapabilitiesChanged]
             * then by a call to [.onLinkPropertiesChanged], and a call
             * to [.onBlockedStatusChanged].
             *
             *
             * Do NOT call [.getNetworkCapabilities] or
             * [.getLinkProperties] or other synchronous ConnectivityManager methods in
             * this callback as this is prone to race conditions (there is no guarantee the objects
             * returned by these methods will be current). Instead, wait for a call to
             * [.onCapabilitiesChanged] and
             * [.onLinkPropertiesChanged] whose arguments are guaranteed
             * to be well-ordered with respect to other callbacks.
             *
             * @param network The [Network] of the satisfying network.
             */
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(ConnectivityState.Connected)
            }

            /**
             * Called when a network disconnects or otherwise no longer satisfies this request or
             * callback.
             *
             *
             * If the callback was registered with requestNetwork() or
             * registerDefaultNetworkCallback(), it will only be invoked against the last network
             * returned by onAvailable() when that network is lost and no other network satisfies
             * the criteria of the request.
             *
             *
             * If the callback was registered with registerNetworkCallback() it will be called for
             * each network which no longer satisfies the criteria of the callback.
             *
             *
             * Do NOT call [.getNetworkCapabilities] or
             * [.getLinkProperties] or other synchronous ConnectivityManager methods in
             * this callback as this is prone to race conditions ; calling these methods while in a
             * callback may return an outdated or even a null object.
             *
             * @param network The [Network] lost.
             */
            override fun onLost(network: Network) {
                trySend(ConnectivityState.Disconnected)
            }

            /**
             * Called if no network is found within the timeout time specified in
             * [.requestNetwork] call or if the
             * requested network request cannot be fulfilled (whether or not a timeout was
             * specified). When this callback is invoked the associated
             * [NetworkRequest] will have already been removed and released, as if
             * [.unregisterNetworkCallback] had been called.
             */
            override fun onUnavailable() {
                trySend(ConnectivityState.Unknown)
            }
        }

        connectivityManager?.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(connectivityCapabilities)
                .build(),
            callback
        )

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }
}
