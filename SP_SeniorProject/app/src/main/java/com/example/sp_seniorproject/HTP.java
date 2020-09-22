package com.example.sp_seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HTP extends AppCompatActivity {
    Button back, submithtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htp1_layout);

        back = findViewById(R.id.toPick);
        submithtp = findViewById(R.id.submithtp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), Test.class);
                startActivity(intent1);
            }
        });

        submithtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), HTPresult.class);
                startActivity(intent1);
            }
        });
    }
}
