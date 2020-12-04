package com.example.team2020;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;

public class MyPage extends AppCompatActivity {

    Button menu, test, journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_layout);

        MyPageFragment2 fragment2 = new MyPageFragment2();
        changeFragment(fragment2);

        menu = findViewById(R.id.menu);
        test = findViewById(R.id.testrecord);
        journal = findViewById(R.id.journalrecord);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent10 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent10);
            }
        }); //menu 버튼 선택시


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageFragment2 fragment2 = new MyPageFragment2();
                changeFragment(fragment2);
            }
        }); //test 버튼 선택시

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageFragment3 fragment3 = new MyPageFragment3();
                changeFragment(fragment3);
            }
        }); //journal 버튼 선택시

    }

    public void changeFragment(Fragment fragment)
    {
       FragmentManager fragmentManager = getFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
