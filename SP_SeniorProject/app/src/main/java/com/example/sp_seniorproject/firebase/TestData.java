package com.example.sp_seniorproject.firebase;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    public String type;         // Test type: HTP, PITR
    public String imgName;      // img name: /route/CaptureyyyyMMHHmmss.jpeg
    public Long date;         // The date of the test: yyyyMMHHmmss
    public String result;       // The result of the test

    public TestData () {
        // Default constructor required for calls to DataSnapshot.getValue(TestData.class)
    }

    public TestData(String type, String imgName, Long date, String result) {
        this.type = type;
        this.imgName = imgName;
        this.date = date;
        this.result = result;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("imgName", imgName);
        result.put("date", date);
        result.put("result", this.result);

        return result;
    }
}
