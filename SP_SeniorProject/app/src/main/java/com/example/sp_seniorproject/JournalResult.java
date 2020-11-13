package com.example.sp_seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class JournalResult extends AppCompatActivity {

  public static Activity journalresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journalresult);

        journalresult = JournalResult.this;

        Button back;

        back = findViewById(R.id.journalback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              journalresult.finish();
              overridePendingTransition(0, 0);
            }
        });
    }
}
