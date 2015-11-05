package kr.go.seoul.seoulian.Network;

import android.os.AsyncTask;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import kr.go.seoul.seoulian.Network.Form.XmlData;

/**
 * Created by jkwoo on 2015-10-05.
 */
public abstract class XmlParser {

    private Exception mException;
    private XmlHandler mXmlHandler;
    private String mUrl;
    private Parser mParser;
    public abstract void success(ArrayList<XmlData> data);
    public abstract void fail(Exception e);

    public XmlParser(String unitName, ArrayList<String> elementNames, String url){
        mXmlHandler = new XmlHandler(unitName, elementNames);
        mUrl = url;
        mParser = new Parser();
    }

    public void cancel(){
        if(mParser!=null && mParser.getStatus().equals(AsyncTask.Status.RUNNING) && !mParser.isCancelled()){
            mParser.cancel(true);
        }
    }

    public void startParsing(){
        if(mParser!=null && mParser.getStatus().equals(AsyncTask.Status.RUNNING) && !mParser.isCancelled()){
            mParser.cancel(true);
        }
        mParser.execute();
    }

    class Parser extends AsyncTask<Void, Void, ArrayList<XmlData>>{

        @Override
        protected ArrayList<XmlData> doInBackground(Void... params) {
            try {
                URLConnection conn = new URL(mUrl).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                SAXParserFactory saxPF = SAXParserFactory.newInstance();
                SAXParser saxP = saxPF.newSAXParser();
                XMLReader xmlR = saxP.getXMLReader();
                xmlR.setContentHandler(mXmlHandler);
                xmlR.parse(new InputSource(conn.getInputStream()));
            } catch (ParserConfigurationException e) {
                mException = new XmlParseException(XmlParseException.SAX_PARSING_EXCEPTION, "");
                return null;
            } catch (SAXException e) {
                mException = new XmlParseException(XmlParseException.SAX_PARSING_EXCEPTION, "");
                return null;
            } catch (MalformedURLException e) {
                mException = new XmlParseException(XmlParseException.URL_EXCEPTION, "");
                return null;
            } catch (IOException e) {
                mException = new XmlParseException(XmlParseException.IO_EXCEPTION, "");
                return null;
            } catch (XmlParseException e){
                mException = e;
                return null;
            }
            return mXmlHandler.getData();
        }

        @Override
        protected void onPostExecute(ArrayList<XmlData> data) {
            if(data==null){
                fail(mException);
            }else {
                success(data);
            }
        }
    }
}
