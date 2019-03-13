package trd.ams.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.Legend;

import java.util.ArrayList;

import trd.ams.R;

public class TurnoutAohActivity extends TitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atd_poh);

        setupTitleBar("BPA-OHE", true, true, true);
        //backward
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TurnoutAohActivity.this,ReportsActivity.class));

            }
        });

        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TurnoutAohActivity.this,AtdAohActivity.class));
            }
        });

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(80f, 0));
        entries.add(new BarEntry(80f, 1));
        entries.add(new BarEntry(56f, 2));
        entries.add(new BarEntry(33f, 3));
        entries.add(new BarEntry(59f, 4));
        entries.add(new BarEntry(2f, 5));



        BarDataSet dataset = new BarDataSet(entries, "TURNOUT-AOH");

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

        // ll.addView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        dataset.setBarSpacePercent(45f);
        // data.setBarWidth(0.5f);

        //get the height and width of the device
        DisplayMetrics ds = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(ds);
        int width = ds.widthPixels;
        int height = ds.heightPixels;


        chart.setDescription("");
      /*  chart.setDescriptionTextSize(16f);
        chart.setPivotX(width-50f);
        chart.setPivotY(height-1250f);*/
        chart.getLegend().setTextColor(Color.MAGENTA);
        chart.getLegend().setTextSize(20f);
        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);


      /*  XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);*/


        // dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.animateX(2000);


    }

}
