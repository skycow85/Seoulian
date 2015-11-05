package kr.go.seoul.seoulian.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.IndicatorMenuItem;
import kr.go.seoul.seoulian.manager.SeoulianPreferenceManager;

/**
 * Created by Administrator on 2015-10-25.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    private final static String TIMEZONE_DEFAULT = "Etc/GMT";
    private final static String TIMEZONE_SEOUL = "Asia/Seoul";

    private final static String[] ARRAY_IMAGE_SLIDE_NAME = {"Gwanghwamun Gate", "Dongdaemun Gate Design plaza", "Myeong-dong",
    "Gyeongbokgung Palace", "Gwanghwamun Gate", "Myeongdong Catholic Cathedral", "Insa-dong"};
    private final static int[] ARRAY_IMAGE_SLIDE_IMG = {R.drawable.slide_gwanghwamun1, R.drawable.slide_dongdaemun, R.drawable.slide_myeongdong,
    R.drawable.silde_gyungbok, R.drawable.slide_gwanghwamun2, R.drawable.slide_myungdongchurch, R.drawable.slide_insadong};
    private final static String[] ARRAY_ADDR_LINK = {"http://www.visitseoul.net/en/index.do",
            "http://stay.visitseoul.net/eng/index.html",
            "http://english.visitkorea.or.kr/enu/index.kto"};

    private SliderLayout mDemoSlider;
    private LinearLayout mBtnFavorite;
    private LinearLayout mBtnSeoulLife;
    private LinearLayout mBtnAttraction;
    private LinearLayout mBtnConvenience;
    private LinearLayout mBtnCurrency;
    private LinearLayout mBtnSpeak;

    private LinearLayout mBtnLink1;
    private LinearLayout mBtnLink2;
    private LinearLayout mBtnLink3;

    private LinearLayout mRightTimeZone;
    private TextView mLeftTime;
    private TextView mRightTimeText;
    private TextView mRightTime;
    private String mTimeZone;

    private HashMap<String,Integer> mFileMaps = new HashMap<String, Integer>();

    public interface OnMainActionListener {
        void onMenuClick(int position);
        void onTimeZoneClick();
        void hideLoadingTime();
        void showLoadingTime();
    }

    private static OnMainActionListener mOnMainClickListner;

    public void setOnMainActionListener(OnMainActionListener l){
        mOnMainClickListner = l;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
        mBtnFavorite = (LinearLayout)view.findViewById(R.id.main_btn_favorite);
        mBtnSeoulLife = (LinearLayout)view.findViewById(R.id.main_btn_seoullife);
        mBtnAttraction = (LinearLayout)view.findViewById(R.id.main_btn_attraction);
        mBtnConvenience = (LinearLayout)view.findViewById(R.id.main_btn_convenience);
        mBtnCurrency = (LinearLayout)view.findViewById(R.id.main_btn_economic);
        mBtnSpeak = (LinearLayout)view.findViewById(R.id.main_btn_speak);
        mBtnLink1 = (LinearLayout)view.findViewById(R.id.link_visitseoul);
        mBtnLink2 = (LinearLayout)view.findViewById(R.id.link_seoulstay);
        mBtnLink3 = (LinearLayout)view.findViewById(R.id.link_visitkorea);
        mRightTimeZone = (LinearLayout)view.findViewById(R.id.main_time_zone);
        mLeftTime = (TextView)view.findViewById(R.id.main_time_left_time);
        mRightTimeText = (TextView)view.findViewById(R.id.main_time_right_city);
        mRightTime = (TextView)view.findViewById(R.id.main_time_right_time);
        startClock();
        mBtnFavorite.setOnClickListener(this);
        mBtnSeoulLife.setOnClickListener(this);
        mBtnAttraction.setOnClickListener(this);
        mBtnConvenience.setOnClickListener(this);
        mBtnCurrency.setOnClickListener(this);
        mBtnSpeak.setOnClickListener(this);
        mBtnLink1.setOnClickListener(this);
        mBtnLink2.setOnClickListener(this);
        mBtnLink3.setOnClickListener(this);
        setTimeZone(SeoulianPreferenceManager.getString(SeoulianPreferenceManager.NAME_TIME_ZONE, TIMEZONE_DEFAULT));
        initSlider();
        mRightTimeZone.setOnClickListener(timeZoneClick);
        return view;
    }
    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onResume() {
        mDemoSlider.startAutoCycle();
        super.onResume();
    }

    private void initSlider(){
        for(int i=0; i<ARRAY_IMAGE_SLIDE_IMG.length;i++){
            mFileMaps.put(ARRAY_IMAGE_SLIDE_NAME[i], ARRAY_IMAGE_SLIDE_IMG[i]);
        }

        for(String name : mFileMaps.keySet()){
            new InitSliderTask().execute(name);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn_favorite:
                if(mOnMainClickListner !=null){
                    mOnMainClickListner.onMenuClick(IndicatorMenuItem.MENU_STORAGE);
                }
                break;
            case R.id.main_btn_seoullife:
                if(mOnMainClickListner !=null){
                    mOnMainClickListner.onMenuClick(IndicatorMenuItem.MENU_SPOT);
                }
                break;
            case R.id.main_btn_attraction:
                if(mOnMainClickListner !=null){
                    mOnMainClickListner.onMenuClick(IndicatorMenuItem.MENU_ATTRACTION);
                }
                break;
            case R.id.main_btn_convenience:
                if(mOnMainClickListner !=null){
                    mOnMainClickListner.onMenuClick(IndicatorMenuItem.MENU_USEFUL);
                }
                break;
            case R.id.main_btn_economic:
                if(mOnMainClickListner !=null){
                    mOnMainClickListner.onMenuClick(IndicatorMenuItem.MENU_CURRENCY);
                }
                break;
            case R.id.main_btn_speak:
                if(mOnMainClickListner !=null){
                    mOnMainClickListner.onMenuClick(9999);
                }
                break;
            case R.id.link_visitseoul:
                openBrowser(ARRAY_ADDR_LINK[0]);
                break;
            case R.id.link_seoulstay:
                openBrowser(ARRAY_ADDR_LINK[1]);
                break;
            case R.id.link_visitkorea:
                openBrowser(ARRAY_ADDR_LINK[2]);
                break;
        }
    }

    private void openBrowser(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void startClock(){
        if(mTimeHandler!=null){
            mTimeHandler.removeMessages(0);
        }

        mTimeHandler.sendEmptyMessage(0);
    }

    class TimeSet extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String result="";
            TimeZone tz;
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");
            tz = TimeZone.getTimeZone(params[0]);
            df.setTimeZone(tz);
            result+=df.format(date);
            tz = TimeZone.getTimeZone(params[1]);
            df.setTimeZone(tz);
            result += "//"+df.format(date);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String[] times = s.split("//", 2);
            mLeftTime.setText(times[0]);
            mRightTime.setText(times[1]);
        }
    }

    private Handler mTimeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            new TimeSet().execute(TIMEZONE_SEOUL, mTimeZone);
            mTimeHandler.sendEmptyMessageDelayed(0, 60000);
        }
    };

    public void setTimeZone(String timeZone){
        mTimeZone = timeZone;
        if(timeZone.contains("/")){
            String[] arr = timeZone.split("/");
            mRightTimeText.setText(arr[arr.length-1]);
        }else{
            mRightTimeText.setText(timeZone);
        }
        if(mOnMainClickListner!=null){
            mOnMainClickListner.hideLoadingTime();
        }
        startClock();
    }

    @Override
    public void onDestroy() {
        mTimeHandler.removeMessages(0);
        SeoulianPreferenceManager.saveString(SeoulianPreferenceManager.NAME_TIME_ZONE, mTimeZone);
        super.onDestroy();
    }

    private View.OnClickListener timeZoneClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnMainClickListner !=null){
                mOnMainClickListner.onTimeZoneClick();
            }
        }
    };

    class InitSliderTask extends AsyncTask<String, Void, TextSliderView>{

        @Override
        protected TextSliderView doInBackground(String... params) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(params[0])
                    .image(mFileMaps.get(params[0]))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",params[0]);

            return textSliderView;
        }

        @Override
        protected void onPostExecute(TextSliderView textSliderView) {
            mDemoSlider.addSlider(textSliderView);
        }
    }
}
