package com.yxr.language.entity;

import java.util.Locale;

public class LanguageLocale {
    private Locale locale;
    private boolean isFollowSystem;

    public LanguageLocale(Locale locale, boolean isFollowSystem) {
        this.locale = locale;
        this.isFollowSystem = isFollowSystem;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isFollowSystem() {
        return isFollowSystem;
    }

    public void setFollowSystem(boolean followSystem) {
        isFollowSystem = followSystem;
    }
}
