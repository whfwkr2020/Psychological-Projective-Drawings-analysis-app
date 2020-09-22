package com.example.team2020;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import yuku.ambilwarna.AmbilWarnaDialog;

public class Journal extends AppCompatActivity {

    Button menu, sub;
    EditText jour;

    MyPaintView view;
    int tColor, n = 0;

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
}
