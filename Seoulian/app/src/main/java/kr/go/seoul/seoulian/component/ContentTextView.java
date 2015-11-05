package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015-10-24.
 */
public class ContentTextView extends TextView {

    private volatile static Typeface mCustomFont;

    public ContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mCustomFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/SeoulNamsanEB.ttf");
        setTypeface(mCustomFont);
    }
}
