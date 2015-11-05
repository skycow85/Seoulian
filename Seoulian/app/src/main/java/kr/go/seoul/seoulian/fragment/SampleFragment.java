package kr.go.seoul.seoulian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.memetix.mst.translate.Translate;

import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.data.StaticData;

/**
 * Created by Administrator on 2015-10-25.
 */
public class SampleFragment extends Fragment {
    TextView text;
    EditText edit;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sample, null);
        btn = (Button)view.findViewById(R.id.btn);
        text = (TextView)view.findViewById(R.id.text);
        edit = (EditText)view.findViewById(R.id.edit);
        Translate.setClientId(StaticData.API_KEY_MICROSOFT_TRANSLATE_CLIENT);
        Translate.setClientSecret(StaticData.API_KEY_MICROSOFT_TRANSLATE_SECRET);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onTClicked(edit.getText().toString());
                }
            }
        });

        return view;
    }

    public interface SampleListener{
        void onTClicked(String s);
    }

    private static SampleListener mListener;

    public void setSampleListener(SampleListener l){
        mListener = l;
    }
}
