package kr.go.seoul.seoulian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.adapter.AttractionListAdapter;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class AttractionFragment extends Fragment implements AdapterView.OnItemClickListener {
    public final static String URL_CATEGORY = "http://openapi.seoul.go.kr:8088/754a6e5a456a6568363543704f6f77/xml/VisitSeoulEn/1/100";

    public final static String TAG_DIVIDE_ONE_ITEM_CATEGORY = "row";
    public final static String TAG_CATEGORY_NAME = "CATEGORY4";
    public final static String TAG_CATEGORY_URL = "URL";
    public final static String TAG_DIVIDE_ONE_ITEM_CONTENT = "item";

    private AttractionListAdapter mAttractionAdapter = null;
    private ListView mAttractionList;
    private TextView mTextSelectCategory;
    private TextView mNoticeSelectCategory;
    private ArrayList<XmlData> mAttractionDataSet;
    private RelativeLayout mBtnSelectCategory;
    public interface OnAttractionActionListener {
        void onSelectCategotyClick();
        void onListClick(XmlData xmlData);
        void showLoadingTime();
        void hideLoadingTime();

    }

    private static OnAttractionActionListener mListener;

    public void setOnAttractionActionListener(OnAttractionActionListener l){
        mListener = l;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_attraction, null);
        mAttractionList = (ListView) base.findViewById(R.id.attraction_list);
        mTextSelectCategory = (TextView) base.findViewById(R.id.text_select_category);
        mNoticeSelectCategory = (TextView) base.findViewById(R.id.notice_select_category);
        mBtnSelectCategory = (RelativeLayout)base.findViewById(R.id.btn_select_category);
        mAttractionList.setOnItemClickListener(this);
        mBtnSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelectCategotyClick();
                }
            }
        });
        return base;
    }

    public void setAttractionList(ArrayList<XmlData> data, String title){
        if(mNoticeSelectCategory.getVisibility()==View.VISIBLE){
            mNoticeSelectCategory.setVisibility(View.GONE);
            mAttractionList.setVisibility(View.VISIBLE);
        }
        mTextSelectCategory.setText(title);
        mAttractionDataSet = data;
        mAttractionAdapter = new AttractionListAdapter(getContext(), R.layout.attractions_list, mAttractionDataSet);
        mAttractionList.setAdapter(mAttractionAdapter);
        if(mListener!=null){
            mListener.hideLoadingTime();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(mListener!=null){
            mListener.onListClick(mAttractionDataSet.get(position));
        }
    }
}
