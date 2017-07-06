package pinadani.zentitylib.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import pinadani.zentitylib.R;
import pinadani.zentitylib.model.Book;
import pinadani.zentitylib.ui.adapter.LibAdapter;
import pinadani.zentitylib.ui.view.LibView;
import pinadani.zentitylib.util.DownloadXML;

/**
 * library Fragment, which contains AsyncTask to receive books in XML format.
 * Created by Daniel Pina on 7/5/2017.
 **/
public class LibFragment extends Fragment implements DownloadXML.DownloadXMLListener {
    public static final String TAG = LibFragment.class.getName();

    /**
     * Library gridview
     */
    LibView mLibView;

    /**
     * Progress dialog shown during loading books.
     */
    ProgressDialog mProgressDialog;
    boolean mProgressDialogShown = false;
    /**
     * List of loaded books
     */
    ArrayList<Book> mBooks;

    /**
     * HashMap contains books covers
     */
    HashMap<String, Bitmap> mCovers = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lib, container, false);

        // init views
        initProgressBar();
        mLibView = (LibView) view.findViewById(R.id.lib_view);


        if (savedInstanceState == null) {
            // start downloading at first start
            new DownloadXML(this).execute();
        } else {
            // Show progress dialog after change configuration
            if(mProgressDialogShown) {
                mProgressDialog.show();
            }

            // wait for Async task or show complete results
            if (mBooks != null) {
                mLibView.setAdapter(new LibAdapter(mBooks, mCovers, getActivity(), mLibView.getCustomBackground().getHeight()));
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialogShown = true;
        }
    }

    /**
     * Init ProgressBar, to show while downloading books
     */
    private void initProgressBar() {
        // Create a progressbar
        mProgressDialog = new ProgressDialog(getActivity());
        // Set progressbar title
        mProgressDialog.setTitle(getString(R.string.load_magazines));
        // Set progressbar message
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setIndeterminate(false);
    }

    @Override
    public void onPreDownload() {
        // Show progressbar
        mProgressDialog.show();
    }

    @Override
    public void onPostDownload(ArrayList<Book> books, HashMap<String, Bitmap> covers) {
        mBooks = books;
        mCovers = covers;

        // Close progressbar
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialogShown = false;
        }

        if (this.mBooks != null) {
            mLibView.setAdapter(new LibAdapter(this.mBooks, this.mCovers, getActivity(), mLibView.getCustomBackground().getHeight()));
        }
    }
}
