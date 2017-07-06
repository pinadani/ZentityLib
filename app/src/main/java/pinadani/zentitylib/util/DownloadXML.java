package pinadani.zentitylib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import pinadani.zentitylib.model.Book;

/**
 * Download lib XML AsyncTask
 * Created by Daniel Pina on 7/6/2017.
 */
public class DownloadXML extends AsyncTask<Void, Void, Void> {
    private String URL = "http://www.lukaspetrik.cz/filemanager/tmp/reader/data.xml";

    private final DownloadXMLListener mDownloadXMLListener;

    private ArrayList<Book> mBooks;
    private HashMap<String, Bitmap> mCovers = new HashMap<>();

    public DownloadXML(DownloadXMLListener downloadXMLListener) {
        this.mDownloadXMLListener = downloadXMLListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDownloadXMLListener.onPreDownload();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            java.net.URL url = new URL(URL);
            // Download the XML file
            XmlParser xmlParser = new XmlParser();
            mBooks = xmlParser.parse(url.openStream());
            loadImages();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void args) {
        mDownloadXMLListener.onPostDownload(mBooks, mCovers);
    }

    /**
     * load books covers
     */
    private void loadImages() {
        mCovers.clear();
        for (Book book : mBooks) {
            try {
                mCovers.put(book.getId(), BitmapFactory.decodeStream(new URL(book.getThumbnail()).openConnection().getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Interface implemented in {@link pinadani.zentitylib.ui.fragment.LibFragment}
     */
    public interface DownloadXMLListener {
        void onPreDownload();

        void onPostDownload(ArrayList<Book> books, HashMap<String, Bitmap> covers);
    }
}
