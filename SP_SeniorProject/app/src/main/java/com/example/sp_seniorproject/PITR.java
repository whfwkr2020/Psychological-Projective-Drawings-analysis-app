package com.example.sp_seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PITR extends AppCompatActivity {
    Button toPick, sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitr1_layout);

        toPick = findViewById(R.id.toPick2);
        sub = findViewById(R.id.submitpitr);

        toPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), Test.class);
                startActivity(intent1);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), PITRresult.class);
                startActivity(intent1);
            }
        });

    }
}
