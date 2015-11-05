package kr.go.seoul.seoulian.Network.Form;

import java.util.ArrayList;

/**
 * Created by jkwoo on 2015-10-05.
 */
public class XmlData {

    private ArrayList<String> mElementNames;
    private String[] mValues;

    public XmlData(ArrayList<String> elementNames){
        this.mElementNames = elementNames;
        mValues = new String[mElementNames.size()];
    }

    public void setValue(String elementName, String value){

        int index = mElementNames.indexOf(elementName);
        if(index != -1) {
            mValues[index]=value;
        }
    }

    public String getValue(String elementName){
        int index = mElementNames.indexOf(elementName);
        if(index != -1){
            return mValues[index];
        }else{
            return "";
        }
    }

    public String getValue(int index){
        if(index < mElementNames.size()){
            return getValue(mElementNames.get(index));
        }else{
            return "";
        }

    }
}
