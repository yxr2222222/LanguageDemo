package com.yxr.language;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.yxr.language.callback.LifecycleCallbacks;
import com.yxr.language.callback.OnLanguageChangedListener;
import com.yxr.language.entity.CustomLanguage;
import com.yxr.language.entity.LanguageConfig;
import com.yxr.language.entity.LanguageLocale;
import com.yxr.language.widget.LanguageSelectorDialog;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanguageManager {
    private static final String SP_LOCALE_LANGUAGE = "SP_LOCALE_LANGUAGE";
    private static LanguageManager instance;
    private Context context;
    private LanguageLocale currLanguage;
    private LifecycleCallbacks lifecycleCallbacks;
    private WeakReference<LanguageSelectorDialog> languageSelectorDialog;
    private OnLanguageChangedListener onLanguageChangedListener;
    private LanguageConfig languageConfig;

    private LanguageManager() {
    }

    public static LanguageManager getInstance() {
        if (instance == null) {
            synchronized (LanguageManager.class) {
                if (instance == null) {
                    instance = new LanguageManager();
                }
            }
        }
        return instance;
    }

    public void init(@NonNull Context context, @NonNull LanguageConfig languageConfig) {
        if (this.context == null) {
            this.context = context;
            this.languageConfig = languageConfig;
            this.lifecycleCallbacks = new LifecycleCallbacks();
            Context applicationContext = context.getApplicationContext();
            if (applicationContext instanceof Application) {
                ((Application) applicationContext).registerActivityLifecycleCallbacks(lifecycleCallbacks);
            }
        }
    }

    /**
     * ??????????????????
     */
    public LanguageConfig getLanguageConfig() {
        return languageConfig;
    }

    /**
     * ??????????????????
     */
    @NonNull
    public LanguageLocale getCurrLanguage() {
        if (currLanguage == null) {
            try {
                String localeLanguage = SPUtil.getString(SP_LOCALE_LANGUAGE);
                if (!TextUtils.isEmpty(localeLanguage)) {
                    currLanguage = new Gson().fromJson(localeLanguage, LanguageLocale.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (currLanguage == null) {
            if (languageConfig != null && languageConfig.getDefaultLocale() != null) {
                currLanguage = new LanguageLocale(languageConfig.getDefaultLocale(), false);
            } else if (languageConfig != null && !languageConfig.isNeedDefaultSystem()) {
                // ???????????????????????????????????????????????????
                List<CustomLanguage> languageList = getLanguageList(getContext(), false);
                Locale currLocale = Locale.getDefault();
                int position = getSamePosition(currLocale, languageList);
                if (position >= 0 && position < languageList.size()) {
                    CustomLanguage customLanguage = languageList.get(position);
                    currLanguage = new LanguageLocale(customLanguage.getLocale(), customLanguage.isSystem());
                }
            }
        }

        if (currLanguage == null) {
            currLanguage = new LanguageLocale(Locale.getDefault(), true);
        }
        return currLanguage;
    }

    /**
     * ?????????????????????????????????
     */
    @NonNull
    public List<CustomLanguage> getLanguageList(@NonNull Context context) {
        return getLanguageList(context, true);
    }

    public List<CustomLanguage> getLanguageList(@NonNull Context context, boolean isNeedCheckSelected) {
        List<CustomLanguage> customLanguageList = new ArrayList<>();

        if (languageConfig != null && languageConfig.isNeedDefaultSystem()) {
            customLanguageList.add(new CustomLanguage(Locale.getDefault(), context.getString(R.string.language_follow_system), true));
        }

        if (languageConfig != null && languageConfig.isNeedDefaultSimplifiedChinese()) {
            customLanguageList.add(new CustomLanguage(Locale.SIMPLIFIED_CHINESE, "????????????"));
        }

        if (languageConfig != null && languageConfig.isNeedDefaultTraditionalChinese()) {
            customLanguageList.add(new CustomLanguage(Locale.TRADITIONAL_CHINESE, "????????????"));
        }

        if (languageConfig != null && languageConfig.isNeedDefaultEnglish()) {
            customLanguageList.add(new CustomLanguage(Locale.ENGLISH, "English"));
        }

        if (languageConfig != null && languageConfig.getCustomLanguageList().size() > 0) {
            customLanguageList.addAll(languageConfig.getCustomLanguageList());
        }

        if (isNeedCheckSelected) {
            for (CustomLanguage customLanguage : customLanguageList) {
                customLanguage.setSelected(false);
            }
            LanguageLocale currLanguage = getCurrLanguage();

            int selectedPosition = 0;
            if (currLanguage.isFollowSystem() && languageConfig != null && languageConfig.isNeedDefaultSystem()) {
                selectedPosition = 0;
            } else {
                Locale currLocale = currLanguage.getLocale();
                int samePosition = getSamePosition(currLocale, customLanguageList);
                selectedPosition = samePosition >= 0 ? samePosition : selectedPosition;
            }

            if (selectedPosition < customLanguageList.size()) {
                customLanguageList.get(selectedPosition).setSelected(true);
            }
        }

        return customLanguageList;
    }

    public Context getContext() {
        return context;
    }

    /**
     * ????????????APP??????
     *
     * @param locale ??????
     */
    public void updateCurrLanguage(boolean followSystem, @NonNull Locale locale) {
        try {
            LanguageLocale languageLocale = new LanguageLocale(locale, followSystem);
            String json = new Gson().toJson(languageLocale);
            SPUtil.putString(SP_LOCALE_LANGUAGE, json);
            this.currLanguage = languageLocale;
            if (onLanguageChangedListener != null) {
                onLanguageChangedListener.onLanguageChanged(currLanguage);
            }
            notifyLanguageSwitchFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     */
    public void setOnLanguageChangedListener(OnLanguageChangedListener onLanguageChangedListener) {
        this.onLanguageChangedListener = onLanguageChangedListener;
    }

    /**
     * ????????????????????????
     */
    public void showLanguageSelectorDialog(@NonNull Activity activity) {
        dismissLanguageSelectorDialog();
        LanguageSelectorDialog languageSelectorDialog = new LanguageSelectorDialog(activity);
        languageSelectorDialog.show();
        this.languageSelectorDialog = new WeakReference<>(languageSelectorDialog);
    }

    /**
     * ????????????????????????
     */
    public void dismissLanguageSelectorDialog() {
        if (languageSelectorDialog != null && languageSelectorDialog.get() != null) {
            languageSelectorDialog.get().dismiss();
            languageSelectorDialog = null;
        }
    }

    /**
     * ????????????????????????onCreate?????????
     */
    public void changLanguage(@NonNull Activity activity) {
        if (languageConfig != null && languageConfig.isInnerNotifyLanguageChanged()) {
            try {
                Resources resources = activity.getResources();
                DisplayMetrics metrics = resources.getDisplayMetrics();
                // ??????????????????
                Configuration config = resources.getConfiguration();
                // ??????????????????????????????????????????????????????????????????values????????????strings.xml??????
                config.setLocale(getCurrLanguage().getLocale());
                resources.updateConfiguration(config, metrics);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ?????????????????????
     */
    private void notifyLanguageSwitchFinish() {
        if (languageConfig != null && languageConfig.isInnerNotifyLanguageChanged() && languageConfig.getMainClass() != null) {
            try {
                Intent intent = new Intent(context, languageConfig.getMainClass());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getSamePosition(Locale currLocale, List<CustomLanguage> customLanguageList) {
        for (int i = 1; i < customLanguageList.size(); i++) {
            Locale locale = customLanguageList.get(i).getLocale();
            if (!TextUtils.isEmpty(locale.getCountry()) && !TextUtils.isEmpty(currLocale.getCountry())) {
                // ???????????????????????????????????????????????????
                if (TextUtils.equals(locale.getCountry(), currLocale.getCountry())) {
                    return i;
                }
            } else if (TextUtils.equals(locale.getLanguage(), currLocale.getLanguage())) {
                // ?????????????????????
                return i;
            }
        }
        return -1;
    }
}
