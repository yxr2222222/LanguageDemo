package com.yxr.language.entity;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageConfig {
    private final List<CustomLanguage> customLanguageList = new ArrayList<>();
    /**
     * 是否需要内部处理语言变化
     */
    private boolean isInnerNotifyLanguageChanged = false;
    /**
     * 是否需要默认的跟随系统
     */
    private boolean isNeedDefaultSystem = true;
    /**
     * 是否需要默认的简体中文
     */
    private boolean isNeedDefaultSimplifiedChinese = true;
    /**
     * 是否需要默认的繁体中文
     */
    private boolean isNeedDefaultTraditionalChinese = true;
    /**
     * 是否需要默认的英文
     */
    private boolean isNeedDefaultEnglish = true;
    /**
     * 初始默认的语言，没有设置默认跟随系统
     */
    private Locale defaultLocale;
    /**
     * 切换语言时跳转到指定的App界面，建议时APP首页
     */
    private Class<? extends Activity> mainClass;

    private LanguageConfig() {

    }

    public boolean isNeedDefaultSystem() {
        return isNeedDefaultSystem;
    }

    public boolean isNeedDefaultSimplifiedChinese() {
        return isNeedDefaultSimplifiedChinese;
    }

    public boolean isNeedDefaultTraditionalChinese() {
        return isNeedDefaultTraditionalChinese;
    }

    public boolean isNeedDefaultEnglish() {
        return isNeedDefaultEnglish;
    }

    public List<CustomLanguage> getCustomLanguageList() {
        return customLanguageList;
    }

    public boolean isInnerNotifyLanguageChanged() {
        return isInnerNotifyLanguageChanged;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public Class<? extends Activity> getMainClass() {
        return mainClass;
    }

    public static class Builder {
        private final LanguageConfig config = new LanguageConfig();

        public Builder isNeedDefaultSystem(boolean isNeedDefaultSystem) {
            config.isNeedDefaultSystem = isNeedDefaultSystem;
            return this;
        }

        public Builder isNeedDefaultSimplifiedChinese(boolean isNeedDefaultSimplifiedChinese) {
            config.isNeedDefaultSimplifiedChinese = isNeedDefaultSimplifiedChinese;
            return this;
        }

        public Builder isNeedDefaultTraditionalChinese(boolean isNeedDefaultTraditionalChinese) {
            config.isNeedDefaultTraditionalChinese = isNeedDefaultTraditionalChinese;
            return this;
        }

        public Builder isNeedDefaultEnglish(boolean isNeedDefaultEnglish) {
            config.isNeedDefaultEnglish = isNeedDefaultEnglish;
            return this;
        }

        public Builder isInnerNotifyLanguageChanged(boolean isInnerNotifyLanguageChanged) {
            config.isInnerNotifyLanguageChanged = isInnerNotifyLanguageChanged;
            return this;
        }

        public Builder setDefaultLocale(@Nullable Locale defaultLocale) {
            config.defaultLocale = defaultLocale;
            return this;
        }

        public Builder setMainClass(@NonNull Class<? extends Activity> mainClass) {
            config.mainClass = mainClass;
            return this;
        }

        public Builder addCustomLanguage(@NonNull CustomLanguage customLanguage) {
            config.customLanguageList.add(customLanguage);
            return this;
        }

        public LanguageConfig build() {
            return config;
        }
    }
}
