package com.example.angelas.spotify2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angelas.spotify2.models.FavSongRepo;
import com.example.angelas.spotify2.models.TrackDB;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.player.Player;
import com.squareup.picasso.Picasso;

public class PlayTrack extends AppCompatActivity {

    String trackid;
    String user;
    int play = 0;
    int favsong = 0;
    private int id = 0;

    ImageView imageView;
    TextView  textView1;
    TextView  textView2;
    TextView  textView3;

    FavSongRepo fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        fav = new FavSongRepo(this);

        imageView = (ImageView) findViewById(R.id.imageViewTrack);
        textView1 = (TextView) findViewById(R.id.trackName);
        textView2 = (TextView) findViewById(R.id.trackTime);
        textView3 = (TextView) findViewById(R.id.trackArtist);
        final ImageButton buttonplay = (ImageButton) findViewById(R.id.imageButton);
        final ImageButton buttonfav = (ImageButton) findViewById(R.id.imageButton4);

        Bundle bundle = this.getIntent().getExtras();
        trackid = bundle.getString("PLAYLIST");
        user = bundle.getString("USER");
        final String name = bundle.getString("NAME");
        final String album = bundle.getString("ALBUM");
        final String artist = bundle.getString("ARTIST");
        final String image = bundle.getString("IMAGE");

        textView1.setText(name);
        textView3.setText("3:12");
        textView2.setText(artist);

        if (isFavorite(trackid)){
            buttonfav.setImageResource(R.drawable.broken);
        }

        Picasso.with(getApplicationContext()).load(image).into(imageView);

        buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = MainActivity.getPlayer();
                if (play == 0) {
                    play = 1;
                    buttonplay.setImageResource(R.drawable.pause);
                    player.play("spotify:track:" + trackid);
                } else {
                    play = 0;
                    buttonplay.setImageResource(R.drawable.play);
                    player.pause();
                }
            }
        });

        buttonfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFavorite(trackid)) {
                    fav.add(new TrackDB(id++, trackid, name, artist, album, image));
                    buttonfav.setImageResource(R.drawable.broken);
                }
                else if(isFavorite(trackid)){
                    fav.deleteTrack(trackid);
                    buttonfav.setImageResource(R.drawable.fav);
                }
            }
        });

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean isFavorite(String id){
        return fav.searchTrack(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(PlayTrack.this, ShowFavorites.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                logout();
                break;
        }
        // return
        return super.onOptionsItemSelected(item);

    }

    public void logout(){
        AuthenticationClient.logout(getApplicationContext());
        Intent nuevoIntent = new Intent(PlayTrack.this, MainActivity.class);
        startActivity(nuevoIntent);
    }

}
