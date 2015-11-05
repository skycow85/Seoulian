package kr.go.seoul.seoulian.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import kr.go.seoul.seoulian.R;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class UsefulFragment extends Fragment{
    private LinearLayout mTaxi;
    private LinearLayout mDasan;
    private static final String URL_DASAN_HAPPY_CALL ="http://120dasan.seoul.go.kr/happyCallService/HappyCallServiceAction.jsp?method=UsList";
    private static final String URL_INTL_TAXI ="http://intltaxi.co.kr/en/index.html";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_useful, null);

        mTaxi = (LinearLayout)base.findViewById(R.id.conven_taxi);
        mDasan = (LinearLayout)base.findViewById(R.id.conven_dasan);

        mDasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                Uri u = Uri.parse(URL_DASAN_HAPPY_CALL);

                i.setData(u);

                startActivity(i);
            }
        });

        mTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.taxiClicked();
                }
            }
        });

        return base;
    }

    public interface OnUsefulActionListener {
        void taxiClicked();
    }

    private static OnUsefulActionListener mListener;

    public void setOnUsefulActionListener(OnUsefulActionListener l){
        mListener = l;
    }
}
