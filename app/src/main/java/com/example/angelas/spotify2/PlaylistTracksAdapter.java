package com.example.angelas.spotify2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angelas.spotify2.models.FavSongRepo;
import com.example.angelas.spotify2.models.TrackDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTrack;

/**
 * Created by AngelaS on 8/11/15.
 */
public class PlaylistTracksAdapter extends ArrayAdapter<PlaylistTrack> {

    Context context;
    ArrayList<PlaylistTrack> tracks;


    public PlaylistTracksAdapter(Context context, int resource, ArrayList<PlaylistTrack> tracks) {
        super(context, resource, tracks);
        this.context = context;
        this.tracks = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.track_item, null);
        }
        final PlaylistTrack track = tracks.get(position);

        if (track != null) {
            TextView textView01 = (TextView) convertView.findViewById(R.id.playlistitem);
            TextView textView02 = (TextView) convertView.findViewById(R.id.playlistinfo);
            ImageView iView = (ImageView) convertView.findViewById(R.id.imageViewPlaylist);
            if (textView01 != null) {
                textView01.setText(track.track.name);
            }
            if (textView02 != null) {
                textView02.setText( track.track.artists.get(0).name+" - "+track.track.album.name);
            }
            if (iView != null) {
                try {
                    Picasso.with(context).load(track.track.album.images.get(0).url).into(iView);
                } catch (Exception e){
                    Picasso.with(context).load("https://browshot.com/static/images/not-found.png").into(iView);
                }
            }

        }
        return convertView;
    }
}
