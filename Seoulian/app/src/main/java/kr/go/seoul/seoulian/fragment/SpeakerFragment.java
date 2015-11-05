package kr.go.seoul.seoulian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import kr.go.seoul.seoulian.R;

/**
 * Created by Administrator on 2015-10-30.
 */
public class SpeakerFragment extends Fragment implements View.OnClickListener{

    private TextView mBtnTranslate;
    private TextView mBtnSpeak;
    private TextView mBtnLower;
    private TextView mBtnHigher;
    private TextView mBtnSlower;
    private TextView mBtnFaster;
    private TextView mKoreanText;
    private EditText mEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_speaker, null);

        mBtnTranslate = (TextView)base.findViewById(R.id.btn_speaker_translate);
        mBtnSpeak = (TextView)base.findViewById(R.id.btn_speaker_speak);
        mBtnLower = (TextView)base.findViewById(R.id.btn_lower);
        mBtnHigher = (TextView)base.findViewById(R.id.btn_higher);
        mBtnSlower = (TextView)base.findViewById(R.id.btn_slower);
        mBtnFaster = (TextView)base.findViewById(R.id.btn_faster);

        mEditText = (EditText)base.findViewById(R.id.speaker_message);
        mKoreanText = (TextView)base.findViewById(R.id.speaker_korean);

        mBtnTranslate.setOnClickListener(this);
        mBtnSpeak.setOnClickListener(this);
        mBtnLower.setOnClickListener(this);
        mBtnHigher.setOnClickListener(this);
        mBtnSlower.setOnClickListener(this);
        mBtnFaster.setOnClickListener(this);

        return base;
    }

    @Override
    public void onClick(View v){
        if(mOnSpeakerActionListener!=null){
            mOnSpeakerActionListener.onButtonClicked(v.getId());
        }
    }

    public interface OnSpeakerActionListener{
        void onButtonClicked(int id);
    }

    private static OnSpeakerActionListener mOnSpeakerActionListener;

    public void setOnSpeakerActionListener(OnSpeakerActionListener l){
        mOnSpeakerActionListener = l;
    }

    public String getMessage(){
        return mEditText.getText().toString();
    }

    public String getKorean(){
        return mKoreanText.getText().toString();
    }

    public void setKorean(String s){
        mKoreanText.setText(s);
    }
}
