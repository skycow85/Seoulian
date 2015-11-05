package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.go.seoul.seoulian.R;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class SelectStringListDialog extends RelativeLayout {
    private ListView mList;
    private TextView mBtnClose;
    private TextView mSubText;

    public SelectStringListDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.select_list_dialog, this, true);
        mList = (ListView)findViewById(R.id.dialog_list);
        mBtnClose = (TextView)findViewById(R.id.dialog_btn_close);
        mSubText = (TextView)findViewById(R.id.dialog_subtext);
        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
            }
        });
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener l){
        mList.setOnItemClickListener(l);
    }

    public void hideDialog(){
        setVisibility(View.GONE);
    }

    public void showDialog(ArrayList<String> data){
        mList.setAdapter(new DialogListAdapter(data));
        setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_appear_alpha_scale);
        startAnimation(ani);
        if(mOnLoadListener!=null){
            mOnLoadListener.onLoaded();
        }

    }
    public void showDialog(String[] data){
        mList.setAdapter(new DialogListAdapter(data));
        setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_appear_alpha_scale);
        startAnimation(ani);
        if(mOnLoadListener!=null){
            mOnLoadListener.onLoaded();
        }
    }

    private class DialogListAdapter extends BaseAdapter{

        private ArrayList<String> mDataList;
        private String[] mDataArr;

        public DialogListAdapter(ArrayList<String> data){
            mDataList = data;
            mDataArr = null;
        }

        public DialogListAdapter(String[] data){
            mDataArr = data;
            mDataList = null;
        }

        @Override
        public int getCount() {
            int cnt;
            if(mDataList!=null){
                cnt = mDataList.size();
            }else{
                cnt = mDataArr.length;
            }
            return cnt;
        }

        @Override
        public Object getItem(int position) {
            String item;
            if(mDataList!=null){
                item = mDataList.get(position);
            }else{
                item = mDataArr[position];
            }
            return item;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.dialog_list_item, null, false);
            }
            TextView text = (TextView)convertView.findViewById(R.id.dialog_list_item_text);
            if(mDataList!=null){
                text.setText(mDataList.get(position));
            }else{
                text.setText(mDataArr[position]);
            }
            return convertView;
        }
    }

    public void setSubTitle(String text){
        mSubText.setText(text);
    }

    public interface OnLoadListener{
        void onLoaded();
    }

    private static OnLoadListener mOnLoadListener;

    public void setOnLoadListener(OnLoadListener l){
        mOnLoadListener = l;
    }


}
