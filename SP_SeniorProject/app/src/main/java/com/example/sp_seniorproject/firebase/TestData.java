package com.example.sp_seniorproject.firebase;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    public String type;         // Test type: HTP, PITR
    public String imgPath;      // img name: /route/CaptureyyyyMMHHmmss.jpeg
    public Long date;         // The date of the test: yyyyMMHHmmss
    public String result;       // The result of the test

    public TestData () {
        // Default constructor required for calls to DataSnapshot.getValue(TestData.class)
    }

    public TestData(String type, String imgPath, Long date, String result) {
        this.type = type;
        this.imgPath = imgPath;
        this.date = date;
        this.result = result;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("imgPath", imgPath);
        result.put("date", date);
        result.put("result", this.result);

        return result;
    }
}
