package com.example.sp_seniorproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart chart = findViewById(R.id.barchart);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(30f, 0));
        NoOfEmp.add(new BarEntry(60f, 1));
        NoOfEmp.add(new BarEntry(40f, 2));
        NoOfEmp.add(new BarEntry(10f, 3));
        NoOfEmp.add(new BarEntry(50f, 4));
        NoOfEmp.add(new BarEntry(80f, 5));
        NoOfEmp.add(new BarEntry(60f, 6));

        ArrayList sensitive = new ArrayList();
        sensitive.add("기쁨");
        sensitive.add("분노");
        sensitive.add("슬픔");
        sensitive.add("즐거움");
        sensitive.add("사랑");
        sensitive.add("증오");
        sensitive.add("욕망");
        BarDataSet bardataset = new BarDataSet(NoOfEmp, "감정의 종류");
        chart.animateY(100);
        BarData data = new BarData(sensitive, bardataset); // MPAndroidChart v3.X 오류 발생
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
}

