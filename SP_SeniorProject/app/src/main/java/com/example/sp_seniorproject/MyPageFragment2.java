package com.example.sp_seniorproject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp_seniorproject.firebase.ScoreData;
import com.example.sp_seniorproject.firebase.TestData;
import com.example.sp_seniorproject.record.RecyclerViewAdapter;
import com.example.sp_seniorproject.record.TestRecordData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.internal.Objects;
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

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "MyPageFragment2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Adapter
    public RecyclerViewAdapter adapter;


    //Firebase - user data
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String UID = currentUser.getUid();

    //firebase - realtime
    public FirebaseDatabase mDatabase;

    public MyPageFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment2 newInstance(String param1, String param2) {
        MyPageFragment2 fragment = new MyPageFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_page2, container, false);

        // RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        showResult();

        return rootView;
    }


    private void showResult() {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("ScoreData").child(UID);

        BarChart chart = new BarChart(getContext());
        final ArrayList date = new ArrayList();
        ArrayList scores = new ArrayList();

        // Create child event listener
        // [START child_event_listener_recycler]
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//                Toast.makeText(getContext(), "1:" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();      //2020

                for (DataSnapshot childYear : dataSnapshot.getChildren()) {     // year
                    Log.d(TAG, "onChildAdded:" + childYear.getKey());       //11
//                    Toast.makeText(getContext(), "2:" + childYear.getKey(), Toast.LENGTH_SHORT).show();

                    BarChart chart = new BarChart(getContext());
                    ArrayList scorelist = new ArrayList();
                    ArrayList datelist = new ArrayList();
                    int index = 0;

                    for (DataSnapshot childMonth : childYear.getChildren()) {     // month
                        Log.d(TAG, "onChildAdded:" + childMonth.getKey());      //05
//                        Toast.makeText(getContext(), "3:" + childMonth.getKey(), Toast.LENGTH_SHORT).show();



//                        for (DataSnapshot childDay : childMonth.getChildren()) {     // day
//                            Log.d(TAG, "onChildAdded:" + childDay.getKey());
                            ScoreData scoreData = childMonth.getValue(ScoreData.class);
                            Map<String, Object> data = scoreData.toMap();
//                            Toast.makeText(getContext(), "4:" + data.get("date") + ", " + data.get("score"), Toast.LENGTH_SHORT).show();


//                            BarChart chart = new BarChart(getContext());
//                            ArrayList scorelist = new ArrayList();
                        BarEntry barEntry = new BarEntry(Float.parseFloat(data.get("score").toString()), index++);
                            scorelist.add(barEntry);

//                            ArrayList datelist = new ArrayList();
                            datelist.add(data.get("date").toString());
//                            TestRecordData testRecordData = new TestRecordData(childYear.getKey(), chart, NoOfEmp, sensitive);
//                            adapter.addItem(testRecordData);

//                        }
                    }

//                    TestRecordData testRecordData = new TestRecordData(childYear.getKey(), chart, scorelist, datelist);
//                    adapter.addItem(testRecordData);
                    adapter.addItem(new TestRecordData(dataSnapshot.getKey() + "년 "+ childYear.getKey() + "월의 기록", chart, scorelist, datelist));
                    adapter.notifyDataSetChanged();

                }


//                // A new comment has been added, add it to the displayed list
//                Comment comment = dataSnapshot.getValue(Comment.class);
//
//                // [START_EXCLUDE]
//                // Update RecyclerView
//                mCommentIds.add(dataSnapshot.getKey());
//                mComments.add(comment);
//                notifyItemInserted(mComments.size() - 1);
//                // [END_EXCLUDE]
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                Comment newComment = dataSnapshot.getValue(Comment.class);
//                String commentKey = dataSnapshot.getKey();
//
//                // [START_EXCLUDE]
//                int commentIndex = mCommentIds.indexOf(commentKey);
//                if (commentIndex > -1) {
//                    // Replace with the new data
//                    mComments.set(commentIndex, newComment);
//
//                    // Update the RecyclerView
//                    notifyItemChanged(commentIndex);
//                } else {
//                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
//                }
//                // [END_EXCLUDE]
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                String commentKey = dataSnapshot.getKey();
//
//                // [START_EXCLUDE]
//                int commentIndex = mCommentIds.indexOf(commentKey);
//                if (commentIndex > -1) {
//                    // Remove data from the list
//                    mCommentIds.remove(commentIndex);
//                    mComments.remove(commentIndex);
//
//                    // Update the RecyclerView
//                    notifyItemRemoved(commentIndex);
//                } else {
//                    Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
//                }
//                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                Comment movedComment = dataSnapshot.getValue(Comment.class);
//                String commentKey = dataSnapshot.getKey();
//
//                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//                Toast.makeText(mContext, "Failed to load comments.",
//                        Toast.LENGTH_SHORT).show();
            }
        };
        ref.addChildEventListener(childEventListener);
        // [END child_event_listener_recycler]

    }

}
