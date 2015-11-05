package kr.go.seoul.seoulian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.Network.XmlParser;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.SpotContentList;
import kr.go.seoul.seoulian.data.StaticData;

/**
 * Created by jkwoo on 2015-10-27.
 */
public class SpotFragment extends Fragment implements View.OnClickListener{
    public final static String URL_GET_SECTION = "http://openapi.seoul.go.kr:8088/754a6e5a456a6568363543704f6f77/xml/ForeignerLivingInfoCategoryService/1/300/";
    public final static String URL_GET_LIST = "http://openapi.seoul.go.kr:8088/754a6e5a456a6568363543704f6f77/xml/ForeignerLivingInfoService/";

    public final static String TAG_DIVIDE_ONE_ITEM_CATEGORY = "row";
    public final static String TAG_DIVIDE_ONE_ITEM_CONTENT = "row";
    public final static String TAG_SECTION_NAME = "SECTION_NM_EN";
    public final static String TAG_SECTION_CODE = "SECTION_CD";
    public final static String NOT_SPECIFIC = "none";
    public final static String NO_VALUE = "";


    private RelativeLayout mCategory;
    private RelativeLayout mSection;
    private LinearLayout mSearch;
    private TextView mCategoryText;
    private TextView mSectionText;
    private SpotContentList mListView;

    private int mCntPerPage=30;
    private int mCurrentStartNo=1;
    private int mCurrentLastNo=30;
    private String mCategoryCode = "";
    private String mSectionCode = "";

    private XmlParser mParser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = LayoutInflater.from(getContext()).inflate(R.layout.fragment_spot, null);

        mCategory = (RelativeLayout)base.findViewById(R.id.spot_category);
        mSection = (RelativeLayout)base.findViewById(R.id.spot_section);
        mSearch = (LinearLayout)base.findViewById(R.id.spot_btn_search);
        mCategoryText = (TextView)base.findViewById(R.id.spot_category_text);
        mSectionText = (TextView)base.findViewById(R.id.spot_section_text);
        mListView = (SpotContentList)base.findViewById(R.id.list);

        mCategory.setOnClickListener(this);
        mSection.setOnClickListener(this);
        mSearch.setOnClickListener(this);

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
        loadData(true);
        return base;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.spot_category:
                if(mListener!=null){
                    mListener.onCategoryClicked();
                }
                break;
            case R.id.spot_section:
                if(mListener!=null){
                    mListener.onSectionClicked();
                }
                break;
            case R.id.spot_btn_search:
                mCurrentStartNo = 1;
                mCurrentLastNo = 30;
                mListView.initailizeList();
                if(mListener!=null){
                    mListener.showLoadingTime();
                }
                loadData(true);
                break;
        }
    }

    private void setList(ArrayList<XmlData> list){
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
        mCurrentStartNo = mCurrentLastNo + 1;
        mCurrentLastNo = mCurrentLastNo + mCntPerPage;
        if(mListener!=null){
            mListener.hideLoadingTime();
        }
    }

    private SpotContentList.OnScrollEndedListener mScrollEndListener = new SpotContentList.OnScrollEndedListener() {
        @Override
        public void onScrollEnded() {
            Log.d("SpotFragment", "onScrollEnded");
            loadData(false);
        }
    };

    public void loadData(boolean isNew){
        Log.e("SpotFragment", "loadData");
        if(isNew && mParser!=null){
            mParser.cancel();
        }
        String url = URL_GET_LIST + mCurrentStartNo + "/" + mCurrentLastNo + "/" +mCategoryCode + "/" +mSectionCode;
        ArrayList<String> arrTag = new ArrayList<>();
        for(String s : StaticData.ARRAY_TAG_SPOT_GET_LIST){
            arrTag.add(s);
        }

        mParser = new XmlParser(TAG_DIVIDE_ONE_ITEM_CONTENT, arrTag, url) {
            @Override
            public void success(ArrayList<XmlData> data) {
                setList(data);
            }

            @Override
            public void fail(Exception e) {
                Toast.makeText(getContext(), getString(R.string.network_exception), Toast.LENGTH_SHORT).show();
                if(mListener!=null){
                    mListener.hideLoadingTime();
                }
            }
        };
        mParser.startParsing();
    }

    public interface SpotActionListener{
        void onCategoryClicked();
        void onSectionClicked();
        void onItemClicked(XmlData xmlData);
        void showLoadingTime();
        void hideLoadingTime();
    }

    private static SpotActionListener mListener;

    public void setSpotActionListener(SpotActionListener l){
        mListener = l;
    }

    public void setCategory(int position){
        mCategoryCode = StaticData.ARRAY_SPOT_CATEGORY_CODE[position];
        mCategoryText.setText(StaticData.ARRAY_SPOT_CATEGORY_NAME[position]);
        setSection(NO_VALUE, getString(R.string.big_section));
    }

    public void setSection(String code, String name){
        mSectionCode = code;
        mSectionText.setText(name);
    }

    public String getCategoryCode(){
        return mCategoryCode;
    }


}
