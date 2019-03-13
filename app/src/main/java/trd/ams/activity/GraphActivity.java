package trd.ams.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.Legend;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import trd.ams.R;
import trd.ams.async.GraphAsyncTask;
import trd.ams.common.Globals;

public class GraphActivity extends TitleBarActivity {

    ArrayList<String> descriptionList;
    ArrayList<String> populationList;
    ArrayList<String> targetList;
    ArrayList<String> targetTillMonthList;
    ArrayList<String> progressList;
    ArrayList<String> complianceList;
    ArrayList<String> outstandingList;

    Spinner spGraphs;
    ArrayList<String> listGraphs;
    SpinnerGraphsAdapter graphsAdapter;
    String desc;
    int pos;

    int num = 0;
    Globals globals;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globals = (Globals) getApplication();
        String fac = globals.getFacilityName();

         descriptionList = globals.getDescriptionList();
         populationList = globals.getPopulationList();
         targetList = globals.getTargetList();
         targetTillMonthList = globals.getTargetTillMonthList();
         progressList = globals.getProgressList();
         complianceList = globals.getComplianceList();
         outstandingList = globals.getOutstandingList();

         listGraphs = new ArrayList<String>();

        listGraphs.clear();
       // listGraphs.addAll(getFacility());
        Log.d(TAG,"desc size--" +descriptionList.size());

        // Facility


      spGraphs = (Spinner)findViewById(R.id.spGraphs);
        graphsAdapter = new SpinnerGraphsAdapter(GraphActivity.this,
                android.R.layout.simple_spinner_item,descriptionList);
        spGraphs.setAdapter(graphsAdapter);
        graphsAdapter.notifyDataSetChanged();

        spGraphs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                desc = graphsAdapter.getItem(position);
                Log.d(TAG,"description: " + desc);
                Log.d(TAG,"position: " + position);
                    pos = position;
                displayDataGraph(position);
              //  globals.setFacilityName(facilityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.d(TAG," pos--"+pos);


