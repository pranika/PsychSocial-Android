package posts.facebook.pranika.facebookapi;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;





public class MyValueFormatter implements IValueFormatter
{

    private long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public MyValueFormatter(long referenceTimestamp) {
        this.referenceTimestamp = referenceTimestamp;
        this.mDataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        this.mDate = new Date();

    }




    private String getHour(long timestamp){
        try{
            mDate.setTime(timestamp);
            return mDataFormat.format(mDate).toString();
        }
        catch(Exception ex){
            return "xx";
        }
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        long convertedTimestamp = (long) value;

        // Retrieve original timestamp
        long originalTimestamp = (convertedTimestamp*1000*60*60*24 ) + referenceTimestamp;

        // Convert timestamp to hour:minute
        return getHour(originalTimestamp);
    }
}