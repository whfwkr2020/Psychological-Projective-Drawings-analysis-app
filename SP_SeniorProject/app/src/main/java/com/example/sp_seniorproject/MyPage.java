package com.example.sp_seniorproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {

    Button menu, user, test, journal;

    public static Activity mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_layout);

        mypage = MyPage.this;

        MyPageFragment1 fragment1 = new MyPageFragment1();
        changeFragment(fragment1);

        menu = findViewById(R.id.menu);
        user = findViewById(R.id.my);
        test = findViewById(R.id.testrecord);
        journal = findViewById(R.id.journalrecord);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mypage.finish();
               overridePendingTransition(0, 0);
            }
        }); //menu 버튼 선택시

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageFragment1 fragment1 = new MyPageFragment1();
                changeFragment(fragment1);
            }
        }); //user 버튼 선택시

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
