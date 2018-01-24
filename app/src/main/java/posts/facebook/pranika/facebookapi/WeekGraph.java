package posts.facebook.pranika.facebookapi;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import posts.facebook.pranika.facebookapi.DaggerApp.DaggerApplication;

public class WeekGraph extends BaseActivity {
    String url="";
    private FirebaseAuth mauth;
    List<Map<String,?>> feedList;
    String patientid="",selfpatientid="";
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedList= new ArrayList<>();
        setContentView(R.layout.activity_week_graph);
        url="http://"+((DaggerApplication)this.getApplication()).getIpaddress()+"/Feed/showfeedsweek";


        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            patientid = extras.getString("patientid");
            selfpatientid = extras.getString("selfpatientid");
            mauth = FirebaseAuth.getInstance(myApp);
        }

        lineChart= (LineChart) findViewById(R.id.linechart);
        final ArrayList<Date> xAxisDate=new ArrayList<>();
        final ArrayList<Entry> yAxisCount=new ArrayList<>();



        //********************************************************************************



        JSONObject json = new JSONObject();
        try {


            if(patientid!=null){
                json.put("substitute_patient",patientid);
                // pref.edit().putString("substitute_patient",patientid);
            }
            if(selfpatientid!=null){
                json.put("doctor",mauth.getCurrentUser().getUid());
                json.put("selfpatientid",selfpatientid);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("response", e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {

                try {

                    String resbody = response.body().string();
                    final JSONObject obj = new JSONObject(resbody);

                    final JSONArray facebookdata = obj.getJSONArray("graph");
                    int n = facebookdata.length();
                    for (int i = 0; i < n; ++i) {

                        final JSONObject fbuser = facebookdata.getJSONObject(i);



                        HashMap feed1 = new HashMap();



                        feed1.put("date", fbuser.getString("date"));
                        feed1.put("count", fbuser.getString("count"));

                        feedList.add(feed1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //******************************************

                WeekGraph.this.runOnUiThread(new Runnable() {


                    @Override
                    public void run() {


                        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Long firstRef = null;

                        for (Map<String, ?> feeditem : feedList)
                        {


                            Date datevalue = null;
                            try {
                                datevalue = df1.parse((String) feeditem.get("date"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (firstRef == null) {
                                firstRef = datevalue.getTime();
                            }

                            int count =Integer.parseInt((String)feeditem.get("count"));

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
                        if(firstRef!=null){

                            IAxisValueFormatter xAxisFormatter = new DateAxisFormatter(firstRef);
                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setValueFormatter(xAxisFormatter);

                            // ld.setValueFormatter(new MyValueFormatter(firstRef));
                            lineChart.setData(ld);
                            //                           XAxis xAxis = lineChart.getXAxis();
//                            xAxis.
                            lineChart.invalidate();
                        }





                    }
                });


                //*************************************************



            }
        });


        //**********************************************************************************




    }
}
