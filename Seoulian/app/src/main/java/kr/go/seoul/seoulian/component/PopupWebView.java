package kr.go.seoul.seoulian.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.data.StaticData;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class PopupWebView extends RelativeLayout {


    private LinearLayout mBtnClose;
    private TextView mTitle;
    private WebView mWebView;
    public PopupWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.popup_webview, this, true);
        mBtnClose = (LinearLayout)findViewById(R.id.btn_close);
        mTitle = (TextView)findViewById(R.id.title);
        mWebView = (WebView)findViewById(R.id.webview);
        mBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePopup();
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.scrollTo(0, 0);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onPageFinished(WebView view, String url) {
                view.scrollTo(0,0);
            }
        });
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
    }


    public void showPopup(XmlData xmlData){
        mTitle.setText(xmlData.getValue("title"));
        mWebView.clearHistory();
        mWebView.clearCache(false);
        mWebView.loadUrl(null);
        mWebView.loadData(getFullHtml(xmlData.getValue("description")), "text/html", "UTF-8");
        mWebView.loadData(getFullHtml(xmlData.getValue("description")), "text/html", "UTF-8");
        setVisibility(View.VISIBLE);
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_appear_down_to_up);
        startAnimation(ani);
    }

    public void hidePopup(){
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.popup_disappear_up_to_down);
        startAnimation(ani);
        setVisibility(View.GONE);
    }

    private String getFullHtml(String body){
        String result = StaticData.HTML_STARTER+body+StaticData.HTML_FINISHER;
        Log.d("WebViewPopup", result);
        return result;
    }
}
