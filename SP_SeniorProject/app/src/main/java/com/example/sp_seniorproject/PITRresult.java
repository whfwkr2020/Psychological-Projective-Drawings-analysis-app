package com.example.sp_seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

public class PITRresult extends AppCompatActivity {
    Button btnBarChart, btnPieChart;

    public static Activity pitrresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitrresult);

        BarChart barChart = (BarChart) findViewById(R.id.barchart);
        btnBarChart = findViewById(R.id.btnBarChart);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnBarChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(PITRresult.this, BarChartActivity.class);
                startActivity(I);
            }
        });
        btnPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(PITRresult.this, PieChartActivity.class);
                startActivity(I);
            }
        });

        pitrresult = PITRresult.this;

        Button back;

        back = findViewById(R.id.pitrback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pitrresult.finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}
