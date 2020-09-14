package com.example.team2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Journal extends AppCompatActivity {

    Button menu, sub;
    EditText jour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_layout);

        menu = findViewById(R.id.toMenu);
        jour = findViewById(R.id.jour);
        sub = findViewById(R.id.submithtp);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1323 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent1323);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1323 = new Intent(v.getContext(), JournalResult.class);
                startActivity(intent1323);
            }
        });
    }
}
