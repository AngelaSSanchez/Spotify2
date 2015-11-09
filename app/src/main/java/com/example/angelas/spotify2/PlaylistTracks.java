package com.example.angelas.spotify2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.angelas.spotify2.models.FavSongRepo;
import com.example.angelas.spotify2.models.TrackDB;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTrack;

public class PlaylistTracks extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adaptador;
    private ArrayList<PlaylistTrack> tracks;
    private String playlist;
    private String user;
    Context context;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    SpotifyApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_tracks);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        playlist = bundle.getString("PLAYLIST");
        user = bundle.getString("USER");
        String playlistname = bundle.getString("PLAYLISTNAME");

        context = getApplicationContext();

        TextView text = (TextView) findViewById(R.id.playname);
        text.setText(playlistname);
        Log.i(LOG_TAG, "User " + user + " Playlist" + playlist);

        listView = (ListView) findViewById(R.id.playlistTrack);

        tracks = new ArrayList<>();

        adaptador = new PlaylistTracksAdapter(this.getApplicationContext(), R.layout.track_item, tracks);
        listView.setAdapter(adaptador);

        PlaylistTracks.SearchSpotifyTask task = new SearchSpotifyTask();
        task.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opcion = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(PlaylistTracks.this, PlayTrack.class);

                Bundle bundle = new Bundle();
                bundle.putString("OPCION_ELEGIDA", opcion);
                bundle.putString("PLAYLIST", tracks.get(position).track.id);
                bundle.putString("USER", user);
                bundle.putString("ALBUM",tracks.get(position).track.album.name);
                bundle.putString("NAME", tracks.get(position).track.name);
                bundle.putString("IMAGE", tracks.get(position).track.album.images.get(0).url);
                bundle.putString("ARTIST", tracks.get(position).track.artists.get(0).name);
                bundle.putInt("NUMERO_OPCION", position);
                intent.putExtras(bundle);

                startActivity(intent);

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
                Intent intent = new Intent(PlaylistTracks.this, ShowFavorites.class);
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
        Intent nuevoIntent = new Intent(PlaylistTracks.this, MainActivity.class);
        startActivity(nuevoIntent);
    }

    public class SearchSpotifyTask extends AsyncTask<Void, Void, Boolean>
    {

        SearchSpotifyTask() {
           tracks = new ArrayList<>();
        }
        @Override
        protected Boolean doInBackground(Void... strings) {
            api = PlaylistsPage.getApi();

            SpotifyService service = api.getService();

            List<PlaylistTrack> list = service.getPlaylistTracks(user, playlist).items;

            for (int i = 0; i < list.size(); i++) {
                PlaylistTrack playlistTrack = list.get(i);
                tracks.add(playlistTrack);
                Log.i(LOG_TAG, i + " " + tracks.get(i).track.id);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adaptador.clear();

            //progressDialog = ProgressDialog.show(PlaylistsPage.this, getString(R.string.msgWait), getString(R.string.msgLoadingUsers), true, true);
            //progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            //    @Override
            //    public void onCancel(DialogInterface dialog) {
            //        tareaGetUsers.cancel(true);
            //    }
            //});
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            // showProgress(false);
            adaptador.clear();

            for (PlaylistTrack u : tracks) {
                adaptador.add(u);
            }
            adaptador.notifyDataSetChanged();
            //progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            // showProgress(false);
        }
    }
}
