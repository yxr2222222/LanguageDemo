package com.yxr.languagedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yxr.language.LanguageManager;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageManager.getInstance().showLanguageSelectorDialog(MainActivity.this);
            }
        });

        findViewById(R.id.btnCustomShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CustomLanguageSwitchActivity.class));
            }
        });
    }
}
