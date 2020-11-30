package com.example.sp_seniorproject.firebase;

import java.util.HashMap;
import java.util.Map;

public class TestData {
    public String type;         // Test type: HTP, PITR
//    public String imgPath;      // img name: /route/CaptureyyyyMMHHmmss.jpeg
    public Long date;         // The date of the test: yyyyMMHHmmss
    public String resultSentence;       // The result of the test
    public Long score;              // The score of the test
    public String sentimentWord;    // word indicating emotional state

    public TestData () {
        // Default constructor required for calls to DataSnapshot.getValue(TestData.class)
    }

    public TestData(String type, Long date, String resultSentence, Long score, String sentimentWord) {
        this.type = type;
//        this.imgPath = imgPath;
        this.date = date;
        this.resultSentence = resultSentence;
        this.score = score;
        this.sentimentWord = sentimentWord;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", type);
//        result.put("imgPath", imgPath);
        result.put("date", date);
        result.put("resultSentence", resultSentence);
        result.put("score", score);
        result.put("sentimentWord", sentimentWord);

        return result;
    }
}
