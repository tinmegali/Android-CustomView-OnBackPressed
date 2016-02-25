package com.tinmegali.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.tinmegali.myapplication.views.MegaEditText;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MegaEditText mMegaEditText = (MegaEditText) findViewById(R.id.edit);
        mMegaEditText.setListener(new MegaEditText.BackPressed() {
            /**
             * Process actions before standard <code>onBackPressed</code>
             * behavior and gives the option to drop this standard beavior.
             * @return true: Drops <code>onBackPressed</code> standard behavior
             *          false:  Execute <code>onBackPressed</code> standard behavior.
             */
            @Override
            public boolean editTextOnBackPressed() {
                Log.d(TAG, "editTextOnBackPressed");
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
