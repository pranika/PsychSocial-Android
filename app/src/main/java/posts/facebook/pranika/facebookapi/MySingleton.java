package posts.facebook.pranika.facebookapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nikhiljain on 9/3/17.
 */

public class MySingleton {

    private static MySingleton mInstance;
    private static Context ctx;
    RequestQueue requestQueue;
    MySingleton(Context mctx)
    {
        ctx=mctx;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue(){

        if(requestQueue==null)
        {
            requestQueue= Volley.newRequestQueue(ctx);


        }
        return requestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance=new MySingleton(context);

        }
        return mInstance;
    }

    public<T> void addToRequestQue(Request<T> request)
    {
        getRequestQueue().add(request);

    }
}
