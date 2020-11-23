package com.example.sp_seniorproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        PieChart pieChart = findViewById(R.id.piechart);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(30f, 0));
        NoOfEmp.add(new BarEntry(60f, 1));
        NoOfEmp.add(new BarEntry(40f, 2));
        NoOfEmp.add(new BarEntry(10f, 3));
        NoOfEmp.add(new BarEntry(50f, 4));
        NoOfEmp.add(new BarEntry(80f, 5));
        NoOfEmp.add(new BarEntry(60f, 6));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "감정의 종류");

        ArrayList sensitive = new ArrayList();
        sensitive.add("기쁨");
        sensitive.add("분노");
        sensitive.add("슬픔");
        sensitive.add("즐거움");
        sensitive.add("사랑");
        sensitive.add("증오");
        sensitive.add("욕망");
        PieData data = new PieData(sensitive, dataSet); // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(100, 100);
    }
}
