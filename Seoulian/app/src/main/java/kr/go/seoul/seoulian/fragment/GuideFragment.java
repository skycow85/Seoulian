package kr.go.seoul.seoulian.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.activity.GuideActivity;
import kr.go.seoul.seoulian.activity.MainActivity;
import kr.go.seoul.seoulian.manager.SeoulianPreferenceManager;


public class GuideFragment extends Fragment implements OnClickListener{
    public static final String ARG_PAGE = "page";
    private  int mPageNumber;
    private LinearLayout mGuideImg, mGuideEnd2;
    private ImageView mGuideEnd1,mGuideBtn1,mGuideBtn2,mGuideBtn3;
    private Context mContext;



    public static GuideFragment create(int pageNumber) {
        Log.i("SH","aa");
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GuideFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        int  layoutNum = R.layout.fragment_guide;

        ViewGroup rootView = (ViewGroup) inflater
                .inflate(layoutNum, container, false);

        mGuideImg = (LinearLayout) rootView.findViewById(R.id.guide_layout);
        mGuideEnd1 = (ImageView)rootView.findViewById(R.id.guide_end1);
        mGuideEnd2 = (LinearLayout)rootView.findViewById(R.id.guide_end2);
        mGuideBtn1 = (ImageView)rootView.findViewById(R.id.guide_btn1);
        mGuideBtn2 = (ImageView)rootView.findViewById(R.id.guide_btn2);
        mGuideBtn3 = (ImageView)rootView.findViewById(R.id.guide_btn3);
        mGuideEnd1.setOnClickListener(this);
        mGuideEnd2.setOnClickListener(this);

        if(mPageNumber == 0){

            mGuideImg.setBackgroundResource(R.drawable.img_detail_one3);
            mGuideBtn1.setBackgroundResource(R.drawable.btn_detail_activity);
            mGuideBtn2.setBackgroundResource(R.drawable.btn_detail_off);
            mGuideBtn3.setBackgroundResource(R.drawable.btn_detail_off);

        }else if(mPageNumber == 1){
            mGuideImg.setBackgroundResource(R.drawable.img_detail_two);
            mGuideBtn1.setBackgroundResource(R.drawable.btn_detail_off);
            mGuideBtn2.setBackgroundResource(R.drawable.btn_detail_activity);
            mGuideBtn3.setBackgroundResource(R.drawable.btn_detail_off);
        }else{
            mGuideImg.setBackgroundResource(R.drawable.img_detail_three);
            mGuideBtn1.setBackgroundResource(R.drawable.btn_detail_off);
            mGuideBtn2.setBackgroundResource(R.drawable.btn_detail_off);
            mGuideBtn3.setBackgroundResource(R.drawable.btn_detail_activity);
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int v_id = v.getId();

        switch (v_id) {

            case R.id.guide_end2:
                SeoulianPreferenceManager.saveBoolean(SeoulianPreferenceManager.NAME_DONT_SHOW_GUIDE,true);
            case R.id.guide_end1:
                showLoading();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
               getActivity().finish();
                break;

        }
    }

    private void showLoading(){
        ((GuideActivity)getActivity()).showLoading();
    }

}
