package com.sawapps.baymaxhealthcare;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.sawapps.baymaxhealthcare.Network.Remote.ApiUtils;

public class SettingsActivity extends AppCompatActivity {

    EditText url;
    View done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        url = (EditText) findViewById(R.id.url);
        done = findViewById(R.id.done);

        url.setText(Utils.BASE_URL);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    if (url.getText().toString().trim().length() != 0) {


                        ApiUtils.changeBaseUrl(url.getText().toString());

                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
