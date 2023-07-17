package com.example.pharmacymgmnt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class splahsqr extends AppCompatActivity {
    Button scanbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splahsqr);
        scanbtn=findViewById(R.id.scanqr);
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });
    }

    private void scanCode() {
        ScanOptions scanOptions=new ScanOptions();
        scanOptions.setPrompt("Press Volume Up For FlashLight");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {
            Toast.makeText(this,result.getContents() , Toast.LENGTH_LONG).show();
            Intent intent=new Intent(splahsqr.this, MainActivity.class);
            intent.putExtra("Data",result.getContents());
            startActivity(intent);
        }
    });
}