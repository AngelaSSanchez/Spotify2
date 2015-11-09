package com.example.angelas.spotify2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angelas.spotify2.models.TrackDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by AngelaS on 8/11/15.
 */
public class TrackDBAdapter extends ArrayAdapter<TrackDB> {

        Context context;
        ArrayList<TrackDB> tracks;

    public TrackDBAdapter(Context context, int layoutId, ArrayList<TrackDB> tracks) {
        super(context, layoutId, tracks);
        this.context = context;
        this.tracks = tracks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.track_item,null);
        }
        TrackDB track=(TrackDB) tracks.get(position);
        if(track!=null){
           ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewPlaylist);
           TextView  textView01 = (TextView) convertView.findViewById(R.id.playlistitem);
           TextView  textView02 = (TextView) convertView.findViewById(R.id.playlistinfo);
           if(textView01!=null){
                textView01.setText(track.get_title());
           }
           if(textView02!=null){
                textView02.setText(track.get_artist()+" - "+track.get_album());
           }
           if(imageView!=null){
                Picasso.with(context).load(track.get_image()).into(imageView);
           }
        }
        return convertView;
    }
}
