package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import kr.go.seoul.seoulian.R;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class PopupIntlTaxi extends RelativeLayout {

    private static String LINK_URL = "http://www.intltaxi.co.kr/en/index.html";
    private static String CALL_NUMBER = "1644-2255";

    private LinearLayout mBtnClose;
    private LinearLayout mBtnLink;
    private LinearLayout mBtnCall;

    public PopupIntlTaxi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.popup_inter_taxi, this, true);
        mBtnClose = (LinearLayout)findViewById(R.id.btn_close);
        mBtnLink = (LinearLayout)findViewById(R.id.taxi_btn_link);
        mBtnCall = (LinearLayout)findViewById(R.id.taxi_btn_call);
        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopup();
            }
        });
        mBtnLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_URL));
                getContext().startActivity(intent);
            }
        });
        mBtnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:"+CALL_NUMBER));
                getContext().startActivity(intentCall);
            }
        });
    }


    public void showPopup(){
        setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_appear_down_to_up);
        startAnimation(ani);
    }

    public void hidePopup(){
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_disappear_up_to_down);
        startAnimation(ani);
        setVisibility(View.GONE);
    }
}
