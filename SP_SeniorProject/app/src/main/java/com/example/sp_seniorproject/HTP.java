package com.example.sp_seniorproject;

import android.app.Activity;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sp_seniorproject.firebase.TestData;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.os.Environment.DIRECTORY_PICTURES;

public class HTP extends AppCompatActivity {
    Button back, totree;

    public static Activity htp;

    MyPaintView view;
    int tColor, n = 0;

    private View mLayout;

    //TODO
    DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htp1_layout);
        htp = HTP.this;

        back = findViewById(R.id.toPick);
        totree = findViewById(R.id.totree);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              htp.finish();
              overridePendingTransition(0, 0);
          }
        });

        totree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent1 = new Intent(v.getContext(), HTP2.class);
              startActivity(intent1);
              overridePendingTransition(0, 0);
            }
        });

        view = new MyPaintView(this);

        LinearLayout container = findViewById(R.id.container);
        Resources res = getResources();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        container.addView(view, params);

        RadioButton rBtn1 = findViewById(R.id.radioButton);
        RadioButton rBtn2 = findViewById(R.id.radioButton2);
        RadioButton rBtn3 = findViewById(R.id.radioButton3);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        view.setCap(0);
                        break;
                    case R.id.radioButton2:
                        view.setCap(1);
                        break;
                    case R.id.radioButton3:
                        view.setCap(2);
                        break;
                }
            }
        });

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {


                Snackbar.make(mLayout, "이 앱을 실행하려면 카메라와 외부 저장소 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        ActivityCompat.requestPermissions(HTP.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {

                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

        Button btn = findViewById(R.id.colorPickerButton);
        Button btn2 = findViewById(R.id.thickPickerButton);
        Button btn3 = findViewById(R.id.eraseButton);
        Button btn4 = findViewById(R.id.saveButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.clear(1);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/path";
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +"";
//                String path = getExternalFilesDir(DIRECTORY_PICTURES) +"/path";

                // resize img size to 960*1600

                final LinearLayout capture = (LinearLayout) findViewById(R.id.container);//캡쳐할영역(프레임레이아웃)


                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                capture.buildDrawingCache();
                Bitmap captureview = capture.getDrawingCache();

                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                File file = getApplicationContext().getFilesDir();

                if (!file.exists()) {
                    file.mkdirs();
                    Toast.makeText(getApplicationContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();

                }

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(path + "/Capture" + day.format(date) + ".jpeg");
                    captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG")));
                    fos.flush();
                    fos.close();
                    capture.destroyDrawingCache();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //TODO: 위의 이미지를 storage에 저장

               //TODO
                writeNewTestData(getUserProfile(), "HTP", "img_name", (long) 20201112, "result:test2");

            }
        });
    }

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
                } else {
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
                }
            }
        }
    }

    @Nullable
    File getAppSpecificAlbumStorageDir(Context context, String albumName) {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        File file = new File(context.getExternalFilesDir(
                DIRECTORY_PICTURES), albumName);
        if (file == null || !file.mkdirs()) {
            Log.e("filefile", "Directory not created");
        }
        return file;
    }

    private void show() {
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("굵기 입력");
        builder.setView(editText);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        view.setStrokeWidth(Integer.parseInt(editText.getText().toString()));

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getApplicationContext(), "" + tColor, Toast.LENGTH_LONG).show();
                view.setColor(color);
            }
        });
        colorPicker.show();
    }

    //TODO
    //Firebase: update the HTP test data
    private void writeNewTestData(String UID, String type, String imgName, Long date, String result) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        TestData testData = new TestData(type, imgName, date, result);
        postValues = testData.toMap();
        childUpdates.put("/TestData/" + UID, postValues);
        mDatabase.updateChildren(childUpdates);


//        TestData testData = new TestData(type, imgName, date, result);

//        mDatabase.child("TestData").child(UID).setValue(testData);
    }

    //TODO: 아래 링크 사용해서 사용자 프로필 띄우는 기능 만들기 (메인 화면 햄버거 아이콘에...?)
    //Firebase: get user profile (https://firebase.google.com/docs/auth/android/manage-users?hl=ko#get_a_users_profile)
    private String getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (user != null) {
            uid = user.getUid();
        }
        else {
            Toast.makeText(getApplicationContext(), "로그인된 사용자가 아닙니다. 로그인 후 이용해주십시오.", Toast.LENGTH_SHORT).show();
        }
        return uid;
    }

}
