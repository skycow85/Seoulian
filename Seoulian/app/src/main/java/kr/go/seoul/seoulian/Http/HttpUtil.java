package kr.go.seoul.seoulian.Http;

import android.os.Build;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * Created by jkwoo on 2015-09-15.
 */
public class HttpUtil {

    private final static int TIMEOUT_CONNECTION = 10000;
    private final static int TIMEOUT_SOCKET = 60000;
    public final static String HTTP_METHOD_POST = "POST";
    public final static String HTTP_METHOD_GET = "GET";


    public static HttpURLConnection connect(String method, String reqUrl, Map<String, String> header, String body, boolean isRetry, boolean isHTTPS) throws IOException{
        HttpURLConnection connection = getClient(reqUrl, isHTTPS);
        if(method!=null){
            switch (method){
                case HTTP_METHOD_POST:
                    connection.setRequestMethod(method);
                    connection.setDoOutput(true);
                    break;
                case HTTP_METHOD_GET:
                    connection.setRequestMethod(method);
                    connection.setDoOutput(false);
                    break;
            }
        }

        connection.setRequestProperty("Accept-Encoding", "identity");

        if(header!=null){
            Object[] keyArr = header.keySet().toArray();
            for(int i=0;i<keyArr.length;i++){
                connection.addRequestProperty(keyArr[i].toString(), header.get(keyArr[i]));
            }
        }
        connection.addRequestProperty("accept", "application/json");

        if (body != null && !body.isEmpty() && method==HTTP_METHOD_POST) {
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream wr = new DataOutputStream(outputStream);
            wr.writeBytes(body);
            wr.flush();
            wr.close();
        }

        connection.connect();
        int responseCode = 0;
        try{
            responseCode = connection.getResponseCode();
            if(responseCode == 400 || responseCode == 404 || responseCode == -1){
                connection.disconnect();
                if(!isRetry){
                    sleepThread(200);
                    return connect(method, reqUrl, header, body, true, isHTTPS);
                }
            }else if(responseCode == 302){
                String url = connection.getHeaderField("location");
                if(url != null) {
                    return connect(method, url, null, body, true, isHTTPS);
                }
            }
        }catch (IOException e){
            if(!isRetry){
                sleepThread(200);
                return connect(method, reqUrl, header, body, true, isHTTPS);
            }else{
                throw e;
            }
        }
        return connection;
    }

    private static void sleepThread(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e1) {
        }
    }

    public static HttpURLConnection getClient(String urlStr, boolean isSSL)throws IOException{
        URL url = null;
        HttpURLConnection conn = null;
        HttpsURLConnection connSSL = null;

        try{
            url = getURL(urlStr);

            if(isSSL){
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, new TrustManager[]{new EzTrustManager(null)}, null);

                connSSL = (HttpsURLConnection) url.openConnection();
                connSSL.setSSLSocketFactory(context.getSocketFactory());
                conn = connSSL;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
        } catch (KeyManagementException e){
            throw new IOException(e);
        } catch (NoSuchAlgorithmException e){

        } catch (KeyStoreException e){

        }

        if(conn != null){
            conn.setConnectTimeout(TIMEOUT_CONNECTION);
            conn.setReadTimeout(TIMEOUT_SOCKET);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);

            if(Build.VERSION.SDK_INT > 20){
                CookieManager cookieManager = new CookieManager();
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
                CookieHandler.setDefault(cookieManager);
            }
        }
        return conn;
    }

    private static URL getURL(String urlStr) throws MalformedURLException {
        URL url = null;

        if (urlStr.lastIndexOf(":", 0) < 5) { // port번호 없이 오는 URL
            url = new URL(urlStr);
        } else {
            String split[] = urlStr.split("[:][/][/]|/|:");
            String file = "";
            for (int i = 3; i < split.length; i++) {
                file += "/";
                file += split[i];
            }
            url = new URL(split[0], split[1], Integer.valueOf(split[2]), file);
        }
        return url;
    }
}
