package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.data.StaticData;

/**
 * Created by jkwoo on 2015-10-27.
 */
public class SpotContentList extends ListView {
    private DefaultAdapter mDefaultAdapter = new DefaultAdapter();
    private boolean mLastitemVisibleFlag = false;
    private ArrayList<XmlData> mList = new ArrayList<>();
    private int mCurrentLast;
    private View mLoadingView;

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
            mLastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            mCurrentLast = totalItemCount;
        }
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
            //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
            if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && mLastitemVisibleFlag && mLoadingView.getVisibility() == View.VISIBLE) {
                //TODO 화면이 바닦에 닿을때 처리
                if(null != mOnScrollEndedListener) {
                    setFooterViewLoading(true);
                    mOnScrollEndedListener.onScrollEnded();
                }
            }
        }

    };

    public void initailizeList(){
        setFooterViewLoading(false);
        mList.clear();
    }

    public void setFooterViewLoading(boolean isLoading){
        if (mLoadingView == null)
            return;
        if (!isLoading && getAdapter() != null) {
            removeFooterView(mLoadingView);
        } else if (isLoading && getAdapter() != null && getFooterViewsCount() == 0) {
            addFooterView(mLoadingView, null, false);
        }
    }

    public SpotContentList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setDefaultAdapter(){
        super.setAdapter(mDefaultAdapter);
        this.setAdapter(mDefaultAdapter);
    }

    public void addData(ArrayList<XmlData> list){
        mList.addAll(list);
    }

    public XmlData getData(int position){
        return mList.get(position);
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLoadingView = inflater.inflate(R.layout.list_item_footer, null);
        setOnScrollListener(mOnScrollListener);
    }

    public static interface OnScrollEndedListener {
        public void onScrollEnded();
    }
    private OnScrollEndedListener mOnScrollEndedListener;
    public void setOnScrollEndedListener(OnScrollEndedListener l){
        mOnScrollEndedListener = l;
    }

    private class DefaultAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {

            return 1;

        }

        @Override
        public int getItemViewType(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spot_list_item, null);
                holder = new ViewHolder();
                holder.icon = (ImageView)convertView.findViewById(R.id.spot_list_icon);
                holder.title = (TextView)convertView.findViewById(R.id.spot_list_title);
                holder.content = (TextView)convertView.findViewById(R.id.spot_list_content);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            new ImageViewTask().execute(holder.icon, position);
            new TextViewTask().execute(holder.title, TextViewTask.TITLE, position);
            new TextViewTask().execute(holder.content, TextViewTask.CONTENT, position);

            return convertView;
        }
    }

    private static class ViewHolder{
        ImageView icon;
        TextView title;
        TextView content;
    }

    public void notifyDataChanged() {
        mDefaultAdapter.notifyDataSetChanged();
    }

    class TextViewTask extends AsyncTask<Object, Void, String>{
        public static final int TITLE = 0;
        public static final int CONTENT = 1;
        private TextView mTextView;
        @Override
        protected String doInBackground(Object... params) {
            mTextView = (TextView)params[0];
            int type = (int)params[1];
            int position = (int)params[2];
            switch (type){
                case TITLE:
                    String enName = mList.get(position).getValue("D_CORP_EN_NM");
                    if(enName==null || enName.trim().isEmpty()){
                        String krName = mList.get(position).getValue("D_CORP_KR_NM");
                        if(krName==null || krName.trim().isEmpty()){
                            return "No Name";
                        }else{
                            return krName;
                        }
                    }else{
                        return enName;
                    }
                case CONTENT:
                    return mList.get(position).getValue("INFO_CORP_EN_INTRO");
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            mTextView.setText(s);
        }
    }

    class ImageViewTask extends AsyncTask<Object, Void, Integer>{
        private ImageView mImageView;
        @Override
        protected Integer doInBackground(Object... params) {
            mImageView = (ImageView)params[0];
            int position = (int)params[1];
            return StaticData.getIconResForSpot(mList.get(position).getValue("CATEGORY_CD"));

        }
        @Override
        protected void onPostExecute(Integer i) {
            mImageView.setImageResource(i);
        }
    }

}
