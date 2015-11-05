package kr.go.seoul.seoulian.Network;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import kr.go.seoul.seoulian.Network.Form.XmlData;

/**
 * Created by jkwoo on 2015-10-05.
 */
public class XmlHandler extends DefaultHandler {
    private String mUnitName;
    private ArrayList<String> mElementNames;
    private boolean mElementOn = false;
    private ArrayList<XmlData> mDataList;
    private String mSingleValue;
    private XmlData mSingleData;

    public XmlHandler(String unitName, ArrayList<String> elementNames){
        mUnitName = unitName;
        mElementNames = elementNames;
        mDataList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        mElementOn = true;
        Log.e("TEST", "TAG:::"+localName);
        if(localName.equals(mUnitName)){
            mSingleData = new XmlData(mElementNames);
        }
    }
int itemindex = 0;
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        mElementOn = false;

        if(localName.equalsIgnoreCase(mUnitName)){
            mDataList.add(mSingleData);
            mSingleData = null;
        }else {
            for (String tag : mElementNames) {
                if (localName.equalsIgnoreCase(tag)) {

                    try {

                        if(mSingleData!=null)
                          mSingleData.setValue(tag, mSingleValue);
                    }catch (XmlParseException e){
                        throw e;
                    }
                }
            }
        }
        mSingleValue = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(mElementOn){
            mSingleValue += new String(ch, start, length);
            Log.e("TEST", "VALUE:::"+mSingleValue);
        }
    }

    public ArrayList<XmlData> getData(){
        return mDataList;
    }
}
