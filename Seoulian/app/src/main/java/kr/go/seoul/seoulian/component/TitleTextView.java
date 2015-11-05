package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015-10-24.
 */
public class TitleTextView extends TextView {

    private volatile static Typeface mCustomFont;

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public TitleTextView(Context context) {
        super(context);
        init();
    }


    private void init(){
        mCustomFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/BMDOHYEON_ttf.ttf");
        setTypeface(mCustomFont);
    }
}
