package ru.gelin.android.getter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ButtonActivity extends Activity implements DialogInterface.OnClickListener {

    static final int URL_DIALOG = 1;

    Preferences preferences;
    String url = Preferences.URL_DEFAULT;
    EditText urlEdit;
    TextView urlView;
    Button buttonView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button);
        this.preferences = new Preferences(this);
        this.urlView = (TextView)findViewById(R.id.url);
        this.buttonView = (Button)findViewById(R.id.button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUrl(this.preferences.getUrl(), false);
    }

    public void changeUrl(View view) {
        showDialog(URL_DIALOG);
    }

    public void getIt(View view) {
        Intent intent = new Intent(this, GetActivity.class);
        intent.setData(Uri.parse(this.url));
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS|Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    void setUrl(String url) {
        setUrl(url, true);
    }

    void setUrl(String url, boolean clear) {
        this.url = url;
        this.preferences.setUrl(url);
        this.urlView.setText(url);
        if (clear) {
            this.preferences.setTitle(Preferences.TITLE_DEFAULT);
            this.preferences.clearIcon();
        }
        String title = this.preferences.getTitle();
        if (title == null || title.length() == 0) {
            this.buttonView.setText(R.string.get_button_default);
        } else {
            this.buttonView.setText(getString(R.string.get_button, this.preferences.getTitle()));
        }
        Drawable icon = this.preferences.getIcon();
        this.buttonView.setCompoundDrawables(null, null, null, icon);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case URL_DIALOG:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                this.urlEdit = (EditText)getLayoutInflater().inflate(R.layout.url_edit, null);
                builder.setTitle(R.string.enter_url)
                       .setView(urlEdit)
                       .setPositiveButton(android.R.string.ok, this)
                       .setNegativeButton(android.R.string.cancel, this);
                return builder.create();
            default:
                return super.onCreateDialog(id);
        }
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case URL_DIALOG:
                this.urlEdit.setText(this.url);
                return;
            default:
                super.onPrepareDialog(id, dialog);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int id) {
        switch (id) {
            case DialogInterface.BUTTON_POSITIVE:
                setUrl(this.urlEdit.getText().toString());
                return;
        }
    }

}
