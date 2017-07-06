package pinadani.zentitylib.ui.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

import pinadani.zentitylib.R;
import pinadani.zentitylib.util.UiUtils;

/**
 * GridView extension to show a single background for the row.
 * Created by Daniel Pina on 7/5/2017.
 */
public class LibView extends GridView {

    /**
     * Background of individual rows.
     */
    private Bitmap mBackground;

    public LibView(Context context) {
        super(context);
        init();
    }

    public LibView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LibView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Init background and resize image to right width
     */
    protected void init() {
        //Init background
        mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.shelfcell_bgr);

        /**
         * Get screen width and resize backgrounds image
         */
        int newWidth = UiUtils.getScreenWidth(getContext());
        mBackground = UiUtils.scaleBitmap(mBackground, newWidth, mBackground.getHeight());
    }

    // Extend dispatchDraw to draw background
    @Override
    protected void dispatchDraw(Canvas canvas) {
        int top = getChildCount() > 0 ? getChildAt(0).getTop() : 0;

        for (int y = top; y < getHeight(); y += mBackground.getHeight()) {
            for (int x = 0; x < getWidth(); x += mBackground.getWidth()) {
                canvas.drawBitmap(mBackground, x, y, null);
            }
        }
        super.dispatchDraw(canvas);
    }

    public Bitmap getCustomBackground() {
        return mBackground;
    }
}
