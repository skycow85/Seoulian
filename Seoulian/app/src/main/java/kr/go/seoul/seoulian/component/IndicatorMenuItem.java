package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Administrator on 2015-10-24.
 */
public class IndicatorMenuItem extends RadioButton{

    public static final int MENU_HOME = 0;
    public static final int MENU_SPOT = 1;
    public static final int MENU_ATTRACTION = 2;
    public static final int MENU_USEFUL = 3;
    public static final int MENU_CURRENCY = 4;
    public static final int MENU_TIP = 5;
    public static final int MENU_SPEAKER = 6;
    public static final int MENU_STORAGE = 7;

    public IndicatorMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/BMDOHYEON_ttf.ttf");
        setTypeface(font);
    }
}
