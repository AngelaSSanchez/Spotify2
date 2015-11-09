package com.example.angelas.spotify2.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.angelas.spotify2.models.FavSongsContract.*;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by AngelaS on 8/11/15.
 */
public class FavSongRepo extends SQLiteOpenHelper {

    private static final String DATABASE_FILENAME = "spotifyfavorites.db";

    private static final int DATABASE_VERSION = 1;

    public FavSongRepo(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sentenciaSQL = "CREATE TABLE " + favoritesongs.TABLE_NAME + "("
                + favoritesongs.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + favoritesongs.COL_NAME_IDSTRING + " TEXT, "
                + favoritesongs.COL_NAME_TITLE + " TEXT, "
                + favoritesongs.COL_NAME_ARTIST + " TEXT, "
                + favoritesongs.COL_NAME_ALBUM + " TEXT, "
                + favoritesongs.COL_NAME_IMAGE + " TEXT)";
        db.execSQL(sentenciaSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sentenciaSQL = "DROP TABLE IF EXISTS " + favoritesongs.TABLE_NAME;
        db.execSQL(sentenciaSQL);
        onCreate(db);
    }

    public long add(TrackDB trackdb) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(favoritesongs.COL_NAME_IDSTRING, trackdb.get_idString());
        valores.put(favoritesongs.COL_NAME_TITLE, trackdb.get_title());
        valores.put(favoritesongs.COL_NAME_ARTIST, trackdb.get_artist());
        valores.put(favoritesongs.COL_NAME_ALBUM, trackdb.get_album());
        valores.put(favoritesongs.COL_NAME_IMAGE, trackdb.get_image());

        return db.insert(favoritesongs.TABLE_NAME, null, valores);
    }

    public ArrayList<TrackDB> getAll() {
        return getAll(favoritesongs.COL_NAME_ID, true);
    }

    public ArrayList<TrackDB> getAll(String columna, boolean ordenAscendente) {
        ArrayList<TrackDB> resultado = new ArrayList<>();
        String consultaSQL = "SELECT * FROM " + favoritesongs.TABLE_NAME
                + " ORDER BY " + columna + (ordenAscendente ? " ASC" : " DESC");

        // TO DO abrir bd lectura
        // Accedo a la DB en modo lectura
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(consultaSQL, null);

        // TO DO recorrer cursor asignando resultados
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
               TrackDB trackdb = new TrackDB(
                        cursor.getInt(cursor.getColumnIndex(favoritesongs.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(favoritesongs.COL_NAME_IDSTRING)),
                        cursor.getString(cursor.getColumnIndex(favoritesongs.COL_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(favoritesongs.COL_NAME_ARTIST)),
                        cursor.getString(cursor.getColumnIndex(favoritesongs.COL_NAME_ALBUM)),
                        cursor.getString(cursor.getColumnIndex(favoritesongs.COL_NAME_IMAGE))
                );
                resultado.add(trackdb);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return resultado;
    }

    public void deleteTrack(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(favoritesongs.TABLE_NAME, favoritesongs.COL_NAME_IDSTRING+"=?",new String[] { id });
       // db.close();
    }

    public boolean searchTrack(String id){
        String consultaSQL = "SELECT * FROM " + favoritesongs.TABLE_NAME
                + " WHERE " + favoritesongs.COL_NAME_IDSTRING + "= ?" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                consultaSQL,
                new String[]{id}
        );

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }

        return false;
        // db.close();
    }
}
