package com.example.sp_seniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.sp_seniorproject.map.MapActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Button menumenu, alert, test, journal, mypage;
    Button mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * 키 해시 찾기 위한 코드
         * 키 해시: 1rMhx1Ujj2c39CzgmwgO2qdmWrU=
         *
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.sp_seniorproject", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHas", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        */

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
                map_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "지도", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
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
                overridePendingTransition(0, 0);
            }
        }); //테스트 선택시

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), Journal.class);
                startActivity(intent2);
                overridePendingTransition(0, 0);
            }
        }); //그림일기 선택시

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(v.getContext(), MyPage.class);
                startActivity(intent3);
                overridePendingTransition(0, 0);
            }
        }); //마이페이지 선택시




//        mapBtn = (Button) findViewById(R.id.showMap);
//        mapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MapActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
