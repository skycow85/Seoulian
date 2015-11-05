package kr.go.seoul.seoulian.Http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import kr.go.seoul.seoulian.Network.Form.PostData;
import kr.go.seoul.seoulian.Network.NetworkManager;

/**
 * Created by jkwoo on 2015-09-15.
 */
public class HttpConnection {

    private String mMethod;
    private String mUrl;
    private Map<String, String> mHeader;
    private boolean mIsHTTPS;
    private Context mContext;
    private Loader mLoader;
    private PostData mBodyData;

    public interface ResultListener {
        void success(JSONObject jsonObject);
        void fail();
    }

    private ResultListener mResultListener;

    public void setResultListener(ResultListener l){
        mResultListener = l;
    }

    public HttpConnection(Context ctx, String method, String url, Map<String, String> header, PostData bodyData, boolean isHTTPS){
        this.mContext = ctx;
        this.mMethod = method;
        this.mUrl = url;
        this.mHeader = header;
        this.mBodyData = bodyData;
        this.mIsHTTPS = isHTTPS;
        this.mLoader = new Loader();
    }

    public HttpConnection(Context ctx, String url, PostData bodyData, boolean isHTTPS){
        this(ctx, HttpUtil.HTTP_METHOD_POST, url, null, bodyData, isHTTPS);
    }

    public HttpConnection(Context ctx, String url, boolean isHTTPS){
        this(ctx, HttpUtil.HTTP_METHOD_GET, url, null, null, isHTTPS);
    }

    public HttpConnection(Context ctx, String method, String url, boolean isHTTPS){
        this(ctx, method, url, null, null, isHTTPS);
    }

    public void startConnect(){
        if(mLoader!=null){
            mLoader.execute();
        }
    }

    public void cancelConnect(boolean mayInterruptIfRunning){
        if(mLoader!=null && mLoader.getStatus()!= AsyncTask.Status.FINISHED && !mLoader.isCancelled()){
            mLoader.cancel(mayInterruptIfRunning);
        }
    }

    private String connect(){
        Log.d("HttpConnection", "CONNECT : "+mUrl);
        if(!NetworkManager.IsNetworkEnable(mContext)){
            for(int i=0;i<10;i++){
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                if(NetworkManager.IsNetworkEnable(mContext)){
                    break;
                }
            }
        }

        if(!NetworkManager.IsNetworkEnable(mContext)){
            Log.d("HttpConnection","Network invailable");
            return null;
        }

        String body = null;

        if(mBodyData != null){
            body = mBodyData.toString();
        }

        HttpURLConnection connection=null;
        try{
            Log.d("HttpConnection","connect");
            connection = HttpUtil.connect(mMethod, mUrl, mHeader, body, false, mIsHTTPS);
        }catch (SSLHandshakeException e){
            Log.d("HttpConnection","SSLHandshakeException");
            return null;
        }catch (IOException e){
            Log.d("HttpConnection","IOException");
            return null;
        }

        int httpStatusCode;
        try{
            httpStatusCode = connection.getResponseCode();
            Log.d("HttpConnection","httpStatusCode: "+httpStatusCode);
            if(httpStatusCode != 200){
                return null;
            }
        }catch (IOException e){
            Log.d("HttpConnection","IOException");
            return null;
        }

        byte[] recvContent=null;
        try{
            Log.d("HttpConnection","readReceivedData");
            recvContent = readReceivedData(connection.getInputStream());
        }catch (IOException e){
            Log.d("HttpConnection","IOException");
            return null;
        }finally {
            connection.disconnect();
        }

        String recvContentStr = new String(recvContent);
        return recvContentStr;

    }

    private void fail(){
        if(mResultListener!=null){
            mResultListener.fail();
        }
    }

    private byte[] readReceivedData(InputStream is) throws IOException  {
        try {
            StringBuffer bf = new StringBuffer();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;

            while((line = rd.readLine()) != null) {
                bf.append(line);
                bf.append("\r\n");
            }
            try {
                rd.close();
            } catch (Exception ignored) {}
            return bf.toString().getBytes();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                is.close();
            } catch (Exception ignored) {}
        }
    }

    class Loader extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            return connect();
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("HttpConnection", "Result String : "+ s);
            JSONObject json;
            try{
                json = new JSONObject(s);
                if(mResultListener!=null){
                    Log.d("HttpConnection", "success");
                    mResultListener.success(json);
                }
            }catch (JSONException e){
                Log.d("HttpConnection", "JSONException");
                fail();
            }catch (NullPointerException e){
                Log.d("HttpConnection", "NullPointerException");
                fail();
            }
        }
    }

}
