package trd.ams.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import trd.ams.R;
import trd.ams.async.DataUpdateAsyncTask;
import trd.ams.common.Globals;

public class GetIdActivity extends AppCompatActivity {
EditText getid;
Button gotologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_id);
        gotologin = (Button) findViewById(R.id.btngotologin);
        getid = (EditText) findViewById(R.id.etgetid);



        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

                Log.d("tag","details"+intent);

                finish();

            }
        });


    }
}



























