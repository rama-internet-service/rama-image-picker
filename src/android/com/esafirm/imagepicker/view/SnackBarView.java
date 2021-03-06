package com.esafirm.imagepicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
//import com.esafirm.imagepicker.R;

public class SnackBarView extends RelativeLayout {

    private static final int ANIM_DURATION = 200;

    private static final Interpolator INTERPOLATOR = new FastOutLinearInInterpolator();

    private TextView txtCaption;
    private Button btnAction;

    private FakeR fakeR;

    public SnackBarView(Context context) {
        this(context, null);
    }

    public SnackBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SnackBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        fakeR = new FakeR(this.getContext());
        //View.inflate(getContext(), R.layout.ef_imagepikcer_snackbar, this);
        View.inflate(getContext(), fakeR.getId("layout", "ef_imagepikcer_snackbar"), this);

        //int padding = getContext().getResources().getDimensionPixelSize(R.dimen.ef_spacing_double);
      //MIRCO
      // int padding = getContext().getResources().getDimensionPixelSize(fakeR.getId("values", "dimen.ef_spacing_double"));
      int padding =16;
      setPadding(padding, 0, padding, 0);

        if (isInEditMode()) {
            return;
        }
        //int height = getContext().getResources().getDimensionPixelSize(R.dimen.ef_height_snackbar);
        //int height = getContext().getResources().getDimensionPixelSize(fakeR.getId("values", "dimen.ef_height_snackbar"));
      int height =80;
      setTranslationY(height);
        setAlpha(0f);

        //txtCaption = findViewById(R.id.ef_snackbar_txt_bottom_caption);
        //txtCaption = findViewById(fakeR.getId("layout", "ef_snackbar_txt_bottom_caption"));
        txtCaption = findViewById(fakeR.getId("id", "ef_snackbar_txt_bottom_caption"));
        //btnAction = findViewById(R.id.ef_snackbar_btn_action);
        btnAction = findViewById(fakeR.getId("id", "ef_snackbar_btn_action"));
    }

    public void setText(@StringRes int textResId) {
        txtCaption.setText(textResId);
    }

    public void setOnActionClickListener(@StringRes int textId, final OnClickListener onClickListener) {
        if (textId == 0) {
            //textId = R.string.ef_ok;
            textId = fakeR.getId("string", "ef_ok");
        }

        btnAction.setText(textId);
        btnAction.setOnClickListener(v -> hide(() -> onClickListener.onClick(v)));
    }

    public void show(@StringRes int textResId, OnClickListener onClickListener) {
        setText(textResId);
        setOnActionClickListener(0, onClickListener);

        ViewCompat.animate(this)
                .translationY(0f)
                .setDuration(ANIM_DURATION)
                .setInterpolator(INTERPOLATOR)
                .alpha(1f);
    }

    public void hide() {
        hide(null);
    }

    private void hide(Runnable runnable) {
        ViewCompat.animate(this)
                .translationY(getHeight())
                .setDuration(ANIM_DURATION)
                .alpha(0.5f)
                .withEndAction(runnable);
    }
}
