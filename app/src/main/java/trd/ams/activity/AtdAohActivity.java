package trd.ams.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.Legend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import trd.ams.R;

public class AtdAohActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atd_aoh);

        setupTitleBar("BPA-OHE", true, true, true);
        //backward
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AtdAohActivity.this,ReportsActivity.class));

            }
        });

        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AtdAohActivity.this,AtdPohActivity.class));
            }
        });


        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(265f, 0));
        entries.add(new BarEntry(210f, 1));
        entries.add(new BarEntry(147f, 2));
        entries.add(new BarEntry(152f, 3));
        entries.add(new BarEntry(103f, 4));
        entries.add(new BarEntry(4f, 5));

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        Iterator itr =  entries1.iterator();
        while(itr.hasNext())
        {
            String entireiesvalus = (String) itr.next();

        }

        BarDataSet dataset = new BarDataSet(entries, "ATD-AOH");

        // X-axis labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("t_p");
        labels.add("y_tar");
        labels.add("ttm");
        labels.add("pro");
        labels.add("com");
        labels.add("out");
        //   labels.add("June");

        dataset.setColors(new int[]{ContextCompat.getColor(getApplicationContext(), R.color.black),
                ContextCompat.getColor(getApplicationContext(), R.color.orange),
                ContextCompat.getColor(getApplicationContext(), R.color.blue),
                ContextCompat.getColor(getApplicationContext(), R.color.yellow),
                ContextCompat.getColor(getApplicationContext(), R.color.green),
                ContextCompat.getColor(getApplicationContext(), R.color.red)

        });




        //  BarChart chart = new BarChart(getApplicationContext());
        //setContentView(chart);
        BarChart chart= (BarChart) findViewById(R.id.chart);

        Log.d("Bar chart","chart created");

        // ll.addView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        dataset.setBarSpacePercent(45f);

        Log.d("Bar chart","data inserted");
        // data.setBarWidth(0.5f);

        //get the height and width of the device
      /*  DisplayMetrics ds = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(ds);
        int width = ds.widthPixels;
        int height = ds.heightPixels;*/


        chart.setDescription("");
      /*  chart.setDescriptionTextSize(16f);
        chart.setPivotX(width-50f);
        chart.setPivotY(height-1250f);*/
        chart.getLegend().setTextColor(Color.MAGENTA);
        chart.getLegend().setTextSize(20f);
        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        Log.d("Bar chart","Legend");

        // dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        //chart.animateX(2000);

        /*Button btn_atdaoh = findViewById(R.id.btn_atdaoh);
        btn_atdaoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AtdAohActivity.this,AtdPohActivity.class));
            }
        });*/

        /*try {
            //TimeUnit.SECONDS.sleep(20);
            Thread.sleep(10000);
            startActivity(new Intent(AtdAohActivity.this,AtdPohActivity.class));

        } catch (InterruptedException e) {
            Log.e(TAG, "Timer exception" + e.getMessage());
        }*/




/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">


</android.support.constraint.ConstraintLayout>*/



    }

}
