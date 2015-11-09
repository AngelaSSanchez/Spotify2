package com.example.angelas.spotify2.models;

/**
 * Created by AngelaS on 8/11/15.
 *
 *
 */
import android.provider.BaseColumns;
public class FavSongsContract {

    public FavSongsContract() {}

    public static class favoritesongs implements BaseColumns {

        public static final String TABLE_NAME = "spotifyfavorites";

        public static final String COL_NAME_ID = _ID;
        public static final String COL_NAME_IDSTRING = "idstrig";
        public static final String COL_NAME_TITLE = "nombre";
        public static final String COL_NAME_ARTIST = "artista";
        public static final String COL_NAME_ALBUM = "album";
        public static final String COL_NAME_IMAGE = "imagen";

    }

}
