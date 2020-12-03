package com.example.sp_seniorproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.os.Environment.DIRECTORY_PICTURES;

public class HTP2 extends AppCompatActivity {
    Socket sock;
    DataInputStream obj;
    InputStream in;
    Intent temp;
    Button re;
    Button back, submithtp;
    private View mLayout;
    MyPaintView view;
    int tColor, n = 0;
    Uri imageUri;
    HTP2.ConnectThread thread;
    String dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htp2_layout);

        back = findViewById(R.id.tohtp1);
        submithtp = findViewById(R.id.submithtp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), HTP.class);
                startActivity(intent1);
            }
        });

        submithtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [address] 주소
                String addr = "192.168.0.5".trim();
                // Create the Thread to connect ip address
                thread = new HTP2.ConnectThread(addr);
                // thread run
                thread.start();

                Intent intent = getIntent();
                String date = intent.getStringExtra("HTP1date");
                Intent intent1 = new Intent(v.getContext(), HTP3.class);
                intent1.putExtra("HTP1date", date);
                intent1.putExtra("HTP2date", dateFormat.substring(0, 8) + 2);
                startActivity(intent1);
            }
        });

        view = new MyPaintView(this);

        final LinearLayout container = findViewById(R.id.container);
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

        Button btn = findViewById(R.id.colorPickerButton);
        Button btn2 = findViewById(R.id.thickPickerButton);
        Button btn3 = findViewById(R.id.eraseButton);

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
        Button btn4 = findViewById(R.id.saveButton);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +"";
                final LinearLayout capture = (LinearLayout) findViewById(R.id.container);//캡쳐할영역(프레임레이아웃)


                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                dateFormat = day.format(date);
                capture.buildDrawingCache();
                Bitmap captureview = capture.getDrawingCache();

                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                File file = getApplicationContext().getFilesDir();

                if (!file.exists()) {
                    file.mkdirs();
                    Toast.makeText(getApplicationContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();

                }

                Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(path + "/Capture" + dateFormat + ".png");
                    captureview.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    imageUri = Uri.parse("file://" + path + "/Capture" + dateFormat + ".PNG");
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + dateFormat + ".PNG")));
                    fos.flush();
                    fos.close();
                    capture.destroyDrawingCache();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    // Create Thread class
    class ConnectThread extends Thread {
        String hostname;

        // initial class
        public ConnectThread(String addr) {
            // Insert the address to hostname
            hostname = addr;
        }

        // run function
        public void run() {
            try {
                // This port number is matched the server port number (7777)
                int port = 7777;
                Log.d("MainActivity", "서버에 연결중입니다.");

                // Create client socket to connect server that has applicable port
                sock = new Socket(hostname, port);
                // Get the data of server through InputStream instance
                in = sock.getInputStream();

                // DataInputStream can directly input/output Java's basic data type data received from inputStream
                obj = new DataInputStream(in);
                Log.d("MainActivity", "서버에서 받은 메시지 : " + obj.readUTF());

                HTP2.FileSender fs = new HTP2.FileSender(sock, imageUri, "img");
                fs.start();
                // terminal socket & stream
//                obj.close();
//                sock.close();
                sock = new Socket(hostname, port);
                // Get the data of server through InputStream instance
                in = sock.getInputStream();

                // DataInputStream can directly input/output Java's basic data type data received from inputStream
                obj = new DataInputStream(in);
                Log.d("MainActivity", "서버에서 받은 메시지 : " + obj.readUTF());
                HTP2.Receiver receiver = new HTP2.Receiver(sock);
                receiver.start();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    class FileSender extends Thread {
        Uri filePath;
        String fileNm;
        Socket socket;
        DataOutputStream dos;
        FileInputStream fis;
        BufferedInputStream bis;
        DataInputStream dis;

        public FileSender(Socket socket, Uri filePath, String fileNm) {
            this.socket = socket;
            this.fileNm = fileNm;
            this.filePath = filePath;

            try {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // @Override
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void run() {
            try {
                dos.writeUTF("Tree");
                dos.flush();
                String result = fileRead(dos);
                Log.d("[FileSender]", "run: " + result);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private String fileRead(DataOutputStream dos) {
            String result = null;

            try {
                String imagePath = getRealPathFromURI(filePath);
                dos.writeUTF(fileNm);
                File file = new File(imagePath);
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);

                int len;
                int size = 4096;
                byte[] data = new byte[size];
                while ((len = bis.read(data)) != -1) {
                    dos.write(data, 0, len);
                }

                dos.flush();
                result = "Success";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                result = "Error";
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }


    }
    // [read]
    class Receiver extends Thread {
        Socket socket;
        FileInputStream fis;
        BufferedInputStream bis;
        DataInputStream dis;
        DataOutputStream dos;

        public Receiver(Socket socket) {
            this.socket = socket;
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // @Override
        public void run() {
            try {
                Log.d("isRun", "runrunrunrun");
                // [read]
                dos.writeUTF("result");
                dos.flush();
                obj = new DataInputStream(in);
//                Log.d("[Read]", obj.readUTF());
                Log.d("돼라", "??");

                String result = new String(obj.readUTF());
//                Log.d("newUTF", result);        //test
                storeResult(result);
//                Log.d("ddd", "success");        //test

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                    dos.close();
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void storeResult(String resultStr) {
            Log.d("read1234", resultStr);      //test

            //Firebase - user data
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String UID = currentUser.getUid();

            //Firebase - storage
//            FirebaseStorage mFirestorage = FirebaseStorage.getInstance();
//            final StorageReference storageRef = mFirestorage.getReference("test/HTP/" + UID + "/" + imageUri.getLastPathSegment());

            //Firebase - realtime
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

//            final String[] downloadUrl = {""};

            // upload image - storage
//            UploadTask uploadTask = storageRef.putFile(imageUri);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getApplicationContext(), "이미지 업로드 실패", Toast.LENGTH_SHORT).show();
//                    Log.d("uploadImg", "fail");
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getApplicationContext(), "이미지 업로드 성공", Toast.LENGTH_SHORT).show();
////                    downloadUrl[0] = storageRef.getDownloadUrl().toString();
////                    Log.d("downloadUrl", downloadUrl[0]);
//                }
//            });

            // upload result - realtime
//            SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
//            Date date = new Date();
            Map<String, Object> childUpdates = new HashMap<>();
            Map<String, Object> postValues = null;

            String type = "HTPtree";
//            String[] temp = resultStr.split("\\|");
            String[] temp = resultStr.split("/");
            Long score = Long.parseLong(temp[0]);
            String sentimentWord = temp[1];
            String resultSentence = temp[2];
            TestData testData = new TestData(type, Long.parseLong(dateFormat.substring(0, 8) + 2), resultSentence, score, sentimentWord);
            postValues = testData.toMap();
            childUpdates.put("/TestData/" + UID + "/" + dateFormat.substring(0, 8) + 2, postValues);
//            childUpdates.put("/TestData/" + UID, postValues);
            mDatabase.updateChildren(childUpdates);

//            Toast.makeText(getApplicationContext(), "결과 업로드 완료", Toast.LENGTH_SHORT).show();
        }

    }
}
