package com.example.sp_seniorproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static android.os.SystemClock.sleep;

import com.github.mikephil.charting.charts.BarChart;


public class HTPresult extends AppCompatActivity {
    private static final String TAG = "HTPresult";
    Button button01;
    TextView input01;
    ImageView iv;
    private View mLayout;
    private final int GET_GALLERY_IMAGE = 200;
    Socket sock;
    DataInputStream obj;
    InputStream in;
    Intent temp;
    Button re;
    ConnectThread thread;
    Button btnBarChart, btnPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htpresult);

        Button back;

        back = findViewById(R.id.htpback);
        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        btnBarChart = findViewById(R.id.btnBarChart);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(HTPresult.this, BarChartActivity.class);
                startActivity(I);
            }
        });
        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(HTPresult.this, PieChartActivity.class);
                startActivity(I);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent11);
            }
        });
        button01 = (Button) findViewById(R.id.button01);
        input01 = (TextView) findViewById(R.id.input01);
        iv = (ImageView) findViewById(R.id.iv);
        re = (Button) findViewById(R.id.reBtn);

        Button imageButton = (Button) findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

            }
        });

        Button sendImageButton = (Button) findViewById(R.id.sendBtn);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + temp.getData());
                FileSender fs = new FileSender(sock, temp.getData(), "img");
                fs.start();
            }
        });
        // Event of click button
        button01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Change the address text to String
                String addr = input01.getText().toString().trim();
                // Create the Thread to connect ip address
                thread = new ConnectThread(addr);
                // thread run
                thread.start();
            }
        });

        // Event of click button
        re.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Change the address text to String
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String addr = input01.getText().toString().trim();
                // Create the Thread to connect ip address
                ConnectThread thread = new ConnectThread(addr);
                // thread run

                thread.start();

                sleep(1000);
                // [read]
                Receiver receiver = new Receiver(sock);
                receiver.start();
            }
        });


        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {


                Snackbar.make(mLayout, "이 앱을 실행하려면 카메라와 외부 저장소 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        ActivityCompat.requestPermissions(HTPresult.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {

                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "URI=>" + data.getData());
        iv.setImageURI(data.getData());
        temp = data;
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

                FileSender fs = new FileSender(sock, temp.getData(), "img");
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
                Receiver receiver = new Receiver(sock);
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
                dos.writeUTF("House");
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
                // [read]
                dos.writeUTF("result");
                dos.flush();
                obj = new DataInputStream(in);
                Log.d("[Read]", obj.readUTF());
                Log.d("돼라", "??");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    dos.close();
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

