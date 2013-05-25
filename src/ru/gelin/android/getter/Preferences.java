package ru.gelin.android.getter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebIconDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Preferences {

    static final String URL_PREF = "url";
    public static final String URL_DEFAULT = "http://example.net/";

    static final String BASICAUTH_PREF = "basicauth";
    public static final boolean BASICAUTH_DEFAULT = false;

    static final String USERNAME_PREF = "username";
    public static final String USERNAME_DEFAULT = "";

    static final String PASSWORD_PREF = "password";
    public static final String PASSWORD_DEFAULT = "";

    static final String TITLE_PREF = "title";
    public static final String TITLE_DEFAULT = "";

    static final String ICON_FILE = "favicon.png";
    static final int ICON_DEFAULT = R.drawable.http;

    Context context;
    SharedPreferences preferences;

    public Preferences(Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUrl() {
        return this.preferences.getString(URL_PREF, URL_DEFAULT);
    }

    public void setUrl(String url) {
        this.preferences.edit().putString(URL_PREF, url).commit();
    }

    public boolean isBasicAuth() {
        return this.preferences.getBoolean(BASICAUTH_PREF, BASICAUTH_DEFAULT);
    }

    public void setBasicAuth(boolean auth) {
        this.preferences.edit().putBoolean(BASICAUTH_PREF, auth).commit();
    }

    public String getUserName() {
        return this.preferences.getString(USERNAME_PREF, USERNAME_DEFAULT);
    }

    public void setUserName(String username) {
        this.preferences.edit().putString(USERNAME_PREF, username).commit();
    }

    public String getPassword() {
        return this.preferences.getString(PASSWORD_PREF, PASSWORD_DEFAULT);
    }

    public void setPassword(String password) {
        this.preferences.edit().putString(PASSWORD_PREF, password).commit();
    }

    public String getTitle() {
        return this.preferences.getString(TITLE_PREF, TITLE_DEFAULT);
    }

    public void setTitle(String title) {
        this.preferences.edit().putString(TITLE_PREF, title).commit();
    }

    public void setIcon(Bitmap icon) {
        FileOutputStream file;
        try {
            file = this.context.openFileOutput(ICON_FILE, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); //should never be happen
        }
        icon.compress(Bitmap.CompressFormat.PNG, 100, file);
    }

    public Drawable getIcon() {
        Rect rect = getIconRect();
        FileInputStream file;
        try {
            file = this.context.openFileInput(ICON_FILE);
        } catch (FileNotFoundException e) {
            Drawable drawable = this.context.getResources().getDrawable(ICON_DEFAULT);
            drawable.setBounds(rect);
            return drawable;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(file);
        BitmapDrawable drawable = new BitmapDrawable(this.context.getResources(), bitmap);
        //drawable.setFilterBitmap(false);
        drawable.setBounds(rect);
        return drawable;
    }

    public void clearIcon() {
        WebIconDatabase.getInstance().removeAllIcons();
        this.context.deleteFile(ICON_FILE);
    }

    Rect getIconRect() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager)this.context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        int size = (int)(128 * metrics.density);
        return new Rect(0, 0, size, size);
    }

}
