package org.artilapx.bytepsec.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable, UniqueObject {

    public final long id;
    public final String title;
    public final String teacher;
    public final String classroom;
    public final String timeStart;
    public final String timeEnd;

    public Schedule(long id, String name, String title, String teacher, String classroom, String timeStart, String timeEnd) {
        this.id = id;
        this.title = title;
        this.teacher = teacher;
        this.classroom = classroom;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    protected Schedule(Parcel in) {
        id = in.readLong();
        title = in.readString();
        teacher = in.readString();
        classroom = in.readString();
        timeStart = in.readString();
        timeEnd = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
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
        parcel.writeString(teacher);
        parcel.writeString(classroom);
        parcel.writeString(timeStart);
        parcel.writeString(timeEnd);
    }

    @Override
    public long getId() {
        return id;
    }
}
