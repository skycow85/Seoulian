package kr.go.seoul.seoulian.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.R;

/**
 * Created by admin on 2015-10-24.
 */
public class AttractionListAdapter extends ArrayAdapter {

    private ArrayList<XmlData> mItems;
    private Context				mContext = null;
    private int					mnViewResourceId;
    private LayoutInflater 		mInflater;
    private ViewWrapper mWrapper = null;

    public AttractionListAdapter(Context context, int textViewResourceId, ArrayList<XmlData> items) {
        super(context, textViewResourceId, items);
        this.mContext = context;
        this.mnViewResourceId = textViewResourceId;
        this.mInflater 	= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItems = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if( v == null ){
            v = mInflater.inflate(mnViewResourceId, null);
            mWrapper = new ViewWrapper(v);
            v.setTag(mWrapper);
        }else {
            mWrapper = (ViewWrapper)v.getTag();
        }
        mWrapper.getCATEGORY().setText(mItems.get(position).getValue("title"));

        return v;
    }

    public class ViewWrapper {
        View base;

        public TextView mCateagory = null;

        public ViewWrapper(View base) {
            this.base = base;
        }

        public TextView getCATEGORY(){
            if(mCateagory == null) {
                mCateagory = (TextView)base.findViewById(R.id.attractions_category);

            }
            return mCateagory;
        }


    }
}
