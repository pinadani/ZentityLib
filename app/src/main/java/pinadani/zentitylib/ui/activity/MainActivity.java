package pinadani.zentitylib.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import pinadani.zentitylib.R;
import pinadani.zentitylib.ui.activity.base.BaseFragmentActivity;
import pinadani.zentitylib.ui.fragment.LibFragment;

/**
 * Main activity contains fragment. Tap back twice to exit
 * Created by Daniel Pina on 7/5/2017.
 */
public class MainActivity extends BaseFragmentActivity {
    public static final String TAG = MainActivity.class.getName();

    /**
     * Double tap back to leaving
     */
    private Toast mLeavingToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toast shown when user presses back button first time
        mLeavingToast = Toast.makeText(this, R.string.main_leave, Toast.LENGTH_SHORT);
    }


    @Override
    protected String getFragmentName() {
        return LibFragment.class.getName();
    }

    @Override
    public void onBackPressed() {
        if (mLeavingToast.getView().getWindowVisibility() != View.VISIBLE) {
            mLeavingToast.show();
        } else {
            finish();
        }
    }
}
