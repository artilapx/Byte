package org.artilapx.bytepsec.models;

import android.os.Parcel;

public class NewsDetails extends NewsHeader {

    public final String date;
    public final String text;

    public NewsDetails(long id, String title, String summary, String thumbnail, String url, String date, String text) {
        super(id, title, summary, thumbnail, url);
        this.date = date;
        this.text = text;
    }

    public NewsDetails(NewsHeader header, String date, String text) {
        super(header.id, header.title, header.summary, header.thumbnail, header.url);
        this.date = date;
        this.text = text;
    }

    protected NewsDetails(Parcel in) {
        super(in);
        date = in.readString();
        text = in.readString();
    }

    public static final Creator<NewsDetails> CREATOR = new Creator<NewsDetails>() {
        @Override
        public NewsDetails createFromParcel(Parcel in) {
            return new NewsDetails(in);
        }

        @Override
        public NewsDetails[] newArray(int size) {
            return new NewsDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(date);
        parcel.writeString(text);
    }

}
