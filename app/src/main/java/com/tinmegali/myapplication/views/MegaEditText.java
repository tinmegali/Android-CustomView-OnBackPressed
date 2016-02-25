package com.tinmegali.myapplication.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.tinmegali.myapplication.R;

/**
 * ---------------------------------------------------
 * Created by Tin Megali on 24/02/16.
 * Project: My Application
 * ---------------------------------------------------
 * <a href="http://www.tinmegali.com">tinmegali.com</a>
 * <a href="http://www.github.com/tinmegali>github</a>
 * ---------------------------------------------------
 * Based on <a href="http://stackoverflow.com/a/5811630/4871489">@mhradek stackoverflow answer</a>
 *
 *
 * This custom EditText intercepts the editTextOnBackPressed when the softKeyboard
 * is visible on the screen and inform the event to subscriber.
 */
public class MegaEditText extends EditText {

    private final String TAG = MegaEditText.class.getSimpleName();

    private BackPressed mCallback;

    public MegaEditText(Context context) {
        super(context);
    }

    public MegaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MegaEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public MegaEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private String extraInfo;
    private void init(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(
                attrs, R.styleable.MegaEditText
        );
        extraInfo = array.getString(R.styleable.MegaEditText_extraInformation);
        array.recycle();
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * Register callback to receive event.
     */
    public void setListener(BackPressed callback){
        mCallback = callback;
    }

    /**
     * Intercepts event <code>KeyEvent.KEYCODE_BACK</code>,
     * continues with standard behavior, depending on
     * {@link BackPressed#editTextOnBackPressed()} result
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEventPreIme(" + event + ")");
        if ( mCallback != null ) {
            if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                KeyEvent.DispatcherState state = getKeyDispatcherState();
                if (state != null) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getRepeatCount() == 0) {
                        state.startTracking(event, this);
                        Log.d(TAG, "KeyEvent.ACTION_DOWN");
                        return true;
                    } else if (event.getAction() == KeyEvent.ACTION_UP
                            && !event.isCanceled() && state.isTracking(event)) {
                        if ( mCallback.editTextOnBackPressed() ) {
                            Log.d(TAG, "KeyEvent.ACTION_UP | cancelling standard behavior");
                            return true;
                        } else {
                            Log.d(TAG, "KeyEvent.ACTION_UP | proceeding with standard behavior");
                            return super.dispatchKeyEventPreIme(event);
                        }
                    }
                }
            }
        }
        Log.i(TAG, "Returning generic event");
        return super.dispatchKeyEventPreIme(event);
    }


    public interface BackPressed {
        /**
         * Listener for onBackPressed events with this object in focus.
         *
         * @return  true: <code>View</code> drop standard behavior e process
         *                  only what is inside <code>editTextOnPressed</code> block
         *          false: <code>View</code> executes code inside <code>editTextOnPressed</code>
         *                  and proceed with standard behavior
         */
        boolean editTextOnBackPressed();
    }
}
