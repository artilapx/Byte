package org.artilapx.bytepsec.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsHeader implements Parcelable {

    public final long id;
    public final String title;
    public final String summary;
    public final String thumbnail;
    public final String url;

    public NewsHeader(String title, String summary, String thumbnail, String url) {
        this.id = title.hashCode();
        this.title = title;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public NewsHeader(long id, String title, String summary, String thumbnail, String url) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    protected NewsHeader(Parcel in) {
        id = in.readLong();
        title = in.readString();
        summary = in.readString();
        thumbnail = in.readString();
        url = in.readString();
    }

    public static final Creator<NewsHeader> CREATOR = new Creator<NewsHeader>() {
        @Override
        public NewsHeader createFromParcel(Parcel in) {
            return new NewsHeader(in);
        }

        @Override
        public NewsHeader[] newArray(int size) {
            return new NewsHeader[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(summary);
        parcel.writeString(thumbnail);
        parcel.writeString(url);
    }
}
