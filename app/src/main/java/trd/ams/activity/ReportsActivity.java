package trd.ams.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import trd.ams.R;
import trd.ams.async.ProgressAsyncTask;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.Facility;

public class ReportsActivity extends TitleBarActivity {


    SQLiteDatabase db;
    DatabaseHelper dbhelper = null;
    String sqlP, st;
    Cursor cursor = null ;
    Facility facility;
    String args[] =  {} ;
    private Context context;
    private ProgressAsyncTask progressAsync;
    Button btnProgress, btn_asHistory, btn_asRecord, btn_overdue, btn_graph;
    TextView tv_progress_server;
    Globals globals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globals = (Globals)getApplication();
        setupTitleBar(getResources().getString(R.string.app_name), false, false, true);
       // setContentView(R.layout.activity_reports);
        context = this;
        btnProgress = findViewById(R.id.btn_progress);
        btn_asHistory = findViewById(R.id.btn_ashistory);
        btn_asRecord = findViewById(R.id.btn_asrecord);
        btn_overdue = findViewById(R.id.btn_overdue);
        btn_graph  =findViewById(R.id.btn_graphs);

        tv_progress_server = findViewById(R.id.tv_progress_server);

        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* new Handler().post(new Runnable()
                {
                    public void run(){
                    progressAsync = new ProgressAsyncTask(ReportsActivity.this,getApplicationContext());
                    progressAsync.execute();
                }
                });*/

