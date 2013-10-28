package com.machete.androidhibrid.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import com.machete.androidhibrid.R;

/**
 * Created by machete on 28/10/13.
 */
public class MainActivity extends ActionBarActivity {

    private TextView mTextView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);

        webView = (WebView) findViewById(R.id.webView);
        webView.addJavascriptInterface(new JavaScriptAccessor(), "AndroidFunction");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/www/index.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class JavaScriptAccessor {

        public void getWebData(final String data) {
            runOnUiThread(new Runnable() {
                public void run() {
                    mTextView.setText(data);
                }
            });
        }

        public void showNativeToast(final String msg) {

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                    mTextView.setText(msg);
                }
            });
        }

        public void openNativeDialog(final String msg) {

            mTextView.setText(msg);

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Dialog Box!")
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show();

        }

        public void sendToJavascript() {
            runOnUiThread(new Runnable() {
                public void run() {
                    webView.loadUrl("javascript:callFromActivity(\"" + 123456789 + "\")");
                }
            });
        }
    }
}
