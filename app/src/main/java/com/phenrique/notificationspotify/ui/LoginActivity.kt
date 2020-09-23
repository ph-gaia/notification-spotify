package com.phenrique.notificationspotify.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.phenrique.notificationspotify.R
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote


class LoginActivity : AppCompatActivity() {
    private val CLIENT_ID = "your_client_id"
    private val REDIRECT_URI = "http://com.yourdomain.yourapp/callback"
    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spotify_login)
    }

    override fun onStart() {
        super.onStart()

        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(this, connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    Log.d("LoginActivity", "Connected! Yay!")

                    // Now you can start interacting with App Remote
                    connected()
                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("LoginActivity", throwable.message, throwable)

                    // Something went wrong when attempting to connect! Handle errors here
                }
            })

    }

    private fun connected() {
        mSpotifyAppRemote?.getPlayerApi()?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }
}