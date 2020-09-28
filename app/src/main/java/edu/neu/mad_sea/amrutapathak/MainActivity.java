package edu.neu.mad_sea.amrutapathak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnAbout;
    private Button btnError;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDescActivity();
            }
        });

        btnError = (Button) findViewById(R.id.btnError);
        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateError();
            }
        });

        TextView tvVersionCode = (TextView) findViewById(R.id.tvVersionCode);
        tvVersionCode.setText("Version Code : "+BuildConfig.VERSION_CODE);

        TextView tvVersionName = (TextView) findViewById(R.id.tvVersionName);
        tvVersionName.setText("Version Name : "+BuildConfig.VERSION_NAME);
    }

    public void openDescActivity(){
        Intent intent = new Intent(this, DescriptionActivity.class);
        startActivity(intent);
    }

    private void generateError(){
        throw new NullPointerException("An exception occurred");
    }

}