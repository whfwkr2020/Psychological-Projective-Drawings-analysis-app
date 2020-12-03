package com.example.sp_seniorproject.record;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sp_seniorproject.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView month;
    LinearLayout linearLayout;
    LinearLayout barchartContainer;
    HorizontalScrollView horizontalScrollView;
    BarChart barchart;

    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        month = itemView.findViewById(R.id.month);
        linearLayout = itemView.findViewById(R.id.linearlayout);
        barchartContainer = itemView.findViewById(R.id.barchart_container);
        horizontalScrollView = itemView.findViewById(R.id.horizontal_scrollView);
        barchart = itemView.findViewById(R.id.barchart);

        // HorizontalScrollView
        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });
    }

    public void onBind(TestRecordData data, int position, SparseBooleanArray selectedItems){
        month.setText(data.getMonth());

        BarDataSet bardataset = new BarDataSet(data.getNoOfEmp(), "score");
        barchart.animateY(100);
        BarData bardata = new BarData(data.getSensitive(), bardataset); // MPAndroidChart v3.X 오류 발생
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barchart.setData(bardata);

        changeVisibility(selectedItems.get(position));
    }

    private void changeVisibility(final boolean isExpanded) {
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // barchart 높이 변경
                barchart.getLayoutParams().height = (int) animation.getAnimatedValue();
                barchart.requestLayout();
                // barchart의 실제로 사라지게하는 부분
                barchart.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            }
        });
        // Animation start
        va.start();
    }

    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }
}
