package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import kr.go.seoul.seoulian.R;

/**
 * Created by Administrator on 2015-10-24.
 */
public class HeaderView extends RelativeLayout implements View.OnClickListener{

    private RadioGroup mMenuGroup;
    private RelativeLayout mBtnNaviOpen;
    private RelativeLayout mBtnEmergencyCall;
    private IndicatorMenuItem mMenuHome;
    private IndicatorMenuItem mMenuSeoulLife;
    private IndicatorMenuItem mMenuAttraction;
    private IndicatorMenuItem mMenuConvenience;
    private IndicatorMenuItem mMenuEconomic;
    private IndicatorMenuItem mMenuTip;
    private IndicatorMenuItem mMenuSpeaker;
    private IndicatorMenuItem mMenuStorage;
    private HorizontalScrollView mMenuScroll;

    public interface HeaderSelectListener{
        void onMenuClick(int menuItem);
        void onNavigationMenuClick();
        void onEmergencyCallClick();
    }

    private HeaderSelectListener mHeaderSelectListener;

    public void setHeaderSelectListener(HeaderSelectListener l){
        mHeaderSelectListener = l;
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.app_header_view,this, true);
        mMenuGroup = (RadioGroup)findViewById(R.id.menu_group);
        mBtnNaviOpen = (RelativeLayout)findViewById(R.id.btn_navi_open);
        mBtnEmergencyCall = (RelativeLayout)findViewById(R.id.btn_emergency_call);
        mMenuHome = (IndicatorMenuItem)findViewById(R.id.menu_home);
        mMenuSeoulLife = (IndicatorMenuItem)findViewById(R.id.menu_seoullife);
        mMenuAttraction = (IndicatorMenuItem)findViewById(R.id.menu_attraction);
        mMenuConvenience = (IndicatorMenuItem)findViewById(R.id.menu_convenience);
        mMenuEconomic = (IndicatorMenuItem)findViewById(R.id.menu_economic);
        mMenuTip = (IndicatorMenuItem)findViewById(R.id.menu_tip);
        mMenuSpeaker = (IndicatorMenuItem)findViewById(R.id.menu_speaker);
        mMenuStorage = (IndicatorMenuItem)findViewById(R.id.menu_storage);
        mMenuScroll = (HorizontalScrollView)findViewById(R.id.menu_scroll);

        mMenuHome.setOnClickListener(this);
        mMenuSeoulLife.setOnClickListener(this);
        mMenuAttraction.setOnClickListener(this);
        mMenuConvenience.setOnClickListener(this);
        mMenuEconomic.setOnClickListener(this);
        mMenuTip.setOnClickListener(this);
        mMenuStorage.setOnClickListener(this);
        mMenuSpeaker.setOnClickListener(this);

        mBtnNaviOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHeaderSelectListener!=null){
                    mHeaderSelectListener.onNavigationMenuClick();
                }
            }
        });
        mBtnEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onEmergencyCallClick();
                }
            }
        });

        mMenuHome.setChecked(true);
    }

    public void setCheck(int position){
        Log.e("HeaderView", "Fragment Page : "+position);
        switch(position){
            case IndicatorMenuItem.MENU_HOME:
                mMenuHome.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                break;
            case IndicatorMenuItem.MENU_SPOT:
                mMenuSeoulLife.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                break;
            case IndicatorMenuItem.MENU_ATTRACTION:
                mMenuAttraction.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                break;
            case IndicatorMenuItem.MENU_USEFUL:
                mMenuConvenience.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                break;
            case IndicatorMenuItem.MENU_CURRENCY:
                mMenuEconomic.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                break;
            case IndicatorMenuItem.MENU_TIP:
                mMenuTip.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                break;
            case IndicatorMenuItem.MENU_SPEAKER:
                mMenuSpeaker.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                break;
            case IndicatorMenuItem.MENU_STORAGE:
                mMenuStorage.setChecked(true);
                mMenuScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                break;
        }

    }

    public void clearCheck(){
        mMenuGroup.clearCheck();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_home:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_HOME);
                }
                break;
            case R.id.menu_seoullife:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_SPOT);
                }
                break;
            case R.id.menu_attraction:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_ATTRACTION);
                }
                break;
            case R.id.menu_convenience:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_USEFUL);
                }
                break;
            case R.id.menu_economic:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_CURRENCY);
                }
                break;
            case R.id.menu_tip:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_TIP);
                }
                break;
            case R.id.menu_speaker:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_SPEAKER);
                }
                break;
            case R.id.menu_storage:
                if (mHeaderSelectListener != null) {
                    mHeaderSelectListener.onMenuClick(IndicatorMenuItem.MENU_STORAGE);
                }
                break;
        }
    }
}
