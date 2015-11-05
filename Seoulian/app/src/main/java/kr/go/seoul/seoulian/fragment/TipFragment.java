package kr.go.seoul.seoulian.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import kr.go.seoul.seoulian.R;

/**
 * Created by jkwoo on 2015-10-29.
 */
public class TipFragment extends Fragment implements View.OnClickListener{
    public static final String URL_TRAVEL = "http://wikitravel.org/en/Seoul";
    public static final String URL_AIRPORT = "http://www.cyberairport.kr/mo/main.do?lang=en";
    public static final String URL_NEWS = "http://theseoultimes.com/ST/";
    public static final String URL_SUBWAY = "http://asiaenglish.visitkorea.or.kr/ena/TR/TR_EN_5_1_4.jsp";
    public static final String URL_BUS = "http://asiaenglish.visitkorea.or.kr/ena/TR/TR_EN_5_1_3_2.jsp";

    RelativeLayout mBtnTravel;
    RelativeLayout mBtnAirport;
    RelativeLayout mBtnNews;
    RelativeLayout mBtnSubway;
    RelativeLayout mBtnBus;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_tip, null);
        mBtnTravel = (RelativeLayout)base.findViewById(R.id.tip_btn_travel);
        mBtnAirport = (RelativeLayout)base.findViewById(R.id.tip_btn_airport);
        mBtnNews = (RelativeLayout)base.findViewById(R.id.tip_btn_news);
        mBtnSubway = (RelativeLayout)base.findViewById(R.id.tip_btn_subway);
        mBtnBus = (RelativeLayout)base.findViewById(R.id.tip_btn_bus);

        mBtnTravel.setOnClickListener(this);
        mBtnAirport.setOnClickListener(this);
        mBtnNews.setOnClickListener(this);
        mBtnSubway.setOnClickListener(this);
        mBtnBus.setOnClickListener(this);

        return base;
    }

    @Override
    public void onClick(View v){
        String url="";
        switch(v.getId()){
            case R.id.tip_btn_travel:
                url = URL_TRAVEL;
                break;
            case R.id.tip_btn_airport:
                url = URL_AIRPORT;
                break;
            case R.id.tip_btn_news:
                url = URL_NEWS;
                break;
            case R.id.tip_btn_subway:
                url = URL_SUBWAY;
                break;
            case R.id.tip_btn_bus:
                url = URL_BUS;
                break;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
