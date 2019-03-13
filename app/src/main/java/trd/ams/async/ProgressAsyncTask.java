package trd.ams.async;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itextpdf.text.pdf.PdfWriter;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import trd.ams.R;
import trd.ams.activity.ProgressActivity;
import trd.ams.common.Constants;
import trd.ams.common.Globals;
import trd.ams.database.DatabaseHelper;
import trd.ams.dto.AssetMasterDataDto;
import trd.ams.dto.MasterDto;
import trd.ams.util.Utils;

public class ProgressAsyncTask extends AsyncTask {
    private Activity activity;
    private android.content.Context context;
    String facilityId;
    AssetMasterDataDto amdfac;
    DatabaseHelper dbhelper = null;
    String sql;
    Cursor cursor = null;
    SQLiteDatabase db;
    private Byte[] reportProgress;
    Globals globals;
    String status = "";
    String FILE = Environment.getExternalStorageDirectory()
            + "/Progress.pdf";
    String url;


    String TAG = "ProgressAsyncTask";

    public ProgressAsyncTask(Activity activity,android.content.Context context){
        Log.d(TAG, "ProgressAsyncTask**");
        this.activity = activity;
        this.context = context;
        globals=(Globals)activity.getApplication();

        dbhelper = DatabaseHelper.getInstance(context);
        net.sqlcipher.database.SQLiteDatabase db = dbhelper.getReadableDatabase("Wf@trd841$ams327");

        String facility_name = globals.getFacilityName();
        try {
            if (db != null) {
                sql = "select facility_id from facility where facility_name = ?";
                cursor = db.rawQuery(sql, new String[]{facility_name});
                Log.d("Facility ****", "count is***" + cursor.getCount());
                while (cursor.moveToNext()) {
                    facilityId = cursor.getString(0);
                }
                Log.d(TAG, "fac::" + facilityId);
                cursor.close();
            } else {
                Log.d(TAG, "no facility");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //facilityId = globals.getFacilityId();

    }

    public void onPreExecute(){
        Log.d( TAG, "onPreExecute***");
        ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
        progressBar.setVisibility(View.VISIBLE);

    }

    @SuppressLint("ResourceAsColor")
    protected Object doInBackground(Object[] objects) {

        Log.d(TAG, "doInBackGround**");
        String result = "";
        try {
              if (!Utils.isOnline())
                {
                     status = "NO_CONNECTIVITY";
                     Log.v(TAG, status);
                    Handler handler =  new Handler(context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(context, R.string.internet,Toast.LENGTH_LONG).show();
                        }
                    });
                } else {

                        result = syncProgressTable(true);

                  if (result == "recvfrom failed: ECONNRESET (Connection reset by peer)") {
                      Handler handler = new Handler(context.getMainLooper());
                      handler.post(new Runnable() {
                          public void run() {
                              Toast.makeText(context, R.string.server, Toast.LENGTH_LONG).show();

                          }
                      });
                  }

                  ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
                  progressBar.setVisibility(View.INVISIBLE);
                  if(this.activity instanceof ProgressActivity) {
                      Button progress = (Button) activity.findViewById(R.id.btn_submit_progress);
                      progress.setBackgroundColor(R.color.buttons);
                  }
                  Log.d(TAG,"Progress result--"+result);




                    }
           }catch (Exception e) {

            Log.e(TAG, "doInBackground: " + e.toString());
        }
        return result;
    }



    @SuppressLint("ResourceAsColor")
    private String syncProgressTable(Boolean setup){
        String responses="";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();


                     MasterDto masterDto = new MasterDto();
                     masterDto.setFacilityId(facilityId);
                     Log.d(TAG,"facility is::"+masterDto.getFacilityId());
                     url = globals.getHOST_URL_BY_USER();
        String progressUrl = url+ "/warehouse/rest/get-report-progress";
                   //  masterDto.setReportProgress(reportProgress);


        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new ObjectMapper().writeValueAsString(masterDto));
           Request request = new Request.Builder().url(progressUrl).post(requestBody).build();
        // Request request = new Request.Builder().url(Constants.PROGRESS_TABLE_URL).post(requestBody).build();
            Log.d(TAG, "..first......." + request);
            Response response = okHttpClient.newCall(request).execute();
            Log.d(TAG, "..second......." + response);
            String output = response.body().string();
            Log.d(TAG, "Response Body *** " + output);

            MasterDto dto = new ObjectMapper().readValue(output,MasterDto.class);
            Log.d(TAG,"acquired--" +dto.getReportProgress());
            Object obj = dto.getReportProgress();
            Log.d(TAG,"acquired--" +obj);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byte [] data = bos.toByteArray();
            Log.d(TAG,"data [] created --"+data);

            FileOutputStream outValue = new FileOutputStream(FILE);
            outValue.write(data);
            outValue.close();
            Log.d(TAG,"written--"+data);


            /*ProgressBar progressBar = activity.findViewById(R.id.progressBarTable);
            progressBar.setVisibility(View.INVISIBLE);
*/

           /* ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            int no = byteArrayInputStream.read();
            String text = "";
            while (no!=-1){
                text = text + (char)no;
                no = byteArrayInputStream.read();
                Log.d(TAG,"no--"+no);
                Log.d(TAG,"text is--"+text);
            }
            Log.d(TAG,"FROM SERVER--"+text);*/

           /* if(this.activity instanceof ReportsActivity){
                Intent i =new Intent(activity, ServerProgressTableActivity.class);
                i.putExtra("text",text);
                activity.startActivity(i);
            }*/

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            fOut.write(data);
            fOut.close();

            Log.d(TAG,"new file created");

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();

            PdfWriter.getInstance(document,fOut);

            viewPdf("newFile.pdf", "Dir");

          /*  if(this.activity instanceof ProgressActivity) {
                Handler handler =  new Handler(context.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Button progress = activity.findViewById(R.id.btn_submit_progress);
                        progress.setBackgroundColor(R.color.buttons);
                    }
                });

            }*/

            Log.d(TAG,"written to PDF");



          /* try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream(FILE);
              // FileOutputStream fileOutputStream = new FileOutputStream(FILE);
               byte bytes[]  = text.getBytes("UTF-8");
               stream.write(bytes);
               stream.close();
               Log.d(TAG,"written to file");
           }catch (Exception e){
               e.printStackTrace();
           }*/



        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return responses;
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
       // Uri path = Uri.fromFile(pdfFile);
        Uri path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider",pdfFile);

        Log.d(TAG,"setting up");

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
       // pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            activity.startActivity(pdfIntent);
            Log.d(TAG,"showing");
        } catch (ActivityNotFoundException e) {
            Log.d(TAG,"can't read pdf file----" +e);
        }
    }


      public void onBackpressed()
    {

    }


}
