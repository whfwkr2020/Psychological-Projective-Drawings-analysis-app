package com.example.sp_seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sp_seniorproject.firebase.ScoreData;
import com.example.sp_seniorproject.firebase.TestData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class HTPresult extends AppCompatActivity {
    private static final String TAG = "HTPresult";
    TextView resultText;
    PieChart pieChart;

    //Firebase - user data
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String UID = currentUser.getUid();

    //firebase - realtime
    private FirebaseDatabase mDatabase;
    //    private DatabaseReference mReference;
//    private ChildEventListener mChild;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.htpresult);

        Button back;

        back = findViewById(R.id.htpback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent11 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent11);
            }
        });

        resultText = (TextView) findViewById(R.id.result);
        resultText.setText("결과를 불러오는 중입니다.");

        pieChart = findViewById(R.id.piechart);

        Intent intent = getIntent();
        String date1 = intent.getStringExtra("HTP1date");
        String date2 = intent.getStringExtra("HTP2date");
        String date3 = intent.getStringExtra("HTP3date");
        showResult(date1,date2,date3);

//        resultText.setText(date);
//        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();


    }

    private void showResult(final String dateStr1, final String dateStr2, final String dateStr3) {
        mDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = mDatabase.getReference(UID).child(dateStr);
//        DatabaseReference myRef = mDatabase.getReference(UID);
        final DatabaseReference[] myRef1 = {mDatabase.getReference("TestData").child(UID).child(dateStr1)};
        final DatabaseReference[] myRef2 = {mDatabase.getReference("TestData").child(UID).child(dateStr2)};
        final DatabaseReference[] myRef3 = {mDatabase.getReference("TestData").child(UID).child(dateStr3)};
        final String[] finalSentenceResult = {""};
        final String[] finalWordResult = {""};
        final int[] finalscoreResult = {0};
        // Add value event listener to the post

        ValueEventListener testDataListener = new ValueEventListener() {
            String temp1 = "";
            String temp2 = "";
            long temp3 = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                TestData testData = dataSnapshot.getValue(TestData.class);
                if (testData == null) {
//                    testData = dataSnapshot.getValue(TestData.class);
                    Log.d("TestdataNull", "null");
                    myRef1[0] = mDatabase.getReference("TestData").child(UID).child(dateStr1);
                    myRef2[0] = mDatabase.getReference("TestData").child(UID).child(dateStr2);
                    myRef3[0] = mDatabase.getReference("TestData").child(UID).child(dateStr3);
                } else {
                    Log.d("TestdataNull", "not Null");
                    Map<String, Object> result = testData.toMap();
                    temp1 = result.get("resultSentence").toString().replace("-", "\n");
//                    resultText.setText((CharSequence) result.get("resultSentence").toString().replace("-", "\n"));
//                    showPieChart(result.get("sentimentWord"));
                    temp2 = result.get("sentimentWord").toString();
                    temp3 = (long) result.get("score");
                    finalSentenceResult[0] +=temp1;
                    finalWordResult[0] +=temp2;
                    finalscoreResult[0] += temp3;
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(getApplicationContext(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        ValueEventListener chartListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                TestData testData = dataSnapshot.getValue(TestData.class);
                if (testData == null) {
//                    testData = dataSnapshot.getValue(TestData.class);
                    Log.d("TestdataNull", "null");
                    myRef1[0] = mDatabase.getReference("TestData").child(UID).child(dateStr1);
                    myRef2[0] = mDatabase.getReference("TestData").child(UID).child(dateStr2);
                    myRef3[0] = mDatabase.getReference("TestData").child(UID).child(dateStr3);
                } else {
                    Log.d("TestdataNull", "not Null");
                    Map<String, Object> result = testData.toMap();
                    String temp1=finalSentenceResult[0];
                    String temp2=finalWordResult[0];
                    storeScoreSum(finalscoreResult[0]);

                    resultText.setText(temp1);
                    showPieChart(temp2);

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(getApplicationContext(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        myRef1[0].addValueEventListener(testDataListener);
        myRef2[0].addValueEventListener(testDataListener);
        myRef3[0].addValueEventListener(testDataListener);
        myRef3[0].addValueEventListener(chartListener);


    }

    private void showPieChart(String word) {
        PieChart pieChart = findViewById(R.id.piechart);
        String words = word;
        Log.d("showPieChart", "words=" + words);
        String[] temp = words.split(" ");
        for (int i = 0; i < temp.length; i++) {
            Log.d("showPieChart", "temp[" + i + "]=" + temp[i]);
        }

        // Count the sentiment words
        HashMap<String, Integer> wordNum = new HashMap<>();
        for (int i = 0; i < temp.length; i++) {
            if (wordNum.containsKey(temp[i])) {
                int n = wordNum.get(temp[i]);
                wordNum.replace(temp[i], n + 1);
            } else
                wordNum.put(temp[i], 1);

        }

        ArrayList NoOfEmp = new ArrayList();
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "심리의 종류");

        ArrayList sensitive = new ArrayList();
//        for(int i = 0; i < temp.length; i++) {
//            sensitive.add(temp[i]);
//            NoOfEmp.add(new BarEntry(1, i));
//        }

        int count = 0;
        for (Map.Entry<String, Integer> entry : wordNum.entrySet()) {
            System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue());
            sensitive.add(entry.getKey());
            NoOfEmp.add(new BarEntry(entry.getValue(), count++));
        }

        PieData data = new PieData(sensitive, dataSet); // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextSize(13f);
        pieChart.animateXY(100, 100);
    }

    public void storeScoreSum (int sum) {
        //Firebase - realtime
        DatabaseReference mDB = FirebaseDatabase.getInstance().getReference();

        // upload result - realtime
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        Date date = new Date();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        ScoreData scoreData = new ScoreData(Long.parseLong(day.format(date)), (long)sum);
//        ScoreData scoreData = new ScoreData((long) 29, (long) 5);     // to make data
        postValues = scoreData.toMap();
        childUpdates.put("/ScoreData/" + UID + "/" + year.format(date) + "/" + month.format(date) + "/" + day.format(date), postValues);
//        childUpdates.put("/ScoreData/" + UID + "/" + year.format(date) + "/" + 11 + "/" + "29", postValues);      // to make data
        mDB.updateChildren(childUpdates);

    }


}
