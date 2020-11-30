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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HTPresult extends AppCompatActivity {
    private static final String TAG = "HTPresult";
    TextView input01;
    TextView resultText;

    //Firebase - user data
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String UID = currentUser.getUid();

    //firebase - realtime
    private FirebaseDatabase mDatabase;
//    private DatabaseReference mReference;
//    private ChildEventListener mChild;

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

        Intent intent = getIntent();
        String date = intent.getStringExtra("dateString");
        showResult(date);

//        resultText.setText(date);
//        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();





    }

    private void showResult(final String dateStr) {
        mDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = mDatabase.getReference(UID).child(dateStr);
//        DatabaseReference myRef = mDatabase.getReference(UID);
        final DatabaseReference[] myRef = {mDatabase.getReference("TestData").child(UID).child(dateStr)};
        Log.d("TestdataNull", "dateStr=" + dateStr);

        // Add value event listener to the post
        ValueEventListener testDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                TestData testData = dataSnapshot.getValue(TestData.class);
                if (testData == null){
//                    testData = dataSnapshot.getValue(TestData.class);
                    Log.d("TestdataNull", "null");
                    myRef[0] = mDatabase.getReference("TestData").child(UID).child(dateStr);
                }
                else {
                    Log.d("TestdataNull", "not Null");
                    Map<String, Object> result = testData.toMap();
                    resultText.setText((CharSequence) result.get("resultSentence").toString().replace("-", "\n"));
                    showPieChart(result.get("sentimentWord"));
                }


                // [START_EXCLUDE]
//                binding.postAuthorLayout.postAuthor.setText(post.author);
//                binding.postTextLayout.postTitle.setText(post.title);
//                binding.postTextLayout.postBody.setText(post.body);
//                Map<String, Object> result = testData.toMap();
//                resultText.setText((CharSequence) result.get("resultSentence"));
                // [END_EXCLUDE]
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
        myRef[0].addValueEventListener(testDataListener);
    }

    private void showPieChart(Object word) {
        PieChart pieChart = findViewById(R.id.piechart);
        String words = word.toString();
        Log.d("showPieChart", "words=" + words);
        String[] temp = words.split(" ");
        for(int i = 0; i < temp.length; i++) {
            Log.d("showPieChart", "temp[" + i + "]=" + temp[i]);
        }

        // Count the sentiment words
        HashMap<String, Integer> wordNum = new HashMap<>();
        for (int i = 0; i < temp.length; i++) {
            if (wordNum.containsKey(temp[i])) {
                int n = wordNum.get(temp[i]);
                wordNum.replace(temp[i], n+1);
            }
            else
                wordNum.put(temp[i], 1);

        }

        ArrayList NoOfEmp = new ArrayList();
//        NoOfEmp.add(new BarEntry(30f, 0));
//        NoOfEmp.add(new BarEntry(60f, 1));
//        NoOfEmp.add(new BarEntry(40f, 2));
//        NoOfEmp.add(new BarEntry(10f, 3));
//        NoOfEmp.add(new BarEntry(50f, 4));
//        NoOfEmp.add(new BarEntry(80f, 5));
//        NoOfEmp.add(new BarEntry(60f, 6));
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

//    private void initDatabase() {
//
//        mDatabase = FirebaseDatabase.getInstance();
//
//        mReference = mDatabase.getReference("log");
//        mReference.child("log").setValue("check");
//
//        mChild = new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                dataSnapshot.getKey(); // will have value of String: "users", then "books"
////                for (DataSnapshot snapshot : dataSnapshot) {
////                    snapshot.getKey();
////                    // if dataSnapshot.getKey() is "users", this will have value of String: "randomUserId1", then "randomUserId2"
////                    // If dataSnapshot.getKey() is "books", this will have value of String: "bookId1", then "bookId2"
////                    for (DataSnapshot deeperSnapshot : dataSnapshot) {
////                        snapshot.getKey();
////                        // if snapshot.getKey() is "randomUserId1" or "randomUserId1", this will have value of String: "display-name", then "gender"
////                        // But the value will be different based on key
////                        // If snapshot.getKey() is "books", this will have value of String: "title", but the value will be different based on key
////                    }
////                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mReference.addChildEventListener(mChild);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mReference.removeEventListener(mChild);
//    }


}

