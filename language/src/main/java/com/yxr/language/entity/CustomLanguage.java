package com.yxr.language.entity;

import androidx.annotation.NonNull;

import java.util.Locale;

public class CustomLanguage {
    @NonNull
    private final Locale locale;
    private final String title;
    private final boolean isSystem;
    private boolean isSelected = false;

    public CustomLanguage(@NonNull Locale locale, String title) {
        this(locale, title, false);
    }

    public CustomLanguage(@NonNull Locale locale, String title, boolean isSystem) {
        this.locale = locale;
        this.title = title;
        this.isSystem = isSystem;
    }

    @NonNull
    public Locale getLocale() {
        return locale;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}