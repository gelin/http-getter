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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Preferences {

    static final String URL_PREF = "url";
    public static final String URL_DEFAULT = "http://example.net/";

    static final String TITLE_PREF = "title";
    public static final String TITLE_DEFAULT = "";

    static final String ICON_FILE = "favicon.png";
    static final int ICON_DEFAULT = R.drawable.http;
    static final Rect ICON_RECT = new Rect(0, 0, 128, 128);

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
        FileInputStream file;
        try {
            file = this.context.openFileInput(ICON_FILE);
        } catch (FileNotFoundException e) {
            Drawable drawable = this.context.getResources().getDrawable(ICON_DEFAULT);
            drawable.setBounds(ICON_RECT);
            return drawable;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(file);
        BitmapDrawable drawable = new BitmapDrawable(this.context.getResources(), bitmap);
        //drawable.setFilterBitmap(false);
        drawable.setBounds(ICON_RECT);
        return drawable;
    }

    public void clearIcon() {
        this.context.deleteFile(ICON_FILE);
    }

}
