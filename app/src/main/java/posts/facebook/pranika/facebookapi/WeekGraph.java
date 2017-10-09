package posts.facebook.pranika.facebookapi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class WeekGraph extends AppCompatActivity {
    String url="http://192.168.1.10:3000/showfeedsweek";
    List<Map<String,?>> feedList;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedList= new ArrayList<>();
        setContentView(R.layout.activity_week_graph);
        lineChart= (LineChart) findViewById(R.id.linechart);
        final ArrayList<Date> xAxisDate=new ArrayList<>();
        final ArrayList<Entry> yAxisCount=new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("feed",response);
                        Log.d("feed",response);
                        try {
                            final JSONObject obj = new JSONObject(response);

                            final JSONArray facebookdata = obj.getJSONArray("graph");
                            int n = facebookdata.length();
                            for (int i = 0; i < n; ++i) {

                                final JSONObject fbuser = facebookdata.getJSONObject(i);

                                Log.d("feeddata", fbuser.toString());

                                HashMap feed1 = new HashMap();

                                feed1.put("date", fbuser.getString("date"));
                                feed1.put("count", fbuser.getString("count"));


                                feedList.add(feed1);

                            }

                            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            Long firstRef = null;

                            for (Map<String, ?> feeditem : feedList)
                            {


                                Date datevalue = df1.parse((String) feeditem.get("date"));

                                if (firstRef == null) {
                                    firstRef = datevalue.getTime();
                                }

                                int count =Integer.parseInt((String)feeditem.get("count"));
                         //       xAxisDate.add(datevalue);
                                System.out.println( "datevzlue"+datevalue.getTime());
                                yAxisCount.add(new Entry((datevalue.getTime() - firstRef)/1000, count));




                            }

                            ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
                            LineDataSet lineDataSet=new LineDataSet(yAxisCount, "Count");
                            lineDataSet.setColor(Color.RED);
                            lineDataSet.setCircleColor(Color.GREEN);

                            lineDataSet.setDrawCircles(true);
                            lineDataSets.add(lineDataSet);


                            LineData ld = new LineData(lineDataSet);

                            IAxisValueFormatter xAxisFormatter = new DateAxisFormatter(firstRef);
                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setValueFormatter(xAxisFormatter);

                           // ld.setValueFormatter(new MyValueFormatter(firstRef));
                            lineChart.setData(ld);
                            //                           XAxis xAxis = lineChart.getXAxis();
//                            xAxis.
                           lineChart.invalidate();






                            }
                        catch (Exception e)
                        {
                            Log.d("exception",e.toString());
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
                error.printStackTrace();


            }
        });
        MySingleton.getmInstance(WeekGraph.this).addToRequestQue(stringRequest);


        //  lineChart.setVisibleXRangeMaximum(65f);




    }
}
