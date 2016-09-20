package com.example.polipo.openqr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String scannedText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.scanned_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"On Start ....." );
        //new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"On Resume ....." );
        //new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "On Pause .....");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"On Restart ....." );
        //new IntentIntegrator(this).initiateScan();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setClipboard(String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text : ", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    public void startScan(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    public void startBrowser (View view) {
        if (scannedText != null) {
            if (!scannedText.startsWith("http://") && !scannedText.startsWith("https://"))
                scannedText = "http://" + scannedText;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedText));
            startActivity(browserIntent);
        } else {
            Toast.makeText(this, "Nothing scanned...", Toast.LENGTH_LONG).show();
        }
    }

    public boolean setText(String text) {
        if (text != null) {
            scannedText = text;
            textView.setText(text);
            setClipboard(text);
            return true;
        } else {
            return false;
        }

    }

}
