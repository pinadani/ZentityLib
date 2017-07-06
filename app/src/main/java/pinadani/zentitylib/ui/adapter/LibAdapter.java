package pinadani.zentitylib.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import pinadani.zentitylib.R;
import pinadani.zentitylib.model.Book;

/**
 * Adapter showing list of items in library.
 * Created by Daniel Pina on 7/5/2017.
 */
public class LibAdapter extends BaseAdapter {
    public static final String TAG = LibAdapter.class.getName();

    private Context mContext;
    private final List<Book> mBooks;
    private final HashMap<String, Bitmap> mCovers;
    private int mRowHeight;

    public LibAdapter(List<Book> data, HashMap<String, Bitmap> covers, Context context, int rowHeight) {
        mBooks = data;
        mCovers = covers;
        mContext = context;
        mRowHeight = rowHeight;
    }

    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public Book getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(mBooks.get(position).getId());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_book, null);
        }
        new ViewHolder(convertView, position);
        return convertView;
    }


    private class ViewHolder {
        RelativeLayout mItemLayout;
        TextView mName;
        ImageView mCover;
        ImageView mCoverShadow;
        ImageView mNew;


        /**
         * Item constructor. Set data to view.
         */
        ViewHolder(View convertView, int position) {
            mName = (TextView) convertView.findViewById(R.id.item_name);
            mCover = (ImageView) convertView.findViewById(R.id.item_image);
            mCoverShadow = (ImageView) convertView.findViewById(R.id.item_shadow);
            mNew = (ImageView) convertView.findViewById(R.id.item_label_new);
            mItemLayout = (RelativeLayout) convertView.findViewById(R.id.lib_item);

            Book book = mBooks.get(position);

            mName.setText(book.getTitle());
            mCover.setImageBitmap(mCovers.get(book.getId()));
            mNew.setVisibility(book.isNewBook() ? View.VISIBLE : View.GONE);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mRowHeight));
            mItemLayout.setLayoutParams(lp);
        }
    }
}