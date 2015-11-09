package com.example.angelas.spotify2;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.angelas.spotify2.models.FavSongRepo;
import com.example.angelas.spotify2.models.TrackDB;
import com.spotify.sdk.android.authentication.AuthenticationClient;

import java.util.ArrayList;

public class ShowFavorites extends AppCompatActivity {

    ArrayList<TrackDB> tracks;
    ListView lvtracks;
    FavSongRepo repositorio;
    ArrayAdapter<TrackDB> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorites);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvtracks = (ListView) findViewById(R.id.favoriteTracks);

        repositorio = new FavSongRepo(this);

        tracks = repositorio.getAll();
        adaptador = new TrackDBAdapter(this, R.layout.track_item, tracks);

        lvtracks.setAdapter(adaptador);

        lvtracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opcion = lvtracks.getItemAtPosition(position).toString();
                Intent intent = new Intent(ShowFavorites.this, PlayTrack.class);

                Bundle bundle = new Bundle();
                bundle.putString("OPCION_ELEGIDA", opcion);
                bundle.putString("PLAYLIST", tracks.get(position).get_idString());
                bundle.putString("ALBUM", tracks.get(position).get_album());
                bundle.putString("NAME", tracks.get(position).get_title());
                bundle.putString("IMAGE", tracks.get(position).get_image());
                bundle.putString("ARTIST", tracks.get(position).get_artist());
                bundle.putInt("NUMERO_OPCION", position);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
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
        Intent nuevoIntent = new Intent(ShowFavorites.this, MainActivity.class);
        startActivity(nuevoIntent);
    }
}
