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
    }

    public MegaEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MegaEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(
                attrs, R.styleable.MegaEditText
        );
        array.recycle();
    }

    /**
     * Registre o callback para receber o evento
     */
    public void setListener(BackPressed callback){
        mCallback = callback;
    }

    /**
     * Subscreva o evento <code>dispatchKeyEventPreIme</code>.
     * Intercepte o tipo de evento <code>KeyEvent.KEYCODE_BACK</code>
     * e faça o que quiser com ele.
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEventPreIme(" + event + ")");
        if ( mCallback != null ) {
            // O View verifica o tipo de evento
            // dá a oportunidade do objeto inscrito
            // processar o evento e anula a ação padrão
            // em caso de retorno positivo
            if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                KeyEvent.DispatcherState state = getKeyDispatcherState();
                if (state != null) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getRepeatCount() == 0) {
                        state.startTracking(event, this);
                        Log.i(TAG, "KeyEvent.ACTION_DOWN");
                        return true;
                    } else if (event.getAction() == KeyEvent.ACTION_UP
                            && !event.isCanceled() && state.isTracking(event)) {
                        if ( mCallback.editTextOnBackPressed() ) {
                            Log.i(TAG, "KeyEvent.ACTION_UP | cancelando processo padrão");
                            return true;
                        } else {
                            Log.i(TAG, "KeyEvent.ACTION_UP | processando o processo padrão");
                            return super.dispatchKeyEventPreIme(event);
                        }
                    }
                }
            }
        }
        Log.i(TAG, "Returning generic onBackPressed");
        return super.dispatchKeyEventPreIme(event);
    }


    public interface BackPressed {
        /**
         * Listener para eventos onBackPressed com o teclado presente.
         * O objeto inscrito tem a oportunidade de processar o evento
         * e definir se o <code>View</code> deve seguir ou não com sua
         * ação padrao.
         * @return  true: <code>View</code> abandona a ação padrão e executa
         *                  somente o bloco definido antes do retorno
         *          false: <code>View</code> executa o código definido antes do
         *                  retorno e dá sequência a ação convencional
         */
        boolean editTextOnBackPressed();
    }
}
