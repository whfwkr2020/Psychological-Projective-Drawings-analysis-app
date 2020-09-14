package com.example.team2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HTPresult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htpresult);

        Button back;

        back = findViewById(R.id.htpback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent11);
            }
        });
    }
}
