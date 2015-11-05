package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2015-10-24.
 */
public class ContentEditText extends EditText {

    private volatile static Typeface mCustomFont;

    public ContentEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mCustomFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/SeoulNamsanEB.ttf");
        setTypeface(mCustomFont);
    }
}
