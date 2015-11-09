package com.example.angelas.spotify2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AngelaS on 8/11/15.
 */
public class TrackDB implements Parcelable {

    private int _id;
    private String _idstring;
    private String _artist;
    private String _album;
    private String _title;
    private String _image;

    public TrackDB(int _id, String _idstring, String _title, String _artist, String _album, String _image) {
        this._id = _id;
        this._idstring = _idstring;
        this._title = _title;
        this._artist = _artist;
        this._album = _album;
        this._image = _image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_idString() {
        return _idstring;
    }

    public void set_idString(String _idstring) {
        this._idstring = _idstring;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_artist() {
        return _artist;
    }

    public void set_artist(String _artist) {
        this._artist = _artist;
    }

    public String get_album() {
        return _album;
    }

    public void set_album(String _album) {
        this._album = _album;
    }

    public String get_image() {
        return _image;
    }

    public void set_imagen(String _image) {
        this._image = _image;
    }

    @Override
    public String toString() {
        return "Track{" +
                "_id=" + _id +
                ", _idstring='" + _idstring + '\'' +
                ", _title='" + _title + '\'' +
                ", _artist=" + _artist +
                ", _album=" + _album +
                ", _image='" + _image + '\'' +
                '}';
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_idstring);
        dest.writeString(_title);
        dest.writeString(_artist);
        dest.writeString(_album);
        dest.writeString(_image);
    }

    public static final Parcelable.Creator<TrackDB> CREATOR
            = new Parcelable.Creator<TrackDB>() {
        public TrackDB createFromParcel(Parcel in) {
            return new TrackDB(in);
        }

        public TrackDB[] newArray(int size) {
            return new TrackDB[size];
        }
    };

    private TrackDB(Parcel origen) {
        this._id         = origen.readInt();        // id
        this._idstring   = origen.readString();
        this._title    = origen.readString();     // nombre
        this._artist     = origen.readString();        // dorsal
        this._album     = origen.readString();     // equipo
        this._image = origen.readString();     // url_imagen
    }

}
