package tesis.hyc.com.appmifihc.Fragmentos;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import tesis.hyc.com.appmifihc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoAhorro extends Fragment {


    public FragmentoAhorro() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ahorro, container, false);

        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) view.findViewById(R.id.chart);
        chart.setDrawBorders(false);
        LineDataSet lineDataSet = new LineDataSet(getData(), "");
        lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        lineDataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        chart.setNoDataText("No hay datos");


        Legend l = chart.getLegend();
        l.setEnabled(false);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
        //////
//        lineDataSet.addColor(Color.GRAY); // Your Blue
        lineDataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        lineDataSet.setLineWidth(3f); // Increase here for line width
        lineDataSet.setCircleRadius(7f);
        lineDataSet.setValueTypeface(font);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setValueTextSize(13f);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(10f);

        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        lineDataSet.setFormSize(15.f);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_orange);
            lineDataSet.setFillDrawable(drawable);
        } else {
            lineDataSet.setFillColor(ContextCompat.getColor(getContext(), R.color.colorAccent)); // Change your colo
        }



        /////

        XAxis xAxis = chart.getXAxis();
//        xAxis.setEnabled(false);

        xAxis.setTypeface(font);
        xAxis.setTextSize(9f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months = new String[]{"22 Mar", "24 Mar", "25 Mar", "29 Mar", "24 Mar", "25 Mar", "29 Mar"};
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return months[(int) value];
            }
        };


        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);

        YAxis yAxisLeft = chart.getAxisLeft();
//        yAxisLeft.setGranularity(1f);
        yAxisLeft.setEnabled(false);
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);

        LineData data = new LineData(lineDataSet);
        chart.setData(data);
        chart.animateX(2500);
        chart.invalidate();

        return view;
    }

    private ArrayList getData(){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 1000f));
        entries.add(new Entry(1f, 900f));
        entries.add(new Entry(2f, 1500f));
        entries.add(new Entry(3f, 1200f));
        entries.add(new Entry(4f, 800f));
        entries.add(new Entry(5f, 850f));
        entries.add(new Entry(6f, 500f));
        return entries;
    }


}
