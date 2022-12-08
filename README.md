## README

#### 1. 简介

语言SDK是一款多语言切换SDK，通过该SDK，我们可以快速实现多语言切换。。

#### 2. 如何使用

* 根Gradle中添加

  ```java
  maven { url 'https://jitpack.io' }
  ```

* Module中依赖集成

  ```java
  implementation 'com.github.yxr2222222:LanguageDemo:v1.0.0.202212081'
  ```

* 初始化和设置监听，建议在Application onCreate()中进行，初始化完成切换到上次选择的系统，没有选择过默认为跟随系统

  ```java
  // 初始化多语言SDK
   LanguageManager.getInstance().init(this, new LanguageConfig.Builder()
                // 是否需要默认的跟随系统，默认为true
                .isNeedDefaultSystem(false)
                // 是否需要默认的简体中文，默认为true
                .isNeedDefaultSimplifiedChinese(true)
                // 是否需要默认的繁体中文，默认为true
                .isNeedDefaultTraditionalChinese(true)
                // 是否需要默认的英文，默认为true
                .isNeedDefaultEnglish(false)
                // 是否内部处理语言切换逻辑，内部有ActivityLifecycleCallbacks，在Activity onCreate时进行语言资源切换，
                // 手动切换语言，需配合setMainClass跳转到指定的App界面，建议时APP首页
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
  // 设置语言切换监听
  LanguageManager.getInstance().setOnLanguageChangedListener(new OnLanguageChangedListener() {
      @Override
      public void onLanguageChanged(@NonNull LanguageLocale languageLocale) {
          
      }
  });
  ```

* 展示多语言切换弹框

  ```java
  LanguageManager.getInstance().showLanguageSelectorDialog(this);
  ```
  
* 多语言转换

  ```java
  // 获取当前语言
  LanguageLocale languageLocale = LanguageManager.getInstance().getCurrLanguage();
  
  ```

#### 3. 其他

* 内部编译时依赖库

  ```java
  compileOnly 'androidx.appcompat:appcompat:1.4.2'
  compileOnly 'com.google.android.material:material:1.6.1'
  compileOnly 'com.google.code.gson:gson:2.8.6'
  ```
