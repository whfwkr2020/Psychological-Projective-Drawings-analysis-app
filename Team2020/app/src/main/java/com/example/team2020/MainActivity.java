package com.example.team2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button menumenu, alert, test, journal, mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menumenu = findViewById(R.id.menu);
        alert = findViewById(R.id.alert);
        test = findViewById(R.id.test);
        journal = findViewById(R.id.journal);
        mypage = findViewById(R.id.mypage);

        menumenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }); //메뉴 버튼 선택시

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popupwindow, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});
                //주변 병원 찾기 버튼
                Button map_btn = (Button) popupView.findViewById(R.id.map_btn);
                map_btn.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "지도", Toast.LENGTH_SHORT).show();
                    }
                });
                popupWindow.showAsDropDown(alert, 50, -30);
        }
        }); //알림 버튼 선택시

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent1 = new Intent(v.getContext(), Test.class);
                    startActivity(intent1);
            }
        }); //테스트 선택시

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), Journal.class);
                startActivity(intent2);
            }
        }); //그림일기 선택시

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(v.getContext(), MyPage.class);
                startActivity(intent3);
            }
        }); //마이페이지 선택시
    }
}