package edu.neu.mad_sea.amrutapathak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    private static final String TAG = "DescriptionActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Log.d(TAG, "onCreate: started.");
        ImageView selfImage = findViewById(R.id.ivSelfImage);
        int selfImageSrc = getResources().getIdentifier("@drawable/selfimage", null, this.getPackageName());
        selfImage.setImageResource(selfImageSrc);




    }

}