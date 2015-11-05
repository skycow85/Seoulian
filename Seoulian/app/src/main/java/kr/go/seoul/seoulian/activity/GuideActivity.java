package kr.go.seoul.seoulian.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.fragment.GuideFragment;
import kr.go.seoul.seoulian.manager.SeoulianPreferenceManager;

public class GuideActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    private static Context mContext = null;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private RelativeLayout mLoading;
    private long mFirstClickFinishTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mLoading = (RelativeLayout)findViewById(R.id.loading);
        mContext = this;

        SeoulianPreferenceManager.createSharedPreference(mContext);
        boolean isCheck = SeoulianPreferenceManager.getBoolean(SeoulianPreferenceManager.NAME_DONT_SHOW_GUIDE, false);
        if(isCheck){
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }
        mPager = (ViewPager) findViewById(R.id.guide_pager);
        mPager.setOffscreenPageLimit(NUM_PAGES);

        mPagerAdapter = new GuidePagerAdapter(getSupportFragmentManager());
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });
        mPager.setAdapter(mPagerAdapter);
    }
    private class GuidePagerAdapter extends FragmentPagerAdapter {

        public GuidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            return GuideFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub

            return PagerAdapter.POSITION_NONE;
        }
    }

    public void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - mFirstClickFinishTime) < 2000) {
            super.onBackPressed();
        }else{
            mFirstClickFinishTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), getString(R.string.toast_press_back_button_to_finish), Toast.LENGTH_SHORT).show();
        }
        super.onBackPressed();
    }
}
