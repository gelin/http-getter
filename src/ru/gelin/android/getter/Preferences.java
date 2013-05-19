package ru.gelin.android.getter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String URL_PREF = "url";
    public static final String URL_DEFAULT = "http://example.net/";

    static final String TITLE_PREF = "title";
    public static final String TITLE_DEFAULT = "";

    SharedPreferences preferences;

    public Preferences(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUrl() {
        return this.preferences.getString(URL_PREF, URL_DEFAULT);
    }

    public void setUrl(String url) {
        this.preferences.edit().putString(URL_PREF, url).commit();
    }

    public String getTitle() {
        return this.preferences.getString(TITLE_PREF, TITLE_DEFAULT);
    }

    public void setTitle(String title) {
        this.preferences.edit().putString(TITLE_PREF, title).commit();
    }

}