               startActivity(new Intent(ReportsActivity.this, ProgressActivity.class));
            }
        });

        btn_asHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        historyAsync = new HistoryAsyncTask(ReportsActivity.this, getApplicationContext());
                        historyAsync.execute();
                    }
                });*/

                startActivity(new Intent(ReportsActivity.this, AsHistoryActivity.class));

            }
        });

        btn_asRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsActivity.this,AsRecordActivity.class));
            }
        });

        btn_overdue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsActivity.this, OverdueActivity.class));
            }
        });

        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsActivity.this,AssetScheduleGraphActivity.class));
            }
        });








      /*  //SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date d = Calendar.getInstance().getTime();
        System.out.println("Current time => " + d);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(d);

        ZoomControls zc = (ZoomControls) findViewById(R.id.zc);

        TextView tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_progress.setText("Progress of Maintenance Schedule of OHE/MLY  assets-  "+date);

        final TableLayout tableLayout = (TableLayout)findViewById(R.id.tablelayout);
        //setupTitleBar(app_name, true, false, true);
        dbhelper = DatabaseHelper.getInstance(ReportsActivity.this);
        net.sqlcipher.database.SQLiteDatabase db = dbhelper.getReadableDatabase("Wf@trd841$ams327");


        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#3f51b5"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Description" , "Total Population" , "Frequency" , "Month Target" , "Apr" , "May" , "Jun" , "Jul" , "Aug" , "Sep" ,"Oct" , "Nov" , "Dec" , "Jan" , "Feb" , "Mar" ,"Total" ,"Target Till Month" ,"Cum Progress" ,"Cum % of SCH Done"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(20);
            tv.setPadding(10, 15, 10, 15);
            tv.setText(c);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setBackgroundResource(R.drawable.cell_shape);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);


        try{
            Log.d("TAG", "MAIN:::" +db);
            if(db != null){
                sqlP = " select \n" +
                        "AT_ST_description  description , Total_Population, frequency, month_target,\n" +
                        "apr_cnt, may_cnt, juN_cnt, jul_cnt, aug_cnt, sep_cnt,\n" +
                        "oct_cnt, nov_cnt, dec_cnt, jan_cnt, feb_cnt, mar_cnt,\n" +
                        "(apr_cnt+may_cnt+jun_cnt+jul_cnt+aug_cnt+sep_cnt+oct_cnt+ nov_cnt+dec_cnt+ jan_cnt +feb_cnt+mar_cnt) as Total,\n" +
                        "avg_cum_month_target_till_month target_till_month ,\n" +
                        "tprog  progress,\n" +
                        "CASE when (avg_cum_month_target_till_month is null or avg_cum_month_target_till_month = 0 ) \n" +
                        "then 0 else ROUND((tprog/avg_cum_month_target_till_month*100),2) end as Percentage_of_progress\n" +
                        "\n" +
                        "from\n" +
                        "(\n" +
                        "\n" +
                        "Select  AT_ST_description, Total_Population, frequency, Asset_type , Schedule_code, month_target, avg(cum_month_target_till_month) avg_cum_month_target_till_month,\n" +
                        "case when sum(apr_cnt) is null THEN 0 else sum(apr_cnt)/12  end as  apr_cnt ,\n" +
                        "case when sum(may_cnt) is null THEN 0 else sum(may_cnt)/12  end as  may_cnt ,\n" +
                        "case when sum(jun_cnt) is null THEN 0 else sum(jun_cnt)/12  end as  jun_cnt ,\n" +
                        "case when sum(jul_cnt) is null THEN 0 else sum(jul_cnt)/12  end as  jul_cnt ,\n" +
                        "case when sum(aug_cnt) is null THEN 0 else sum(aug_cnt)/12  end as  aug_cnt ,\n" +
                        "case when sum(sep_cnt) is null THEN 0 else sum(sep_cnt)/12  end as  sep_cnt ,\n" +
                        "case when sum(oct_cnt) is null THEN 0 else sum(oct_cnt)/12  end as  oct_cnt ,\n" +
                        "case when sum(nov_cnt) is null THEN 0 else sum(nov_cnt)/12  end as  nov_cnt ,\n" +
                        "case when sum(dec_cnt) is null THEN 0 else sum(dec_cnt)/12  end as  dec_cnt ,\n" +
                        "case when sum(jan_cnt) is null THEN 0 else sum(jan_cnt)/12  end as  jan_cnt ,\n" +
                        "case when sum(feb_cnt) is null THEN 0 else sum(feb_cnt)/12 end as  feb_cnt ,\n" +
                        "case when sum(mar_cnt) is null THEN 0 else sum(mar_cnt)/12 end as  mar_cnt ,\n" +
                        "(sum(apr_cnt)+sum(may_cnt)+sum(jun_cnt)+sum(jul_cnt)+sum(aug_cnt)+sum(sep_cnt)+sum(oct_cnt)+sum(nov_cnt)+sum(dec_cnt)+sum(jan_cnt)+sum(feb_cnt)+sum(mar_cnt))/12 tprog\n" +
                        "From\n" +
                        "(\n" +
                        "\n" +
                        "\n" +
                        "\n" +

                        "\n" +
                        "   \n" +
                        "               select   \n" +
                        "\t\t\t   as12m.Asset_type , \n" +
                        "\t\t\t   \n" +
                        "\t\t\t  as12m.Schedule_code  ,\n" +
                        "\t\t\t    as12m.Asset_type||' - '||as12m.Schedule_code  AT_ST_description ,\n" +
                        "              Case when Total_Population is null THEN 0 else Total_Population end as Total_Population,\n" +
                        "               as12m.Asset_type , as12m.Schedule_code,\n" +
                        "               case when month_target is null then 0 else month_target end month_target,\n" +
                        "               case when cum_month_target_till_month is null then 0 else cum_month_target_till_month end cum_month_target_till_month,\n" +
                        "               case when Mon_cnt is null then 0 else Mon_cnt end Mon_cnt, frequency ,\n" +
                        "\n" +
                        "\t\t\t\tprog_of_year, prog_of_month, prog_of_year||'-'||prog_of_month,\n" +
                        "                    case when prog_of_month != 4  then  0 else   mon_cnt end as apr_cnt  ,\n" +
                        "                    case when prog_of_month != 5  then  0 else   mon_cnt end as may_cnt  ,\n" +
                        "                    case when prog_of_month != 6  then  0 else   mon_cnt end as jun_cnt  ,\n" +
                        "                    case when prog_of_month != 7  then  0 else   mon_cnt end as jul_cnt  ,\n" +
                        "                    case when prog_of_month != 8  then  0 else   mon_cnt end as aug_cnt  ,\n" +
                        "                    case when prog_of_month != 8  then  0 else   mon_cnt end as sep_cnt  ,\n" +
                        "                    case when prog_of_month != 10 then  0 else   mon_cnt end as oct_cnt  ,\n" +
                        "                    case when prog_of_month != 11 then  0 else   mon_cnt end as nov_cnt  ,\n" +
                        "                    case when prog_of_month != 12 then  0 else   mon_cnt end as dec_cnt  ,\n" +
                        "                    case when prog_of_month != 1  then  0 else   mon_cnt end as jan_cnt  ,\n" +
                        "                    case when prog_of_month != 2  then  0 else   mon_cnt end as feb_cnt  ,\n" +
                        "                    case when prog_of_month != 3  then  0 else   mon_cnt end as mar_cnt  \n" +
                        "                   \n" +
                        "               from (\n" +
                        "\t\t\t\t\t\t\t\n" +
                        "                              select stdate,\n" +
                        "\t\t\t\t\t\t\t  (strftime('%Y',date(stdate))) y1,  \n" +
                        "\t\t\t\t\t\t\t  (strftime('%m',date(stdate))) m1,   FY, \n" +
                        "\t\t\t\t\t\t\t   \n" +
                        "                              asa1.Asset_type , asa1.schedule_code, \n" +
                        "                              asa1.duration,\n" +
                        "                              case when Schedule_code = 'AOH' then 'Yearly' \n" +
                        "                              when Schedule_code = 'QTR' then 'Quarterly' \n" +
                        "                              when Schedule_code = 'HY' then 'Half Yearly ' \n" +
                        "                              when Schedule_code = 'MON' then 'Monthly' \n" +
                        "                              when Schedule_code = 'Monthly' then 'Monthly' \n" +
                        "                              when Schedule_code = 'WEEK' then 'Weekly' \n" +
                        "                              when Schedule_code = 'POH' then  case when duration is null then 'Duration not defined' else duration||' Years' end  end frequency\n" +
                        "                              from\n" +
                        "                              (\n" +
                        " \n" +
                        "                                            \tSelect stdate, m1 m22,  y1,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\tcase when strftime('%m',date('now')) <=3 then (strftime('%Y',date('now'))-1)||'-'|| (strftime('%Y',date('now'))%100)\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\telse (strftime('%Y',date('now'))) ||'-'|| ((strftime('%Y',date('now'))+1)%100) end as FY\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\tfrom\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t(\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\tWITH RECURSIVE dates(date) AS (\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  VALUES(  date('now', 'localtime', '-3 months', 'start of year', '+3 months')   )\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  UNION ALL\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  SELECT date(date, '+1 month')\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  FROM dates\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  WHERE date < date('now', 'localtime', '-3 months', 'start of year', '+1 year', '+2 months', '-1 day' )\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t)\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\tSELECT date stdate, strftime('%m',date) m1, strftime('%Y',date) y1 FROM dates\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\torder by date\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t)\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\n" +
                        "                              ) b , asset_schedule_assoc asa1, product_category_member pcm1 \n" +
                        "                              where asa1.asset_type = pcm1.Product_id\n" +
                        "                              and pcm1.product_category_id = 'OHE_FIXED_ASSET'\n" +
                        "                              and asa1.Schedule_code not in ('UNSCHD' , 'COMM' ) \n" +
                        "                              order by asset_type, Schedule_code, y1, m1\n" +
                        "\t\t\t\t\t\t\t  ) as12m \n" +
                        "\n" +
                        "\t\t\t    left Outer join \n" +
                        "\n" +
                        " \n" +
                        "               (\n" +
                        "               select count(*) Total_Population , Asset_type\n" +
                        "               from Asset_master_data\n" +
                        "               where Facility_id ='10073'\n" +
                        "               Group by Asset_type\n" +
                        "               ) AP on AP.asset_type = as12m.Asset_type  \n" +
                        " \n" +
                        "\n" +
                        "               left Outer join \n" +
                        "\n" +
                        "\n" +
                        "               (\n" +
                        "               Select asset_type, schedule_type, fy,\n" +
                        "                  CASE\n" +
                        "                   WHEN ash.month1 = 1 THEN vmct.target_jan\n" +
                        "                   WHEN ash.month1 = 2 THEN vmct.target_feb\n" +
                        "                   WHEN ash.month1 = 3 THEN vmct.target_mar\n" +
                        "                   WHEN ash.month1 = 4 THEN vmct.target_apr\n" +
                        "                   WHEN ash.month1 = 5 THEN vmct.target_may\n" +
                        "                   WHEN ash.month1 = 6 THEN vmct.target_jun\n" +
                        "                   WHEN ash.month1 = 7 THEN vmct.target_jul\n" +
                        "                   WHEN ash.month1 = 8 THEN vmct.target_aug\n" +
                        "                   WHEN ash.month1 = 9 THEN vmct.target_sep\n" +
                        "                   WHEN ash.month1 = 10 THEN vmct.target_oct\n" +
                        "                   WHEN ash.month1 = 11 THEN vmct.target_nov\n" +
                        "                   WHEN ash.month1 = 12 THEN vmct.target_dec\n" +
                        "                   ELSE '' \n" +
                        "\t\t\t\t  \n" +
                        "               END AS month_target ,\n" +
                        "               CASE\n" +
                        "                   WHEN ash.month1 = 1 THEN vmct.cum_target_jan\n" +
                        "                   WHEN ash.month1 = 2 THEN vmct.cum_target_feb\n" +
                        "                   WHEN ash.month1 = 3 THEN vmct.cum_target_mar\n" +
                        "                   WHEN ash.month1 = 4 THEN vmct.cum_target_apr\n" +
                        "                   WHEN ash.month1 = 5 THEN vmct.cum_target_may\n" +
                        "                   WHEN ash.month1 = 6 THEN vmct.cum_target_jun\n" +
                        "                   WHEN ash.month1 = 7 THEN vmct.cum_target_jul\n" +
                        "                   WHEN ash.month1 = 8 THEN vmct.cum_target_aug\n" +
                        "                   WHEN ash.month1 = 9 THEN vmct.cum_target_sep\n" +
                        "                   WHEN ash.month1 = 10 THEN vmct.cum_target_oct\n" +
                        "                   WHEN ash.month1 = 11 THEN vmct.cum_target_nov\n" +
                        "                   WHEN ash.month1 = 12 THEN vmct.cum_target_dec\n" +
                        "                   ELSE '' \n" +
                        "               END AS cum_month_target_till_month\n" +
                        "               from v_monthly_cum_targets vmct, \n" +
                        "                              (select strftime('%m',date('now') ) month1 , strftime('%Y',date('now') ) yy1 )ash\n" +
                        "               where facility_id='10073'\n" +
                        "               ) tar on as12m.Asset_type = tar.Asset_type  and  as12m.Schedule_code = tar.schedule_type and as12m.fy= tar.fy\n" +
                        " \n" +
                        "               left outer join\n" +
                        " \n" +
                        "               (\n" +
                        "               Select count(*) Mon_cnt , Asset_type  , Schedule_code , \n" +
                        "\t\t\t   strftime('%m',date(schedule_date) ) prog_of_month,\n" +
                        "\t\t\t   strftime('%Y',date(schedule_date) ) prog_of_year,\n" +
                        "\t\t\t  \n" +
                        "\t\t\t \n" +
                        "\t\t\t   fy\n" +
                        "               from v_assets_schedule_history\n" +
                        "               where facility_id='10073'\n" +
                        "               group by Asset_type  , Schedule_code , strftime('%m',date(schedule_date) ) , strftime('%Y',date(schedule_date) ) , fy\n" +
                        "               order by prog_of_year, prog_of_month\n" +
                        "               ) mp\n" +
                        "               on as12m.Asset_type = mp.Asset_type  and  as12m.Schedule_code = mp.schedule_code and as12m.fy= mp.fy\n" +
                        " \n" +
                        "               \n" +
                        "               order by 1 , 2\n" +
                        "               ) mgrp\n" +
                        "\t\t\t   \n" +
                        "\t\t\t   \n" +
                        "\t\t\t   \n" +
                        "group by AT_ST_description, Total_Population, Asset_type , Schedule_code, month_target , frequency\n" +
                        "\n" +
                        "order by AT_ST_description,   Asset_type , Schedule_code\n" +
                        ") final\n ";

                cursor = db.rawQuery(sqlP, args);


                Log.d("Progress Table ****",  "count is***" +cursor.getCount());

                StringBuilder sb =new StringBuilder();

                while(cursor.moveToNext()){
                    String description = cursor.getString(0);
                    String Total_Population= cursor.getString(1);
                    String frequency = cursor.getString(2);
                    String month_target = cursor.getString(3);
                   // String avg_cum_month_target_till_month = cursor.getString(4);
                    // String Asset_type = cursor.getString(5);
                   // String Schedule_code = cursor.getString(6);
                    String apr_cnt = cursor.getString(4);
                    String may_cnt = cursor.getString(5);
                    String jun_cnt = cursor.getString(6);
                    String jul_cnt = cursor.getString(7);
                    String aug_cnt = cursor.getString(8);
                    String sep_cnt = cursor.getString(9);
                    String oct_cnt = cursor.getString(10);
                    String nov_cnt = cursor.getString(11);
                    String dec_cnt = cursor.getString(12);
                    String jan_cnt = cursor.getString(13);
                    String feb_cnt = cursor.getString(14);
                    String mar_cnt = cursor.getString(15);
                    String Total = cursor.getString(16);
                    String target_till_month = cursor.getString(17);
                    String progress = cursor.getString(18);
                    String percentage_of_progress = cursor.getString(19);

                    //Log.d("TAG","AssetTypes are::" +Asset_type);



                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={description,Total_Population, frequency, month_target,apr_cnt, may_cnt, jun_cnt, jul_cnt, aug_cnt, sep_cnt,oct_cnt, nov_cnt, dec_cnt, jan_cnt, feb_cnt, mar_cnt,Total,target_till_month,progress,percentage_of_progress};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        tv.setTextColor(Color.BLACK);
                        tv.setBackgroundResource(R.drawable.cell_shape);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                  //  sb.append(description).append(";").append(Total_Population).append(";").append(frequency).append(";").append(month_target).append(";").append(avg_cum_month_target_till_month).append(";").append(Asset_type).append(";").append(Schedule_code).append(";").append(apr_cnt).append(";").append(may_cnt).append(";").append(jun_cnt).append(";").append(jul_cnt).append(";").append(aug_cnt).append(";").append(sep_cnt).append(";").append(oct_cnt).append(";").append(nov_cnt).append(";").append(dec_cnt).append(";").append(jan_cnt).append(";").append(feb_cnt).append(";").append(mar_cnt).append(";").append(Total).append(";").append(target_till_month).append(";").append(progress).append(";").append(percentage_of_progress).append("_");
                }

                    cursor.close();
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        zc.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x = tableLayout.getScaleX();
                float y = tableLayout.getScaleY();

                 tableLayout.setScaleX(x+0.2f);
                 tableLayout.setScaleY(y+0.2f);
            }
        });

        zc.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x = tableLayout.getScaleX();
                float y = tableLayout.getScaleY();

                   tableLayout.setScaleX(x-0.2f);;
                   tableLayout.setScaleY(y-0.2f);

            }
        });
*/




    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_reports;
    }


}
