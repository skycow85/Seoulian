package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.go.seoul.seoulian.Http.HttpConnection;
import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.data.SpotInfo;
import kr.go.seoul.seoulian.data.StaticData;
import kr.go.seoul.seoulian.manager.MySQLiteHandler;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class PopupSpotDetail extends RelativeLayout implements View.OnClickListener{

    private final static String URL_GEO_BASE = "https://apis.daum.net/local/geo/addr2coord";

    private LinearLayout mLayoutInfo;
    private LinearLayout mLayoutUrl;
    private LinearLayout mLayoutPhone;
    private LinearLayout mLayoutFax;
    private LinearLayout mLayoutEmail;
    private LinearLayout mLayoutAddress;

    private ImageView mBtnLink;
    private ImageView mBtnCall;
    private ImageView mBtnMap;
    private ImageView mBtnStorage;

    private LinearLayout mBtnClose;
    private TextView mTitle;
    private TextView mTxtInfo;
    private TextView mTxtUrl;
    private TextView mTxtPhone;
    private TextView mTxtFax;
    private TextView mTxtEmail;
    private TextView mTxtAddr;

    private ScrollView mWrapperScroll;

    private MySQLiteHandler mDB;

    private String mAddress="";
    private boolean mIsSaved = false;

    private XmlData mXmlData;
    private SpotInfo mSpotInfo;

    public PopupSpotDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.popup_spot_detail, this, true);
        mBtnClose = (LinearLayout)findViewById(R.id.btn_close);
        mLayoutInfo = (LinearLayout)findViewById(R.id.layout_information);
        mLayoutUrl = (LinearLayout)findViewById(R.id.layout_url);
        mLayoutPhone = (LinearLayout)findViewById(R.id.layout_phone);
        mLayoutFax = (LinearLayout)findViewById(R.id.layout_fax);
        mLayoutEmail = (LinearLayout)findViewById(R.id.layout_email);
        mLayoutAddress = (LinearLayout)findViewById(R.id.layout_address);
        mBtnLink = (ImageView)findViewById(R.id.spot_btn_link);
        mBtnCall = (ImageView)findViewById(R.id.spot_btn_phone);
        mBtnMap = (ImageView)findViewById(R.id.spot_btn_address);
        mBtnStorage = (ImageView)findViewById(R.id.spot_btn_storage);
        mTitle = (TextView)findViewById(R.id.title);
        mTxtInfo = (TextView)findViewById(R.id.spot_text_information);
        mTxtUrl = (TextView)findViewById(R.id.spot_text_link);
        mTxtPhone = (TextView)findViewById(R.id.spot_text_phone);
        mTxtFax = (TextView)findViewById(R.id.spot_text_fax);
        mTxtEmail = (TextView)findViewById(R.id.spot_text_email);
        mTxtAddr = (TextView)findViewById(R.id.spot_text_address);
        mWrapperScroll = (ScrollView)findViewById(R.id.popup_scroll);
        mBtnCall.setOnClickListener(this);
        mBtnLink.setOnClickListener(this);
        mBtnMap.setOnClickListener(this);
        mBtnStorage.setOnClickListener(this);

        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopup();
            }
        });
    }


    public void showPopup(XmlData xmlData){
        mXmlData = xmlData;
        new DataBaseTask(getContext()).execute(DataBaseTask.SELECT);
        setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_appear_down_to_up);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                refreshVisibility();
                setData(mXmlData);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(ani);
    }

    public void showPopup(SpotInfo spotInfo){
        mSpotInfo = spotInfo;
        setStorageState(true);
        setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_appear_down_to_up);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                refreshVisibility();
                setData(mSpotInfo);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(ani);
    }

    public void hidePopup(){
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_disappear_up_to_down);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(View.GONE);
                refreshScroll();
                mXmlData = null;
                mSpotInfo = null;
                mWrapperScroll.fullScroll(FOCUS_UP);
                allTextClear();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(ani);
    }

    private void setData(SpotInfo spotInfo){
        String enName = spotInfo.getD_CORP_EN_NM();
        if(enName==null || enName.trim().isEmpty()){
            String krName = spotInfo.getD_CORP_KR_NM();
            if(krName==null || krName.trim().isEmpty()){
                mTitle.setText("No Name");
            }else{
                mTitle.setText(krName);
            }
        }else{
            mTitle.setText(enName);
        }
        String info = spotInfo.getINFO_CORP_EN_INTRO();
        if(info == null || info.trim().isEmpty()){
            mLayoutInfo.setVisibility(View.GONE);
        }else{
            mTxtInfo.setText(info);
        }

        String url = spotInfo.getURL();
        if(url == null || url.trim().isEmpty()){
            mLayoutUrl.setVisibility(View.GONE);
        }else{
            if(!url.startsWith("http")){
                url = "http://"+url;
            }
            mTxtUrl.setText(url);
        }

        String phone = spotInfo.getPHONE1();
        if(phone == null || phone.trim().isEmpty()){
            mLayoutPhone.setVisibility(View.GONE);
        }else{
            mTxtPhone.setText(phone);
        }

        String fax = spotInfo.getPHONE2();
        if(fax == null || fax.trim().isEmpty()){
            mLayoutFax.setVisibility(View.GONE);
        }else{
            mTxtFax.setText(fax);
        }

        String email = spotInfo.getEMAIL1();
        if(email == null || email.trim().isEmpty()){
            mLayoutEmail.setVisibility(View.GONE);
        }else{
            mTxtEmail.setText(email);
        }

        String addr = spotInfo.getADDR_EN();
        if(addr == null || addr.trim().isEmpty()){
            mLayoutAddress.setVisibility(View.GONE);
        }else{
            mTxtAddr.setText(addr);
        }

        mAddress = spotInfo.getADDR_KR();
    }

    private void setData(XmlData xmlData){
        String enName = xmlData.getValue("D_CORP_EN_NM");
        if(enName==null || enName.trim().isEmpty()){
            String krName = xmlData.getValue("D_CORP_KR_NM");
            if(krName==null || krName.trim().isEmpty()){
                mTitle.setText("No Name");
            }else{
                mTitle.setText(krName);
            }
        }else{
            mTitle.setText(enName);
        }
        String info = xmlData.getValue("INFO_CORP_EN_INTRO");
        if(info == null || info.trim().isEmpty()){
            mLayoutInfo.setVisibility(View.GONE);
        }else{
            mTxtInfo.setText(info);
        }

        String url = xmlData.getValue("URL");
        if(url == null || url.trim().isEmpty()){
            mLayoutUrl.setVisibility(View.GONE);
        }else{
            if(!url.startsWith("http")){
                url = "http://"+url;
            }
            mTxtUrl.setText(url);
        }

        String phone = xmlData.getValue("PHONE1");
        if(phone == null || phone.trim().isEmpty()){
            mLayoutPhone.setVisibility(View.GONE);
        }else{
            mTxtPhone.setText(phone);
        }

        String fax = xmlData.getValue("PHONE2");
        if(fax == null || fax.trim().isEmpty()){
            mLayoutFax.setVisibility(View.GONE);
        }else{
            mTxtFax.setText(fax);
        }

        String email = xmlData.getValue("EMAIL1");
        if(email == null || email.trim().isEmpty()){
            mLayoutEmail.setVisibility(View.GONE);
        }else{
            mTxtEmail.setText(email);
        }

        String addr = xmlData.getValue("ADDR_EN");
        if(addr == null || addr.trim().isEmpty()){
            mLayoutAddress.setVisibility(View.GONE);
        }else{
            mTxtAddr.setText(addr);
        }
        mAddress = xmlData.getValue("ADDR_KR");
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.spot_btn_phone:
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:"+mTxtPhone.getText()));
                getContext().startActivity(intentCall);
                break;
            case R.id.spot_btn_link:
                Intent intentBrowser = new Intent(Intent.ACTION_VIEW);
                intentBrowser.setData(Uri.parse(mTxtUrl.getText().toString()));
                getContext().startActivity(intentBrowser);
                break;
            case R.id.spot_btn_address:
                loadGeoMatrics();
                break;
            case R.id.spot_btn_storage:
                if(mIsSaved){
                    new DataBaseTask(getContext()).execute(DataBaseTask.DELETE);
                }else{
                    new DataBaseTask(getContext()).execute(DataBaseTask.INSERT);
                }
                setStorageState(!mIsSaved);
                if(mListener!=null){
                    mListener.onStorageClick();
                }
                break;
        }
    }

    private void refreshScroll(){
        ((HorizontalScrollView)findViewById(R.id.spot_link_scroll)).fullScroll(HorizontalScrollView.FOCUS_LEFT);
        ((HorizontalScrollView)findViewById(R.id.spot_phone_scroll)).fullScroll(HorizontalScrollView.FOCUS_LEFT);
        ((HorizontalScrollView)findViewById(R.id.spot_fax_scroll)).fullScroll(HorizontalScrollView.FOCUS_LEFT);
        ((HorizontalScrollView)findViewById(R.id.spot_email_scroll)).fullScroll(HorizontalScrollView.FOCUS_LEFT);
    }

    private void allTextClear(){
        mTitle.setText("");
        mTxtInfo.setText("");
        mTxtUrl.setText("");
        mTxtPhone.setText("");
        mTxtFax.setText("");
        mTxtEmail.setText("");
        mTxtAddr.setText("");
        setStorageState(false);
    }

    private void refreshVisibility(){
        mLayoutInfo.setVisibility(View.VISIBLE);
        mLayoutUrl.setVisibility(View.VISIBLE);
        mLayoutPhone.setVisibility(View.VISIBLE);
        mLayoutFax.setVisibility(View.VISIBLE);
        mLayoutEmail.setVisibility(View.VISIBLE);
        mLayoutAddress.setVisibility(View.VISIBLE);
    }

    private void loadGeoMatrics(){
        if(mAddress==null || mAddress.isEmpty()){
            return;
        }
        try {
            String url = URL_GEO_BASE+"?apikey="+ StaticData.API_KEY_DAUM +"&q=" + URLEncoder.encode(mAddress, "UTF-8")+"&output=json";
            HttpConnection hc = new HttpConnection(getContext(), url, true);
            hc.setResultListener(new HttpConnection.ResultListener() {
                @Override
                public void success(JSONObject jsonObject) {
                    try {
                        JSONObject result = jsonObject.getJSONObject("channel").getJSONArray("item").getJSONObject(0);
                        String lng = result.getString("lng");
                        String lat = result.getString("lat");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lat+","+lng+"?q="+lat+","+lng));
                        intent.setPackage("com.google.android.apps.maps");
                        getContext().startActivity(intent);
                    } catch (JSONException e) {
                        showToastErr();
                    }
                }

                @Override
                public void fail() {
                    showToastErr();
                }
            });
            hc.startConnect();
        } catch (UnsupportedEncodingException e) {
            showToastErr();
        }

    };

    private void showToastErr(){
        Toast.makeText(getContext(), getContext().getString(R.string.network_exception), Toast.LENGTH_SHORT).show();
    }

    private void setStorageState(boolean isSaved){
        mIsSaved = isSaved;
        if(isSaved){
            mBtnStorage.setImageResource(R.drawable.storage_on);
        }else{
            mBtnStorage.setImageResource(R.drawable.storage_off);
        }
    }

    private static OnSpotPopupActionListener mListener;

    public void setOnSpotPopupActionListener(OnSpotPopupActionListener l){
        mListener = l;
    }

    public interface OnSpotPopupActionListener{
        void onStorageClick();
    }

    class DataBaseTask extends AsyncTask<Integer, Void, Boolean>{

        public static final int SELECT = 0;
        public static final int INSERT = 1;
        public static final int DELETE = 2;
        private int mType;
        private Context mContext;
        public DataBaseTask(Context context){
            mContext = context;
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            mType = params[0];
            mDB = MySQLiteHandler.open(mContext);
            switch (mType){
                case SELECT:
                    try {
                        SpotInfo sl = mDB.select(mXmlData.getValue("CORP_SEQ"));
                        if(sl.getCORP_SEQ().equals(mXmlData.getValue("CORP_SEQ"))){
                            mDB.close();
                            return true;
                        }
                    }catch (Exception e){
                        mDB.close();
                        return false;
                    }
                    mDB.close();
                    return false;
                case INSERT:
                    if(mXmlData!=null) {
                        SpotInfo spotInfo = new SpotInfo(
                                mXmlData.getValue("CORP_SEQ"),
                                mXmlData.getValue("CATEGORY_CD"),
                                mXmlData.getValue("CATEGORY_NM_KR"),
                                mXmlData.getValue("CATEGORY_NM_EN"),
                                mXmlData.getValue("SECTION_CD"),
                                mXmlData.getValue("SECTION_NM_KR"),
                                mXmlData.getValue("SECTION_NM_EN"),
                                mXmlData.getValue("CONTENTS_CD"),
                                mXmlData.getValue("CONTENTS_NM_KR"),
                                mXmlData.getValue("CONTENTS_NM_EN"),
                                mXmlData.getValue("D_CORP_KR_NM"),
                                mXmlData.getValue("D_CORP_EN_NM"),
                                mXmlData.getValue("INFO_CORP_KR_INTRO"),
                                mXmlData.getValue("INFO_CORP_EN_INTRO"),
                                mXmlData.getValue("URL"),
                                mXmlData.getValue("PHONE1"),
                                mXmlData.getValue("PHONE2"),
                                mXmlData.getValue("URL1"),
                                mXmlData.getValue("ADDR_KR"),
                                mXmlData.getValue("ADDR_EN"),
                                mXmlData.getValue("TAG_KR"),
                                mXmlData.getValue("TAG_EN")
                        );
                        mDB.insert(spotInfo);
                    }else{
                        mDB.insert(mSpotInfo);
                    }
                    mDB.close();
                    return true;
                case DELETE:
                    if(mXmlData!=null) {
                        mDB.delete(mXmlData.getValue("CORP_SEQ"));
                    }else{
                        mDB.delete(mSpotInfo.getCORP_SEQ());
                    }
                    mDB.close();
                    return true;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            switch (mType){
                case SELECT:
                    setStorageState(aBoolean);
                    break;
                case INSERT:
                    Toast.makeText(mContext, mContext.getString(R.string.saved_storage), Toast.LENGTH_SHORT).show();
                    break;
                case DELETE:
                    Toast.makeText(mContext, mContext.getString(R.string.deleted_storage), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
