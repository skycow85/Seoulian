package kr.go.seoul.seoulian.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.util.Locale;

import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.data.StaticData;

/**
 * Created by jkwoo on 2015-10-30.
 */
public abstract class TranslateActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech mTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Translate.setClientId(StaticData.API_KEY_MICROSOFT_TRANSLATE_CLIENT);
        Translate.setClientSecret(StaticData.API_KEY_MICROSOFT_TRANSLATE_SECRET);
        mTTS = new TextToSpeech(this, this);
        mTTS.setLanguage(Locale.KOREAN);
    }

    @Override
    public void onInit(int status) {
    }

    /**
     * 번역 호출 from언어 자동 탐색됨
     * @param fromString 원래 언어 문자열
     */
    protected void startTranslate(String fromString){
        new TranslateTask().execute(fromString);
    }
    // 오버라이드
    abstract void getTranslatedString(String result);

    private class TranslateTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String toString = Translate.execute(params[0], Language.KOREAN);
                Log.e("TTranslate", "toString: " + toString);
                return toString;
            } catch (Exception e) {
                Log.e("TTranslate", e.getMessage()+"//"+e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null){
                Toast.makeText(getApplicationContext(), getString(R.string.network_exception), Toast.LENGTH_SHORT).show();
            }
            getTranslatedString(s);
        }
    }

    protected void speakString(String s){
        mTTS.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    // 톤
    protected void setTTSPitch(float pitch){
        if(pitch>2.0f){
            pitch = 2.0f;
        }
        if(pitch<0.1f){
            pitch = 0.1f;
        }
        mTTS.setPitch(pitch);
    }

    // 속도
    protected void setTTSSpeechRate(float speed){
        if(speed>2.0f){
            speed = 2.0f;
        }
        if(speed<0.1f){
            speed = 0.1f;
        }
        mTTS.setSpeechRate(speed);
    }
}
