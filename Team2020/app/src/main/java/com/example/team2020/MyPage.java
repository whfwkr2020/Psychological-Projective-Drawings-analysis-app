package com.example.team2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {

    Button menu, user, test, journal, infochange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_layout);

        menu = findViewById(R.id.menu);
        user = findViewById(R.id.my);
        test = findViewById(R.id.testrecord);
        journal = findViewById(R.id.journalrecord);
        infochange = findViewById(R.id.infochange12);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent10 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent10);
            }
        }); //menu 버튼 선택시

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(v.getContext(), MyPage.class);
                startActivity(intent11);
            }
        }); //user 버튼 선택시

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12 = new Intent(v.getContext(), MyPage2.class);
                startActivity(intent12);
            }
        }); //test 버튼 선택시

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent13 = new Intent(v.getContext(), MyPage3.class);
                startActivity(intent13);
            }
        }); //journal 버튼 선택시

        infochange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent10 = new Intent(v.getContext(), MyPage12.class);
                startActivity(intent10);
            }
        }); //infochange 버튼 선택시
    }
}
