package com.yxr.languagedemo;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yxr.language.LanguageManager;
import com.yxr.language.callback.OnLanguageChangedListener;
import com.yxr.language.entity.CustomLanguage;
import com.yxr.language.entity.LanguageConfig;
import com.yxr.language.entity.LanguageLocale;

import java.util.Locale;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化多语言SDK
        LanguageManager.getInstance().init(this, new LanguageConfig.Builder()
                // 是否需要默认的跟随系统，默认为true
                .isNeedDefaultSystem(true)
                // 是否需要默认的简体中文，默认为true
                .isNeedDefaultSimplifiedChinese(true)
                // 是否需要默认的繁体中文，默认为true
                .isNeedDefaultTraditionalChinese(false)
                // 是否需要默认的英文，默认为true
                .isNeedDefaultEnglish(true)
                // 是否内部处理语言切换逻辑，内部有ActivityLifecycleCallbacks，在Activity onCreate时进行语言资源切换，
                // 手动切换语言，需配合setMainClass跳转到指定的App界面，建议是APP首页
                // 默认为false
                .isInnerNotifyLanguageChanged(true)
                // 设置初始默认的语言，如果没有设置且不需要跟随系统，尝试找到匹配的，没有找到匹配默认跟随系统
                .setDefaultLocale(new Locale("in"))
                // 设置跳转到指定的App界面，建议时APP首页
                .setMainClass(MainActivity.class)
                // 添加自定义语言，SDK内部只有简体中文、繁体中文、英文、设置自定义语言时在对应的语言string.xml中添加SDK内的字符资源
                .addCustomLanguage(new CustomLanguage(new Locale("es"), "Español"))
                .addCustomLanguage(new CustomLanguage(new Locale("in"), "Indonesia"))
                .build());
        // 设置语言切换监听, isInnerNotifyLanguageChanged为false时可自己处理语言切换逻辑
        LanguageManager.getInstance().setOnLanguageChangedListener(new OnLanguageChangedListener() {
            @Override
            public void onLanguageChanged(@NonNull LanguageLocale languageLocale) {

            }
        });
    }
}
