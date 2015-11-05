package kr.go.seoul.seoulian.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.ContentTextView;
import kr.go.seoul.seoulian.component.TitleTextView;


public class HelpMeFragment extends DialogFragment implements OnClickListener{
    private LinearLayout mCloseBtn;
    private ContentTextView mStopBtn;
    private TitleTextView mHelpMeCnt;
    private Context mContext;
    private  AlertDialog.Builder builder;
    private MediaPlayer mMp = null;
    private Boolean mIsLife = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mIsLife = true;
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.popup_help_me, null);

        mCloseBtn = (LinearLayout) rootView.findViewById(R.id.btn_close);
        mStopBtn = (ContentTextView)rootView.findViewById(R.id.btn_stop);
        mHelpMeCnt = (TitleTextView)rootView.findViewById(R.id.help_me_count);

        mCloseBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);

        builder.setView(rootView);

        return builder.create();
    }

    public void setHelpMeCnt(String helpMeCnt){

        mHelpMeCnt.setText(helpMeCnt);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int v_id = v.getId();

        switch (v_id) {

            case R.id.btn_close:
            case R.id.btn_stop:
                stop();
                break;

        }
    }

    public void stop(){
        mIsLife = false;
        helpMeEnd();
        dismiss();
    }

    public boolean isLife(){
        return mIsLife;
    }

    public Boolean getIsLife(){
        return mIsLife;
    }
    public MediaPlayer getMediaPlayer(){
        return mMp;
    }
    public void helpMeStart(){
        mMp = MediaPlayer.create(mContext.getApplicationContext(), R.raw.help_me);

        mMp.setVolume(1.0f, 1.0f); // 불륨 1 최대치
        mMp.setLooping(true);
        mMp.start();

    }
    public void helpMeEnd(){
        if(mMp != null){
            mMp.stop();
            mMp.release();
            mMp= null;
        }
    }

    @Override
    public void onDetach() {
        mIsLife = false;
        stop();
        if(mOnDetachListener!=null){
            mOnDetachListener.onDetached();
        }
        super.onDetach();
    }

    private OnDetachListener mOnDetachListener;

    public void setOnDetachListener(OnDetachListener l){
        mOnDetachListener = l;
    }

    public interface OnDetachListener{
        void onDetached();
    }
}
