package ru.dkotik.notesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class Note implements Parcelable {

    @StringRes
    private final int title;

    @StringRes
    private final int description;

    @StringRes
    private final int date;

    public Note(int title, int date, int description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    protected Note(Parcel in) {
        title = in.readInt();
        date = in.readInt();
        description = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getTitle() {
        return title;
    }

    public int getDate() {
        return date;
    }

    public int getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(title);
        dest.writeInt(date);
        dest.writeInt(description);
    }
}
