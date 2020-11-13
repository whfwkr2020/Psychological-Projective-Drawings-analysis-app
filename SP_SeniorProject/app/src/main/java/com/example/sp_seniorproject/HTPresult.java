package com.example.sp_seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HTPresult extends AppCompatActivity {

  public static Activity htpresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htpresult);

        htpresult = HTPresult.this;

        Button back;

        back = findViewById(R.id.htpback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent1);
                htpresult.finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}
