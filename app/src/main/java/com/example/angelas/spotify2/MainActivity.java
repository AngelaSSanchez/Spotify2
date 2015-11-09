package com.example.angelas.spotify2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

public class MainActivity extends AppCompatActivity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String CLIENT_ID = "7857de3d06294270a8e11025a09a8008";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "angela-android-first-app://callback";

    private static final int REQUEST_CODE = 15;
    private static String ACCESS_TOKEN;
    private static Player mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming","playlist-modify-public"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.createLoginActivityIntent(this, request);

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


    }

    public static Player getPlayer(){
        return mPlayer;
    }

    public void setPlayer(Player player){
        mPlayer = player;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                ACCESS_TOKEN = response.getAccessToken();
                setAccessToken(ACCESS_TOKEN);
                //setPlayer();
                mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addPlayerNotificationCallback(MainActivity.this);
                        //Intent nuevoIntent = new Intent(MainActivity.this, PlaylistsPage.class);
                        //startActivity(nuevoIntent);
                    }


                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
                Intent nuevoIntent = new Intent(MainActivity.this, PlaylistsPage.class);
                startActivity(nuevoIntent);
                //setPlayer(mPlayer);
            }
        }
    }

    public void setAccessToken(String token){
        ACCESS_TOKEN = token;
    }

    public static String getAccessToken(){
        return ACCESS_TOKEN;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // case 1:
            case R.id.action_settings:
                break;
            case R.id.action_logout:
                logout();
                break;
        }

        return true;
    }


    public void logout(){

        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        builder.setShowDialog(true);
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginInBrowser(this, request);

        AuthenticationClient.logout(this);
    }
*/

    @Override
    public void onLoggedIn() {
    Log.d("MainActivity", "User logged in");
}

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }


}
