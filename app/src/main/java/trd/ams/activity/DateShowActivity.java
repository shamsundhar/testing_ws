package trd.ams.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import trd.ams.R;

public class DateShowActivity extends AppCompatActivity {
TextView currentdate;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_show);

        currentdate=(TextView) findViewById(R.id.dateget);
        String bh = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        currentdate.setText(bh);

    }
}
