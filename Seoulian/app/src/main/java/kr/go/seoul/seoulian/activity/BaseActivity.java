package kr.go.seoul.seoulian.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.HeaderView;
import kr.go.seoul.seoulian.component.PopupEmergencyCall;
import kr.go.seoul.seoulian.component.PopupIntlTaxi;
import kr.go.seoul.seoulian.component.PopupLicense;
import kr.go.seoul.seoulian.component.SelectStringListDialog;
import kr.go.seoul.seoulian.component.PopupSpotDetail;
import kr.go.seoul.seoulian.component.PopupWebView;
import kr.go.seoul.seoulian.data.SpotInfo;
import kr.go.seoul.seoulian.manager.SeoulianPreferenceManager;

public abstract class BaseActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected FrameLayout mContentLayout;
    protected DrawerLayout mBase;
    protected NavigationView mNaviView;
    protected HeaderView mHeaderView;
    private RelativeLayout mLoading;
    private long mFirstClickFinishTime;
    protected SelectStringListDialog mDialogSelectStringList;
    protected PopupWebView mWebViewPopup;
    protected PopupSpotDetail mSpotPopup;
    protected PopupIntlTaxi mTaxiPopup;
    protected PopupEmergencyCall mEmergencyPopup;
    protected PopupLicense mLicensePopup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        init();

        mContentLayout = (FrameLayout)findViewById(R.id.content_layout);
        mBase = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNaviView = (NavigationView) findViewById(R.id.nav_view);
        mHeaderView = (HeaderView) findViewById(R.id.header);
        mLoading = (RelativeLayout) findViewById(R.id.loading);
        mDialogSelectStringList = (SelectStringListDialog) findViewById(R.id.dialog_select_list);
        mWebViewPopup = (PopupWebView) findViewById(R.id.webview_popup);
        mSpotPopup = (PopupSpotDetail) findViewById(R.id.spot_detail_popup);
        mTaxiPopup = (PopupIntlTaxi)findViewById(R.id.taxi_popup);
        mEmergencyPopup = (PopupEmergencyCall)findViewById(R.id.emergency_popup);
        mLicensePopup = (PopupLicense)findViewById(R.id.license_popup);
        mNaviView.setNavigationItemSelectedListener(this);
        mSpotPopup.setOnSpotPopupActionListener(new PopupSpotDetail.OnSpotPopupActionListener() {
            @Override
            public void onStorageClick() {
                spotStorageClicked();
            }
        });

        View NaviheaderView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null, false);
        NaviheaderView.findViewById(R.id.btn_close_navi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        NaviheaderView.findViewById(R.id.btn_help_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHelpMeClicked();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        mNaviView.addHeaderView(NaviheaderView);

        mHeaderView.setHeaderSelectListener(new HeaderView.HeaderSelectListener() {
            @Override
            public void onMenuClick(int menuItem) {
                onMenuItemClick(menuItem);
            }

            @Override
            public void onNavigationMenuClick() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(mNaviView);
            }

            @Override
            public void onEmergencyCallClick() {
                showEmergencyPopup();
            }
        });

        mDialogSelectStringList.setOnLoadListener(new SelectStringListDialog.OnLoadListener() {
            @Override
            public void onLoaded() {
                hideLoading();
            }
        });

    }

    private void init(){
        SeoulianPreferenceManager.createSharedPreference(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(mWebViewPopup.getVisibility()==View.VISIBLE){
            hideWebViewPopup();
        }else if(mTaxiPopup.getVisibility()==View.VISIBLE){
            hideTaxiPopup();
        }else if(mEmergencyPopup.getVisibility()==View.VISIBLE){
            hideEmergencyPopup();
        }else if(mLicensePopup.getVisibility()==View.VISIBLE){
            hideLicensePopup();
        }else if(mSpotPopup.getVisibility()==View.VISIBLE){
            hideSpotPopup();
        }else if(mDialogSelectStringList.getVisibility()==View.VISIBLE){
            mDialogSelectStringList.setVisibility(View.GONE);
        }else {
            if((System.currentTimeMillis() - mFirstClickFinishTime) < 2000) {
                super.onBackPressed();
            }else{
                mFirstClickFinishTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), getString(R.string.toast_press_back_button_to_finish), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //TODO must override
        return false;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = View.inflate(this, layoutResID, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mContentLayout.addView(view, layoutParams);
    }

    protected void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
    }

    protected void hideLoading(){
        mLoading.setVisibility(View.GONE);
    }

    public void setBackground(int drawableResId){
        mBase.setBackgroundResource(drawableResId);
    }

    public void clearMenuSelected(){
        mHeaderView.clearCheck();
    }

    public abstract void onMenuItemClick(int menuItem);

    public void setMenuPosition(int position){
        mHeaderView.setCheck(position);
    }

    public void showStringListDialog(ArrayList<String> data, AdapterView.OnItemClickListener l, String subTitle){
        mDialogSelectStringList.setSubTitle(subTitle);
        mDialogSelectStringList.setOnItemClickListener(l);
        mDialogSelectStringList.showDialog(data);
    }
    public void showStringListDialog(String[] data, AdapterView.OnItemClickListener l, String subTitle){
        mDialogSelectStringList.setSubTitle(subTitle);
        mDialogSelectStringList.setOnItemClickListener(l);
        mDialogSelectStringList.showDialog(data);
    }

    public void hideStringListDialog(){
        mDialogSelectStringList.hideDialog();
    }

    public void showWebViewPopup(XmlData xmlData){
        mWebViewPopup.showPopup(xmlData);
    }

    public void hideWebViewPopup(){
        mWebViewPopup.hidePopup();
    }

    public void showSpotPopup(XmlData xmlData){
        mSpotPopup.showPopup(xmlData);
    }

    public void hideSpotPopup(){
        mSpotPopup.hidePopup();
    }

    public void showStoragePopup(SpotInfo spotInfo){
        mSpotPopup.showPopup(spotInfo);
    }

    public void showTaxiPopup(){
        mTaxiPopup.showPopup();
    }

    public void hideTaxiPopup(){
        mTaxiPopup.hidePopup();
    }

    public void showEmergencyPopup(){
        mEmergencyPopup.showPopup();
    }

    public void hideEmergencyPopup(){
        mEmergencyPopup.hidePopup();
    }

    public void showLicensePopup(){
        mLicensePopup.showPopup();
    }

    public void hideLicensePopup(){
        mLicensePopup.hidePopup();
    }

    public void spotStorageClicked(){
        //TODO 오버라이드 필요
    }

    protected void onHelpMeClicked(){
        //TODO 오버라이드 필요
    }

}