        setupTitleBar(fac, true, true, true);
        //backward
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GraphActivity.this,ReportsActivity.class));

            }
        });

        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(pos == 0){
                num = num + 1;
                displayDataGraph(num);
                  if(num+1 == descriptionList.size()){
                      Log.d(" chart1", descriptionList.get(num));
                      ivForward.setVisibility(View.INVISIBLE);
                  }
                  else{
                      ivForward.setVisibility(View.VISIBLE);
                  }
              }
                else{
                  pos = pos + 1;
                  displayDataGraph(pos);
                  if(pos+1 == descriptionList.size()){
                      Log.d(" chart2", descriptionList.get(pos));
                      ivForward.setVisibility(View.INVISIBLE);
                  }else {
                      ivForward.setVisibility(View.VISIBLE);
                  }
              }
            }
        });

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(Float.parseFloat(populationList.get(num)), 0));
        entries.add(new BarEntry(Float.parseFloat(targetList.get(num)), 1));
        entries.add(new BarEntry(Float.parseFloat(targetTillMonthList.get(num)), 2));
        entries.add(new BarEntry(Float.parseFloat(progressList.get(num)), 3));
        entries.add(new BarEntry(Float.parseFloat(complianceList.get(num)), 4));
        entries.add(new BarEntry(Float.parseFloat(outstandingList.get(num)), 5));

        BarDataSet dataset = new BarDataSet(entries, descriptionList.get(num));

        // X-axis labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("t_p");
        labels.add("y_tar");
        labels.add("ttm");
        labels.add("pro");
        labels.add("com");
        labels.add("out");


        dataset.setColors(new int[]{ContextCompat.getColor(getApplicationContext(), R.color.black),
                ContextCompat.getColor(getApplicationContext(), R.color.orange),
                ContextCompat.getColor(getApplicationContext(), R.color.blue),
                ContextCompat.getColor(getApplicationContext(), R.color.yellow),
                ContextCompat.getColor(getApplicationContext(), R.color.green),
                ContextCompat.getColor(getApplicationContext(), R.color.red)

        });

        BarChart chart = (BarChart) findViewById(R.id.chart);

        Log.d("Bar chart", "chart created");
        BarData data = new BarData(labels, dataset);
        chart.setData(data);
        dataset.setBarSpacePercent(45f);

        Log.d("Bar chart", "data inserted");

        chart.setDescription("");
        chart.getLegend().setTextColor(Color.MAGENTA);
        chart.getLegend().setTextSize(20f);
        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        Log.d("Bar chart", "Legend");
        Log.d("Bar chart", descriptionList.get(num));


        chart.animateX(3000);



    }

    public void displayDataGraph(int i) {

        // for (int i = 0; i < descriptionList.size(); i++) {

        if (i < descriptionList.size()) {

            ArrayList<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(Float.parseFloat(populationList.get(i)), 0));
            entries.add(new BarEntry(Float.parseFloat(targetList.get(i)), 1));
            entries.add(new BarEntry(Float.parseFloat(targetTillMonthList.get(i)), 2));
            entries.add(new BarEntry(Float.parseFloat(progressList.get(i)), 3));
            entries.add(new BarEntry(Float.parseFloat(complianceList.get(i)), 4));
            entries.add(new BarEntry(Float.parseFloat(outstandingList.get(i)), 5));

            BarDataSet dataset = new BarDataSet(entries, descriptionList.get(i));

            // X-axis labels
            ArrayList<String> labels = new ArrayList<String>();
            labels.add("t_p");
            labels.add("y_tar");
            labels.add("ttm");
            labels.add("pro");
            labels.add("com");
            labels.add("out");


            dataset.setColors(new int[]{ContextCompat.getColor(getApplicationContext(), R.color.black),
                    ContextCompat.getColor(getApplicationContext(), R.color.orange),
                    ContextCompat.getColor(getApplicationContext(), R.color.blue),
                    ContextCompat.getColor(getApplicationContext(), R.color.yellow),
                    ContextCompat.getColor(getApplicationContext(), R.color.green),
                    ContextCompat.getColor(getApplicationContext(), R.color.red)

            });

            BarChart chart = (BarChart) findViewById(R.id.chart);

            Log.d("Bar chart", "chart created");
            BarData data = new BarData(labels, dataset);
            chart.setData(data);
            dataset.setBarSpacePercent(45f);

            Log.d("Bar chart", "data inserted");

            chart.setDescription("");
            chart.getLegend().setTextColor(Color.MAGENTA);
            chart.getLegend().setTextSize(20f);
            chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

            Log.d("Bar chart", "Legend");
            Log.d("Bar chart", descriptionList.get(i));


            chart.animateX(3000);




        }
        else{
            Toast.makeText(getApplicationContext(),"No more graphs",Toast.LENGTH_LONG).show();
            ivForward.setVisibility(View.INVISIBLE);
        }
    }


    //  Facility Adapter


    class SpinnerGraphsAdapter extends ArrayAdapter<String> {

        private Context context;
        private  ArrayList<String> values;
        LayoutInflater inflater;

        public SpinnerGraphsAdapter(Context context,int textViewResourceId,ArrayList<String> values){
            super(context,textViewResourceId,values);
            this.context = context;
            this.values = values;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public int getCount() {return  values.size();}
        public String getItem(int position) {return values.get(position);}
        public int getPosition(String description){
            int position = 0 ;
            for(int i = 0; i < values.size(); i++) {
                if (values.get(i).equals(description)) {
                    position = i;
                    break;
                }
            }
            return position;
        }
        public View getView (int position, View convertView, ViewGroup parent){
            View itemView = inflater.inflate(R.layout.measure_spinner,parent,false);
            String facilityId = values.get(position);
            TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvName.setText(facilityId);

            return itemView;
        }
        public View getDropDownView(int position, View convertView, ViewGroup parent){
            return  getView(position,convertView,parent);
        }

    }



    @Override
    public int getLayoutResource(){return R.layout.activity_graph;}
}
