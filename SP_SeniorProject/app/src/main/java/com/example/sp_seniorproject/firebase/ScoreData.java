package com.example.sp_seniorproject.firebase;

import java.util.HashMap;
import java.util.Map;

public class ScoreData {
    public Long date;
    public Long score;

    public ScoreData () {

    }

    public ScoreData(Long date, Long scoreSum) {
        this.date = date;
        this.score = scoreSum;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("score", score);

        return result;
    }
}
