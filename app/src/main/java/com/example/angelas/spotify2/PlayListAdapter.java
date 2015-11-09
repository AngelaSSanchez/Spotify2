package com.example.angelas.spotify2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistBase;
import kaaes.spotify.webapi.android.models.PlaylistSimple;

/**
 * Created by AngelaS on 4/11/15.
 */
public class PlayListAdapter extends ArrayAdapter<PlaylistSimple> {

    Context context;
    ArrayList<PlaylistSimple> p;

    public PlayListAdapter(Context context, int layoutId, ArrayList<PlaylistSimple> playlists) {
        super(context, layoutId, playlists);
        this.context = context;
        this.p = playlists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.playlist_item, null);
        }
        PlaylistSimple playlist = (PlaylistSimple) p.get(position);
        if (playlist != null) {
            TextView textView01 = (TextView) convertView.findViewById(R.id.playlistitem);
            TextView textView02 = (TextView) convertView.findViewById(R.id.playlistinfo);
            ImageView iView = (ImageView) convertView.findViewById(R.id.imageViewPlaylist);
            if (textView01 != null) {
                textView01.setText(playlist.name);
            }
            if (textView02 != null) {
                textView02.setText("by "+ playlist.owner.id + " - " + playlist.tracks.total+" tracks");
            }
            if (iView != null) {
                Picasso.with(context).load(playlist.images.get(0).url).into(iView);
            }
        }
        return convertView;
    }
}
