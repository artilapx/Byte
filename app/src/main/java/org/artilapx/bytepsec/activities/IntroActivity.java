package org.artilapx.bytepsec.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import org.artilapx.bytepsec.R;

public class IntroActivity extends com.heinrichreimersoftware.materialintro.app.IntroActivity {

    public static final String EXTRA_CUSTOM_FRAGMENTS = "com.heinrichreimersoftware.materialintro.demo.EXTRA_CUSTOM_FRAGMENTS";
    public static final String EXTRA_SHOW_BACK = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SHOW_BACK";
    public static final String EXTRA_SHOW_NEXT = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SHOW_NEXT";
    public static final String EXTRA_SKIP_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SKIP_ENABLED";
    public static final String EXTRA_FINISH_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_FINISH_ENABLED";
    public static final String EXTRA_GET_STARTED_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_GET_STARTED_ENABLED";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();

        boolean customFragments = intent.getBooleanExtra(EXTRA_CUSTOM_FRAGMENTS, true);
        boolean showBack = intent.getBooleanExtra(EXTRA_SHOW_BACK, true);
        boolean showNext = intent.getBooleanExtra(EXTRA_SHOW_NEXT, true);
        boolean skipEnabled = intent.getBooleanExtra(EXTRA_SKIP_ENABLED, false);
        boolean finishEnabled = intent.getBooleanExtra(EXTRA_FINISH_ENABLED, true);
        boolean getStartedEnabled = intent.getBooleanExtra(EXTRA_GET_STARTED_ENABLED, false);

        setFullscreen(false);

        super.onCreate(savedInstanceState);

        setButtonBackFunction(skipEnabled ? BUTTON_BACK_FUNCTION_SKIP : BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction(finishEnabled ? BUTTON_NEXT_FUNCTION_NEXT_FINISH : BUTTON_NEXT_FUNCTION_NEXT);
        setButtonBackVisible(showBack);
        setButtonNextVisible(showNext);
        setButtonCtaVisible(getStartedEnabled);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        addSlide(new SimpleSlide.Builder()
                .title("Расписание, которое всегда под рукой")
                .description("Смотрите расписание в любое время, в любом месте")
                .image(R.drawable.intro_1)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Экономьте ваше время")
                .description("Теперь не надо заходить на сайт, теперь всё есть в приложении")
                .image(R.drawable.intro_2)
                .background(R.color.googleBlue)
                .backgroundDark(R.color.googleBlueDark)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Простота и интуитивность")
                .description("Дизайн без излишеств на основе Material Design")
                .image(R.drawable.intro_3)
                .background(R.color.googleGreen)
                .backgroundDark(R.color.googleGreenDark)
                .scrollable(false)
                .build());
    }

}
