package com.example.sp_seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PITRresult extends AppCompatActivity {

  public static Activity pitrresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitrresult);

        pitrresult = PITRresult.this;

        Button back;

        back = findViewById(R.id.pitrback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              pitrresult.finish();
              overridePendingTransition(0, 0);
            }
        });
    }
}
