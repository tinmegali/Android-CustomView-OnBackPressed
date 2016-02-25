package com.tinmegali.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tinmegali.myapplication.views.MegaEditText;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MegaEditText mEditText = (MegaEditText) findViewById(R.id.edit);
        mEditText.setListener(new MegaEditText.BackPressed() {
            /**
             * Processa ações antes do <code>onBackPressed</code>
             * ser chamado pelo EditText.
             * @return  true: Não permite que o View continue com o
             *                processo convencional do <code>onBackPressed</code>
             *          false: Permite que o comando continue sendo processado
             *                 no View.
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
