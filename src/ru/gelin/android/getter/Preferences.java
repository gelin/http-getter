package ru.gelin.android.getter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String URL_PREF = "url";
    static final String URL_DEFAULT = "http://example.net/";

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

}
