package kr.go.seoul.seoulian.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import kr.go.seoul.seoulian.Network.Form.XmlData;
import kr.go.seoul.seoulian.Network.XmlParser;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.component.IndicatorMenuItem;
import kr.go.seoul.seoulian.data.SpotInfo;
import kr.go.seoul.seoulian.data.StaticData;
import kr.go.seoul.seoulian.fragment.AttractionFragment;
import kr.go.seoul.seoulian.fragment.CurrencyFragment;
import kr.go.seoul.seoulian.fragment.HelpMeFragment;
import kr.go.seoul.seoulian.fragment.MainFragment;
import kr.go.seoul.seoulian.fragment.SpeakerFragment;
import kr.go.seoul.seoulian.fragment.SpotFragment;
import kr.go.seoul.seoulian.fragment.StorageFragment;
import kr.go.seoul.seoulian.fragment.TipFragment;
import kr.go.seoul.seoulian.fragment.UsefulFragment;
import kr.go.seoul.seoulian.manager.SeoulianPreferenceManager;

public class MainActivity extends TranslateActivity {
    private final int mPageCnt = 8;
    private int HelpMeCnt  = 5;
    private ViewPager mViewPager;

    private MainFragment mMainFragment;
    private SpotFragment mSpotFragment;
    private AttractionFragment mAttractionFragment;
    private UsefulFragment mUsefulFragment;
    private CurrencyFragment mCurrencyFragment;
    private TipFragment mTipFragment;
    private StorageFragment mStorageFragment;
    private SpeakerFragment mSpeakerFragment;
    private float mTTSPitch = 1.0f;
    private float mTTSSpeechRate = 1.0f;

    private Context mContext;
    private HelpMeFragment mHelpMeFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;



        createFragments();
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new SeoulianPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setMenuPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(mPageCnt - 1);
        setBackground(R.drawable.main_back2);

        mTTSPitch = SeoulianPreferenceManager.getFloat(SeoulianPreferenceManager.NAME_TTS_PITCH, 1.0f);
        mTTSSpeechRate = SeoulianPreferenceManager.getFloat(SeoulianPreferenceManager.NAME_TTS_SPEECH_RATE, 1.0f);

