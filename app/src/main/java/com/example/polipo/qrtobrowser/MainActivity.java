package com.example.polipo.qrtobrowser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        startScan();

        final Button buttonGo = (Button) findViewById(R.id.button_go);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                // Perform action on click
                startScan();

        }});
    }

    public void startScan() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        startActivityForResult(intent, 0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                if (contents.startsWith("http://") | contents.startsWith("https://")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contents));
                    startActivity(browserIntent);
                } else Toast.makeText(context, contents, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                //Handle cancel
                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_LONG).show();
            }
        }
    }



}
