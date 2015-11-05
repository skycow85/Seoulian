package kr.go.seoul.seoulian.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.StorageContentList;
import kr.go.seoul.seoulian.data.SpotInfo;
import kr.go.seoul.seoulian.manager.MySQLiteHandler;

/**
 * Created by jkwoo on 2015-10-27.
 */
public class StorageFragment extends Fragment {

    private StorageContentList mListView;
    private int mCntPerPage = 30;
    private int mCurrentStartNo = 0;
    private MySQLiteHandler mDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = LayoutInflater.from(getContext()).inflate(R.layout.fragment_storage, null);
        mListView = (StorageContentList)base.findViewById(R.id.list);

        mListView.setDefaultAdapter();
        mListView.setOnScrollEndedListener(mScrollEndListener);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListener!=null){
                    mListener.onItemClicked(mListView.getData(position));
                }
            }
        });
        loadData();
        return base;
    }

    private void setList(ArrayList<SpotInfo> list){
        if(list.size() == 0){
            mListView.setFooterViewLoading(false);
            if(mListener!=null){
                mListener.hideLoadingTime();
            }
            return;
        }
        if(list.size() >= mCntPerPage){
            mListView.setFooterViewLoading(true);
        }else{
            mListView.setFooterViewLoading(false);
        }
        mListView.addData(list);
        mListView.notifyDataChanged();
        mCurrentStartNo += mCntPerPage;
        if(mListener!=null){
            mListener.hideLoadingTime();
        }
    }

    private StorageContentList.OnScrollEndedListener mScrollEndListener = new StorageContentList.OnScrollEndedListener() {
        @Override
        public void onScrollEnded() {
            Log.d("StorageFragment", "onScrollEnded");
            loadData();
        }
    };

    public void loadData(){
        Log.e("StorageFragment", "loadData");
        new DBLoaderTask().execute();
    }

    public interface StorageActionListener {
        void onItemClicked(SpotInfo spotInfo);
        void showLoadingTime();
        void hideLoadingTime();
    }

    private static StorageActionListener mListener;

    public void setStorageActionListener(StorageActionListener l){
        mListener = l;
    }

    public void refresh(){
        mCurrentStartNo = 0;
        mListView.initailizeList();
        loadData();
    }

    class DBLoaderTask extends AsyncTask<Void, Void, ArrayList<SpotInfo>>{

        @Override
        protected ArrayList<SpotInfo> doInBackground(Void... params) {
            mDB = MySQLiteHandler.open(getContext());
            ArrayList<SpotInfo> list = mDB.select(mCurrentStartNo, mCntPerPage);
            mDB.close();
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<SpotInfo> list) {
            setList(list);
        }
    }
}
