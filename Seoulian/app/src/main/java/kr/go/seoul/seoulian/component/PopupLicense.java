package kr.go.seoul.seoulian.component;

import android.content.Context;
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
public class PopupLicense extends RelativeLayout {


    private LinearLayout mBtnClose;
    public PopupLicense(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.popup_license, this, true);
        mBtnClose = (LinearLayout)findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopup();
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