        /* 도와주세요!! */
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        /* 도와주세요!! */
    }




        @Override
    public void onMenuItemClick(int menuItem) {
        mViewPager.setCurrentItem(menuItem);
    }

    private class SeoulianPagerAdapter extends FragmentStatePagerAdapter{
        public SeoulianPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case IndicatorMenuItem.MENU_HOME:
                    return mMainFragment;
                case IndicatorMenuItem.MENU_SPOT:
                    return mSpotFragment;
                case IndicatorMenuItem.MENU_ATTRACTION:
                    return mAttractionFragment;
                case IndicatorMenuItem.MENU_USEFUL:
                    return mUsefulFragment;
                case IndicatorMenuItem.MENU_CURRENCY:
                    return mCurrencyFragment;
                case IndicatorMenuItem.MENU_TIP:
                    return mTipFragment;
                case IndicatorMenuItem.MENU_SPEAKER:
                    return mSpeakerFragment;
                case IndicatorMenuItem.MENU_STORAGE:
                    return mStorageFragment;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return mPageCnt;
        }
    }

    public void createFragments(){
        mMainFragment = new MainFragment();
        mSpotFragment = new SpotFragment();
        mAttractionFragment = new AttractionFragment();
        mUsefulFragment = new UsefulFragment();
        mCurrencyFragment = new CurrencyFragment();
        mTipFragment = new TipFragment();
        mSpeakerFragment = new SpeakerFragment();
        mStorageFragment = new StorageFragment();

        setListenerOnMainFragment();
        setListenerOnSpotFragment();
        setListenerOnAttractionFragment();
        setListenerOnUsefulFragment();
        setListenerOnCurrencyFragment();
        setListenerOnSpeakerFragment();
        setListenerOnStorageFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        if(handler!=null){
            handler.removeMessages(0);
        }
        if(mHelpMeFragment!=null && mHelpMeFragment.isLife()){
            mHelpMeFragment.stop();
            mHelpMeFragment = null;
        }
        super.onStop();
    }

    private void setListenerOnMainFragment(){
        mMainFragment.setOnMainActionListener(new MainFragment.OnMainActionListener() {
            @Override
            public void onMenuClick(int position) {
                if (position == 9999) {

                } else {
                    mViewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onTimeZoneClick() {
                showLoading();
                final String[] tz = TimeZone.getAvailableIDs();
                showStringListDialog(tz, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mMainFragment.setTimeZone(tz[position]);
                        hideStringListDialog();
                    }
                }, getString(R.string.dialog_select_timezone));
            }

            @Override
            public void hideLoadingTime() {
                hideLoading();
            }

            @Override
            public void showLoadingTime() {
                showLoading();
            }
        });
    }

    private void setListenerOnSpotFragment(){
        mSpotFragment.setSpotActionListener(new SpotFragment.SpotActionListener() {
            @Override
            public void onCategoryClicked() {
                showStringListDialog(StaticData.ARRAY_SPOT_CATEGORY_NAME, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSpotFragment.setCategory(position);
                        hideStringListDialog();
                    }
                }, getString(R.string.dialog_select_category));
            }

            @Override
            public void onSectionClicked() {
                String catCode = mSpotFragment.getCategoryCode();
                if (catCode == null || catCode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_select_category), Toast.LENGTH_SHORT).show();
                } else {
                    XmlParser parser = new XmlParser(SpotFragment.TAG_DIVIDE_ONE_ITEM_CATEGORY, getArrayListWithArray(StaticData.ARRAY_TAG_SPOT_GET_SECTION), SpotFragment.URL_GET_SECTION + "/" + catCode) {
                        @Override
                        public void success(ArrayList<XmlData> data) {
                            final HashMap<String, String> map = new HashMap<>();
                            map.put(SpotFragment.NOT_SPECIFIC, SpotFragment.NO_VALUE);
                            final ArrayList<String> arrName = new ArrayList<>();
                            arrName.add(SpotFragment.NOT_SPECIFIC);
                            for (int i = 0; i < data.size(); i++) {
                                String name = data.get(i).getValue(SpotFragment.TAG_SECTION_NAME);
                                if (map.put(name, data.get(i).getValue(SpotFragment.TAG_SECTION_CODE)) == null) {
                                    arrName.add(name);
                                }
                            }
                            showStringListDialog(arrName, new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String name = arrName.get(position);
                                    mSpotFragment.setSection(map.get(name), name);
                                    hideStringListDialog();

                                }
                            }, getString(R.string.dialog_select_section));
                        }

                        @Override
                        public void fail(Exception e) {
                            showToastNetworkErr();
                        }
                    };
                    parser.startParsing();
                }
            }

            @Override
            public void onItemClicked(XmlData xmlData) {
                showSpotPopup(xmlData);
            }

            @Override
            public void showLoadingTime() {
                showLoading();
            }

            @Override
            public void hideLoadingTime() {
                hideLoading();
            }
        });
    }

    private void setListenerOnAttractionFragment(){
        mAttractionFragment.setOnAttractionActionListener(new AttractionFragment.OnAttractionActionListener() {
            @Override
            public void onSelectCategotyClick() {
                setAttractionCategoryList();
            }

            @Override
            public void onListClick(XmlData xmlData) {
                showWebViewPopup(xmlData);
            }

            @Override
            public void showLoadingTime() {
                showLoading();
            }

            @Override
            public void hideLoadingTime() {
                hideLoading();
            }
        });
    }

    private void setListenerOnUsefulFragment(){
        mUsefulFragment.setOnUsefulActionListener(new UsefulFragment.OnUsefulActionListener() {
            @Override
            public void taxiClicked() {
                showTaxiPopup();
            }
        });
    }
    private void setListenerOnSpeakerFragment(){
        mSpeakerFragment.setOnSpeakerActionListener(new SpeakerFragment.OnSpeakerActionListener() {
            @Override
            public void onButtonClicked(int id) {
                switch (id) {
                    case R.id.btn_speaker_translate:
                        String m = mSpeakerFragment.getMessage();
                        if (m != null && !m.trim().isEmpty()) {
                            startTranslate(m);
                        }
                        break;
                    case R.id.btn_speaker_speak:
                        String korean = mSpeakerFragment.getKorean();
                        if (korean != null && !korean.trim().isEmpty()) {
                            speakString(korean);
                        }
                        break;
                    case R.id.btn_lower:
                        mTTSPitch -= 0.3f;
                        if (mTTSPitch < 0.1f) {
                            mTTSPitch = 0.1f;
                        }
                        setTTSPitch(mTTSPitch);
                        break;
                    case R.id.btn_higher:
                        mTTSPitch += 0.3f;
                        if (mTTSPitch > 2.0f) {
                            mTTSPitch = 2.0f;
                        }
                        setTTSPitch(mTTSPitch);
                        break;
                    case R.id.btn_slower:
                        mTTSSpeechRate -= 0.3f;
                        if (mTTSSpeechRate < 0.1f) {
                            mTTSSpeechRate = 0.1f;
                        }
                        setTTSSpeechRate(mTTSSpeechRate);
                        break;
                    case R.id.btn_faster:
                        mTTSSpeechRate += 0.3f;
                        if (mTTSSpeechRate > 2.0f) {
                            mTTSSpeechRate = 2.0f;
                        }
                        setTTSSpeechRate(mTTSSpeechRate);
                        break;
                }
            }
        });
    }
    private void setListenerOnCurrencyFragment(){
        mCurrencyFragment.setOnCurrencyActionListener(new CurrencyFragment.OnCurrencyActionListener() {
            @Override
            public void onFromCurrencyClick() {
                showLoading();
                showStringListDialog(StaticData.ARRAY_TAG_CURRENCY_DATASET, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mCurrencyFragment.setFromCurrency(StaticData.ARRAY_TAG_CURRENCY_DATASET[position]);
                        hideStringListDialog();
                    }
                }, getString(R.string.dialog_select_before_currency));
            }

            @Override
            public void onToCurrencyClick() {
                showLoading();
                showStringListDialog(StaticData.ARRAY_TAG_CURRENCY_DATASET, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mCurrencyFragment.setToCurrency(StaticData.ARRAY_TAG_CURRENCY_DATASET[position]);
                        hideStringListDialog();
                    }
                }, getString(R.string.dialog_select_after_currency));
            }

            @Override
            public void showLoadingTime() {
                showLoading();
            }

            @Override
            public void hideLoadingTime() {
                hideLoading();
            }
        });
    }

    private void setListenerOnStorageFragment(){
        mStorageFragment.setStorageActionListener(new StorageFragment.StorageActionListener() {
            @Override
            public void onItemClicked(SpotInfo spotInfo) {
                showStoragePopup(spotInfo);
            }

            @Override
            public void showLoadingTime() {

            }

            @Override
            public void hideLoadingTime() {

            }
        });
    }

    private void setAttractionCategoryList(){
        showLoading();
        String url = AttractionFragment.URL_CATEGORY;
        XmlParser parser = new XmlParser(AttractionFragment.TAG_DIVIDE_ONE_ITEM_CATEGORY, getArrayListWithArray(StaticData.ARRAY_TAG_ATTRACTION_CATEGORY), url) {
            @Override
            public void success(final ArrayList<XmlData> data) {
                final String[] categories = new String[data.size()];
                for(int i=0; i<data.size(); i++){
                    categories[i] = data.get(i).getValue(AttractionFragment.TAG_CATEGORY_NAME);
                }
                showStringListDialog(categories, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        setAttractionList(data.get(position).getValue(AttractionFragment.TAG_CATEGORY_URL), data.get(position).getValue(AttractionFragment.TAG_CATEGORY_NAME));
                        hideStringListDialog();
                    }
                }, getString(R.string.dialog_select_category));
            }

            @Override
            public void fail(Exception e) {
                hideLoading();
                showToastNetworkErr();
            }
        };
        parser.startParsing();
    }

    private void setAttractionList(String url, final String title){
        showLoading();
        XmlParser parser = new XmlParser(AttractionFragment.TAG_DIVIDE_ONE_ITEM_CONTENT, getArrayListWithArray(StaticData.ARRAY_TAG_ATTRACTION_LIST), url) {
            @Override
            public void success(ArrayList<XmlData> data) {
                mAttractionFragment.setAttractionList(data, title);
            }

            @Override
            public void fail(Exception e) {
                hideLoading();
                showToastNetworkErr();
            }
        };

        parser.startParsing();
    }

    private ArrayList<String> getArrayListWithArray(String[] src){
        ArrayList<String> arrListString = new ArrayList<>();
        for(String s : src){
            arrListString.add(s);
        }
        return arrListString;
    }

    private void showToastNetworkErr(){
        Toast.makeText(getApplicationContext(), getString(R.string.network_exception), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void spotStorageClicked(){
        mStorageFragment.refresh();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.navi_home:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_HOME);
                break;
            case R.id.navi_tip:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_TIP);
                break;
            case R.id.navi_storage:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_STORAGE);
                break;
            case R.id.navi_spot:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_SPOT);
                break;
            case R.id.navi_attraction:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_ATTRACTION);
                break;
            case R.id.navi_useful:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_USEFUL);
                break;
            case R.id.navi_currency:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_CURRENCY);
                break;
            case R.id.navi_korean:
                mViewPager.setCurrentItem(IndicatorMenuItem.MENU_SPEAKER);
                break;
            case R.id.navi_license:
                showLicensePopup();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onHelpMeClicked(){
        //TODO 여기에서 "도와주세요" 음원 1초 간격 반복 재생, 볼륨 자동 최대 (-> 정지 시 원래대로 돌려놓음)
        //TODO 팝업 제작해야 하므로 "음원 반복 재생하는 메서드(볼륨 최대)", "음원 정지하는 메서드(볼륨 원복)" 두개 제작해서 작성 바랍니다.

        showDialog();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    private void showDialog(){


        if(mHelpMeFragment== null) {
            mHelpMeFragment = new HelpMeFragment();
            mHelpMeFragment.setOnDetachListener(new HelpMeFragment.OnDetachListener() {
                @Override
                public void onDetached() {
                    HelpMeCnt = 5;
                    if(handler!=null){
                        handler.removeMessages(0);
                    }
                }
            });
            mHelpMeFragment.show(getFragmentManager(), "HelpMe");
        }
        else if(mHelpMeFragment.getIsLife()==false){
            mHelpMeFragment.show(getFragmentManager(), "HelpMe");
        }else{
            mHelpMeFragment.setHelpMeCnt(Integer.toString(HelpMeCnt));
        }
    }


    @Override
    void getTranslatedString(String result) {
        mSpeakerFragment.setKorean(result);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 0){

                if(HelpMeCnt > 0) {
                    HelpMeCnt--;
                    showDialog();
                    if(HelpMeCnt == 0){

                        if(mHelpMeFragment.getMediaPlayer() == null){

                            mHelpMeFragment.helpMeStart();
                        }

                        HelpMeCnt = 5;
                    }else {

                        handler.sendEmptyMessageDelayed(0, 1000);
                    }

                }

            }


        }
    };

    @Override
    public void onBackPressed() {
        if(handler!=null){
            handler.removeMessages(0);
        }
        if(mHelpMeFragment!=null && mHelpMeFragment.isLife()){
            mHelpMeFragment.stop();
            mHelpMeFragment = null;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        SeoulianPreferenceManager.saveFloat(SeoulianPreferenceManager.NAME_TTS_PITCH, mTTSPitch);
        SeoulianPreferenceManager.saveFloat(SeoulianPreferenceManager.NAME_TTS_SPEECH_RATE, mTTSSpeechRate);
        super.onDestroy();
    }
}
