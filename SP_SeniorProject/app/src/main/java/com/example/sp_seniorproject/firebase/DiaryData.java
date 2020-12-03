package com.example.sp_seniorproject.firebase;

import java.util.HashMap;
import java.util.Map;

public class DiaryData {
    public Long date;
    public String imgPath;          // image url (firenbase storage)
    public String contents;         // diary contents

    public DiaryData () {
        // Default constructor required for calls to DataSnapshot.getValue(DiaryData.class)
    }

    public DiaryData (Long date, String imgPath, String contents) {
        this.date = date;
        this.imgPath = imgPath;
        this.contents = contents;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("imgPath", imgPath);
        result.put("contents", contents);

        return result;
    }
}
