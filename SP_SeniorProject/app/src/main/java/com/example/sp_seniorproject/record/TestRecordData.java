package com.example.sp_seniorproject.record;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

public class TestRecordData {
    String month;
    BarChart barchart;
    ArrayList NoOfEmp;
    ArrayList sensitive;

    public TestRecordData (String month, BarChart barchart, ArrayList NoOfEmp, ArrayList sensitive) {
        this.month = month;
        this.barchart = barchart;
        this.NoOfEmp = NoOfEmp;
        this.sensitive = sensitive;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BarChart getBarchart() {
        return barchart;
    }

    public void setBarchart(BarChart barchart) {
        this.barchart = barchart;
    }

    public ArrayList getNoOfEmp() {
        return NoOfEmp;
    }

    public void setNoOfEmp(ArrayList noOfEmp) {
        NoOfEmp = noOfEmp;
    }

    public ArrayList getSensitive() {
        return sensitive;
    }

    public void setSensitive(ArrayList sensitive) {
        this.sensitive = sensitive;
    }
}
