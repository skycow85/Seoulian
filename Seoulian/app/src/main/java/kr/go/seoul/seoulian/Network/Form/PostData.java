package kr.go.seoul.seoulian.Network.Form;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * POST 방식으로 BODY를 작성하기 위한 객체
 * Created by jkwoo on 2015-09-15.
 */
public class PostData {
    public ArrayList<String> paramList = new ArrayList<>();
    public ArrayList<String> valueList = new ArrayList<>();

    public void add(String param, String value){
        paramList.add(param);
        valueList.add(value);
    }

    public int size(){
        return paramList.size();
    }

    @Override
    public String toString(){
        if(paramList.isEmpty() || valueList.isEmpty() || (paramList.size() != valueList.size())){
            Log.d("PostData", "Params or Values Error");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            for(int i = 0 ; i < paramList.size() ; i++){
                if(i > 0) {
                    sb.append("&");
                }
                sb.append(paramList.get(i));
                sb.append("=");
                sb.append(URLEncoder.encode(valueList.get(i), "UTF-8"));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            String result = sb.toString();
            Log.d("PostData", "post data : "+result);
            return result;
        }

    }
}
