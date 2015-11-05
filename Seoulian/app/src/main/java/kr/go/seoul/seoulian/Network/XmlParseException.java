package kr.go.seoul.seoulian.Network;

/**
 * Created by jkwoo on 2015-10-05.
 */
public class XmlParseException extends RuntimeException {

    private int mCode;

    public static final int NO_SUCH_ELEMENT_NAME = -10000;
    public static final int OUT_BOUND_OF_INDEX_ELEMENT_NAME = -10001;
    public static final int SAX_PARSING_EXCEPTION = -20000;
    public static final int URL_EXCEPTION = -30000;
    public static final int IO_EXCEPTION = -40000;


    public XmlParseException(int code, String message){
        super(message);
        mCode = code;
    }

    public int getCode(){
        return mCode;
    }
}
