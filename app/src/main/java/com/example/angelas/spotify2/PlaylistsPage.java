package com.example.angelas.spotify2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.spotify.sdk.android.authentication.AuthenticationClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import retrofit.Callback;

/**
 * Created by AngelaS on 3/11/15.
 */
public class PlaylistsPage extends AppCompatActivity {

    private ArrayList<PlaylistSimple> playlists;
    private String user;
    private ListView listView;
    SearchSpotifyTask task;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    ArrayAdapter adaptador;
    ProgressDialog progressDialog;

    private static SpotifyApi api;
    private static SpotifyService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.api = new SpotifyApi();

        this.api.setAccessToken(MainActivity.getAccessToken());

        this.service = this.api.getService();

        listView = (ListView) findViewById(R.id.playListview);

        // Inicializar usuarios
        playlists = new ArrayList<>();
        user = new String() ;

        adaptador = new PlayListAdapter(this.getApplicationContext(), R.layout.playlist_item, playlists);
        listView.setAdapter(adaptador);

        task = new SearchSpotifyTask();
        task.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String opcion = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(PlaylistsPage.this, PlaylistTracks.class);

                Bundle bundle = new Bundle();
                bundle.putString("OPCION_ELEGIDA", opcion);
                bundle.putString("PLAYLIST", playlists.get(position).id);
                bundle.putString("PLAYLISTNAME", playlists.get(position).name);
                bundle.putString("USER", user);
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
            //case android.R.id.home:
             //   return true;
            case R.id.action_settings:
                Intent intent = new Intent(PlaylistsPage.this, ShowFavorites.class);
                startActivity(intent);
                break;
            case R.id.action_logout:
                logout();
                break;
        }
         return true;
        //return super.onOptionsItemSelected(item);
    }

    public void logout(){
        AuthenticationClient.logout(getApplicationContext());
        Intent nuevoIntent = new Intent(PlaylistsPage.this, MainActivity.class);
        startActivity(nuevoIntent);
    }

    public static SpotifyApi getApi() {
        return api;
    }

    public void setApi() {
        this.api = new SpotifyApi();
    }

    public class CreatePlayListTask extends AsyncTask<Void,Void,Void>{

        CreatePlayListTask(){}

        @Override
        protected Void doInBackground(Void... params) {
            Map<String, Object> map = new HashMap<>();
            map.put("New Playlist", true);

            Playlist p = service.createPlaylist(user, map);
            return null;
        }
    }

    public class SearchSpotifyTask extends AsyncTask<Void, Void, Boolean> {

        SearchSpotifyTask() {
            playlists = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... strings) {

            List<PlaylistSimple> list = service.getPlaylists(service.getMe().id).items;

            user = service.getMe().id;
            Log.i(LOG_TAG, "Usuario " + user);

            for (int i = 0; i < list.size(); i++) {
                PlaylistSimple playlistSimple = list.get(i);
                playlists.add(playlistSimple);
                Log.i(LOG_TAG, i + " " + playlists.get(i).id);
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


            for (PlaylistSimple u : playlists) {
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
