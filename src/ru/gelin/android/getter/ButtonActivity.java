package ru.gelin.android.getter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ButtonActivity extends Activity implements DialogInterface.OnClickListener {

    static final int URL_DIALOG = 1;

    String url = "http://example.com/";
    EditText urlEdit;
    TextView urlView;
    Preferences preferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button);
        this.urlView = (TextView)findViewById(R.id.url);
        this.preferences = new Preferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUrl(this.preferences.getUrl());
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
        this.url = url;
        this.preferences.setUrl(url);
        this.urlView.setText(url);
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
