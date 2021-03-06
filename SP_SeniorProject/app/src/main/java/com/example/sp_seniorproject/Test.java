package com.example.sp_seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Test extends AppCompatActivity {

    Button back, htp, pitr;

    public static Activity test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        test = Test.this;

        back = findViewById(R.id.back);
        htp = findViewById(R.id.htp);
        pitr = findViewById(R.id.pitr);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              test.finish();
              overridePendingTransition(0, 0);
            }
        }); //back 버튼 선택시

        htp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent4 = new Intent(v.getContext(), HTP.class);
              startActivity(intent4);
              overridePendingTransition(0, 0);
              test.finish();
            }
        }); //htp 버튼 선택시

        pitr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent5 = new Intent(v.getContext(), PITR.class);
              startActivity(intent5);
              overridePendingTransition(0, 0);
              test.finish();
            }
        }); //pitr 버튼 선택시
    }
}
