package trd.ams.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import trd.ams.R;

public class ImageActivity extends TitleBarActivity {

    ImageView iv;
    Context context ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupTitleBar(getResources().getString(R.string.app_name), false, false, false);
            context = getApplicationContext();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"BACK");
                startActivity(new Intent(ImageActivity.this,StatusActivity.class));

            }
        });

        ivForward = (ImageView) findViewById(R.id.iv_forward);
        ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"FORWARD");
                startActivity(new Intent(ImageActivity.this, StatusActivity.class));
            }
        });


        setContentView(R.layout.activity_image);

        // getting uri from status activity

        Intent i = getIntent();
        Uri myUri = Uri.parse(i.getStringExtra("imageUri"));
        iv = (ImageView)findViewById(R.id.iv);
        iv.setImageURI(myUri);
        Log.d("TAG","displayed");

           // Getting byte array from Status activity
       /* Bundle extras = getIntent().getExtras();
        byte[] data = extras.getByteArray("bArray");
        Log.d("TAG","received byte array intent");
        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
        Log.d("TAG","Array to bitmap");
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"Image",null);
        Log.d("TAG","bitmap to path");
        Uri decodedUri = Uri.parse(path);
        Log.d("TAG","path to uri");
        iv = (ImageView)findViewById(R.id.iv);
        iv.setImageURI(decodedUri);
        Log.d("TAG","decoded & displayed");*/


        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageActivity.this, StatusActivity.class));
            }
        });

    }
}
