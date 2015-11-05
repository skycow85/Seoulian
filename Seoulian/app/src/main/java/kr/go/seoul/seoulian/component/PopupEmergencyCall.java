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
import android.widget.ScrollView;

import kr.go.seoul.seoulian.R;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class PopupEmergencyCall extends RelativeLayout implements View.OnClickListener{

    private LinearLayout mBtnClose;
    private LinearLayout mBtn112;
    private LinearLayout mBtn119;
    private LinearLayout mBtn1330;
    private LinearLayout mBtn120;
    private ScrollView mScroll;

    public PopupEmergencyCall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.popup_emergency_call, this, true);
        mBtnClose = (LinearLayout)findViewById(R.id.btn_close);
        mBtn112 = (LinearLayout)findViewById(R.id.btn_112);
        mBtn119 = (LinearLayout)findViewById(R.id.btn_119);
        mBtn1330 = (LinearLayout)findViewById(R.id.btn_1330);
        mBtn120 = (LinearLayout)findViewById(R.id.btn_120);
        mScroll = (ScrollView)findViewById(R.id.scroll);
        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopup();
            }
        });
        mBtn112.setOnClickListener(this);
        mBtn119.setOnClickListener(this);
        mBtn1330.setOnClickListener(this);
        mBtn120.setOnClickListener(this);
    }

    public void onClick(View v) {
        String number = "";
        switch (v.getId()){
            case R.id.btn_112:
                number = "112";
                break;
            case R.id.btn_119:
                number = "119";
                break;
            case R.id.btn_1330:
                number = "1330";
                break;
            case R.id.btn_120:
                number = "02-120";
                break;
        }

        Intent intentCall = new Intent(Intent.ACTION_CALL);
        intentCall.setData(Uri.parse("tel:" + number));
        getContext().startActivity(intentCall);
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
        mScroll.fullScroll(ScrollView.FOCUS_UP);
    }
}
