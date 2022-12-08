package com.yxr.language.callback;

import android.support.annotation.NonNull;

import com.yxr.language.entity.LanguageLocale;

public interface OnLanguageChangedListener {
    void onLanguageChanged(@NonNull LanguageLocale languageLocale);
}
