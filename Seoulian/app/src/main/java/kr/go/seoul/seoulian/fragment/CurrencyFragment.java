package kr.go.seoul.seoulian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.go.seoul.seoulian.Http.HttpConnection;
import kr.go.seoul.seoulian.R;
import kr.go.seoul.seoulian.manager.SeoulianPreferenceManager;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class CurrencyFragment extends Fragment {
    private final static String mUrl = "http://query.yahooapis.com/v1/public/yql?q=";
    private final static String mParam1_1 = "select * from yahoo.finance.xchange where pair in ('";
    private final static String mParam1_2 = "')";
    private final static String mParam2 = "format=json&env=store://datatables.org/alltableswithkeys";
    private final static String DEFAULT_FROM_CURRENCY = "USD";
    private final static String DEFAULT_TO_CURRENCY = "KRW";

    private HttpConnection mConnection;
    private String mFromCurrency;
    private String mToCurrency;

    private RelativeLayout mEconomicFromCurrency;
    private RelativeLayout mEconomicToCurrency;
    private TextView mEconomicViewCurrency;
    private TextView mEconomicTextFromCurrency;
    private TextView mEconomicTextToCurrency;
    private EditText mEconomicInputCurrency;
    private TextView mEconomicBtnCalculate;

    public interface OnCurrencyActionListener {
        void onFromCurrencyClick();
        void onToCurrencyClick();
        void showLoadingTime();
        void hideLoadingTime();
    }

    private static OnCurrencyActionListener mListener;

    public void setOnCurrencyActionListener(OnCurrencyActionListener l){
        mListener = l;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View base = inflater.inflate(R.layout.fragment_currency, null);
        mEconomicFromCurrency = (RelativeLayout) base.findViewById(R.id.economic_fromCurrency);
        mEconomicToCurrency = (RelativeLayout) base.findViewById(R.id.economic_toCurrency);
        mEconomicViewCurrency = (TextView) base.findViewById(R.id.economic_viewCurrency);
        mEconomicInputCurrency = (EditText) base.findViewById(R.id.economic_inputCurrency);
        mEconomicBtnCalculate = (TextView) base.findViewById(R.id.economic_btn_calculate);
        mEconomicTextFromCurrency = (TextView)base.findViewById(R.id.economic_left_text);
        mEconomicTextToCurrency = (TextView)base.findViewById(R.id.economic_right_text);

        setFromCurrency(SeoulianPreferenceManager.getString(SeoulianPreferenceManager.NAME_FROM_CURRENCY, DEFAULT_FROM_CURRENCY));
        setToCurrency(SeoulianPreferenceManager.getString(SeoulianPreferenceManager.NAME_TO_CURRENCY, DEFAULT_TO_CURRENCY));

        mEconomicBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.showLoadingTime();
                }
                String input = mEconomicInputCurrency.getText().toString();
                try {
                    Double value = Double.parseDouble(input);
                    calculateCurrency(value);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.no_amount), Toast.LENGTH_SHORT).show();
                    if(mListener!=null){
                        mListener.hideLoadingTime();
                    }
                }
            }
        });

        mEconomicFromCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onFromCurrencyClick();
                }
            }
        });

        mEconomicToCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onToCurrencyClick();
                }
            }
        });
        return base;
    }

    private void calculateCurrency(final Double value) {
        String calculUrl = null;
        try {
            calculUrl = mUrl + URLEncoder.encode(mParam1_1 + (mFromCurrency + mToCurrency) + mParam1_2, "UTF-8") + "&" + mParam2;

            mConnection = new HttpConnection(getContext(), calculUrl, false);
            mConnection.setResultListener(new HttpConnection.ResultListener() {
                @Override
                public void success(JSONObject jsonObject) {
                    try {
                        String result = jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getString("Rate");
                        Double valueDoble = Double.parseDouble(result);
                        Double resultCurrency = valueDoble * value;
                        mEconomicViewCurrency.setText("" + getRounded(resultCurrency));
                    } catch (JSONException e) {
                        Log.d("calculateCurrency", "JSONException : " + e.getMessage());
                        Toast.makeText(getContext(), getString(R.string.network_exception), Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Log.d("calculateCurrency", "NumberFormatException");
                    }
                    if(mListener!=null){
                        mListener.hideLoadingTime();
                    }
                }

                @Override
                public void fail() {
                    if(mListener!=null){
                        mListener.hideLoadingTime();
                    }
                    Toast.makeText(getContext(), getString(R.string.network_exception), Toast.LENGTH_SHORT).show();
                }
            });
            mConnection.startConnect();
        } catch (UnsupportedEncodingException e) {
            if(mListener!=null){
                mListener.hideLoadingTime();
            }
            Log.d("calculateCurrency", "UnsupportedEncodingException");
        }
    }

    public void setFromCurrency(String currency){
        mFromCurrency = currency;
        mEconomicTextFromCurrency.setText(currency);
    }

    public void setToCurrency(String currency){
        mToCurrency = currency;
        mEconomicTextToCurrency.setText(currency);
    }

    @Override
    public void onDestroy() {
        SeoulianPreferenceManager.saveString(SeoulianPreferenceManager.NAME_FROM_CURRENCY, mFromCurrency);
        SeoulianPreferenceManager.saveString(SeoulianPreferenceManager.NAME_TO_CURRENCY, mToCurrency);
        super.onDestroy();
    }

    private double getRounded(double org){
        return Math.round(org*100d)/100d;
    }
}
