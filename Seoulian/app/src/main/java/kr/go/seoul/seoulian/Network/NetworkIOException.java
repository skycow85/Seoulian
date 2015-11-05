package kr.go.seoul.seoulian.Network;

import android.util.Log;

/**
 * Created by jkwoo on 2015-09-15.
 */
public class NetworkIOException extends RuntimeException {

    private static final long serialVersionUID = -6619476476407664516L;

    private int code = -1;

    public NetworkIOException(int code, String msg) {
        super(msg);
        this.code = code;
        Log.d("NetworkIOException", code+"/"+msg);
    }

    public int getCode(){
        return this.code;
    }
}
