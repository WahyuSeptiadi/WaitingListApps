package com.wahyu.waitinglistapps.View.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wahyu.waitinglistapps.R;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by wahyu_septiadi on 19, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#03A9F4"))
                .withHeaderText("")
                .withFooterText(getApplication().getResources().getString(R.string.copyright))
                .withBeforeLogoText(getApplication().getResources().getString(R.string.app_name))
                .withAfterLogoText(getApplication().getResources().getString(R.string.name_hospital))
                .withLogo(R.drawable.icon_logoapps);

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
