package pinadani.zentitylib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Book model downloaded and parsed from XML
 * Created by Daniel Pina on 7/5/2017.
 */
public class Book {

    private String id;
    private String title;
    private String thumbnail;
    private boolean newBook;
    private String thumbExt;

    public Book(String id, String title, String thumbnail, boolean newBook, String thumbExt) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.newBook = newBook;
        this.thumbExt = thumbExt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isNewBook() {
        return newBook;
    }
}
