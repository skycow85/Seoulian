package kr.go.seoul.seoulian.data;

import kr.go.seoul.seoulian.R;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class StaticData {
    public final static String API_KEY_DAUM = "7daea115b8f4113484eef9577c4ea08c";
    public final static String API_KEY_MICROSOFT_TRANSLATE_CLIENT = "seoulian";
    public final static String API_KEY_MICROSOFT_TRANSLATE_SECRET = "gIhlewLTLQL+lCfmp/W65c4hZB8kpyteKkKciHe+rng=";
    public final static String API_KEY_SEOUL = "754a6e5a456a6568363543704f6f77";

    public final static String[] ARRAY_TAG_CURRENCY_DATASET = {"KRW", "USD", "JPY", "CNY"};
    public final static String[] ARRAY_TAG_ATTRACTION_CATEGORY = {"SYSTEMNM","CATEGORY1", "CATEGORY2", "CATEGORY3","CATEGORY4", "INFO_ID", "LANGUAGE","URL"};
    public final static String[] ARRAY_TAG_ATTRACTION_LIST = {"author","category", "title", "link","guid", "description", "pubDate","tag"};
    public final static String HTML_STARTER = "<!DOCTYPE html>" +
            "<html lang=\"ko\">" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "</head>" +
            "<body>";
    public final static String HTML_FINISHER = "</body></html>";
    public final static String[] ARRAY_SPOT_CATEGORY_CODE = {"","147","201","46","27","228","1","221","105","82","55","16","212","128","241","192"};
    public final static String[] ARRAY_SPOT_CATEGORY_NAME = {"none","Organizations/Governmental Bodies",
            "Immigration",
            "Accomodation & Housing",
            "Travel/Tour",
            "Religious Services",
            "Shopping",
            "Culture/Arts",
            "Transportation/Driving",
            "Lesiure/Entertainment",
            "Medical/Health Care",
            "Education/School",
            "Banking","Communication","Business","Miscellaneous Services"};
    public final static int[] ARRAY_SPOT_CATEGORY_IMAGE = {0,R.drawable.sicon_goverment,
            R.drawable.sicon_immigration,
            R.drawable.sicon_acomodation,
            R.drawable.sicon_travel,
            R.drawable.sicon_religious_services,
            R.drawable.sicon_shopping,
            R.drawable.sicon_culture,
            R.drawable.sicon_transportation,
            R.drawable.sicon_entertainment,
            R.drawable.sicon_medical,
            R.drawable.sicon_education,
            R.drawable.sicon_banking,
            R.drawable.sicon_communication,
            R.drawable.sicon_business,
            R.drawable.sicon_miscellaneous_services};
        public static final String[] ARRAY_TAG_SPOT_GET_LIST = {"CORP_SEQ",
                "CATEGORY_CD", "CATEGORY_NM_KR", "CATEGORY_NM_EN",
                "SECTION_CD", "SECTION_NM_KR", "SECTION_NM_EN",
                "CONTENTS_CD", "CONTENTS_NM_KR", "CONTENTS_NM_EN",
                "D_CORP_KR_NM", "D_CORP_EN_NM",
                "INFO_CORP_KR_INTRO", "INFO_CORP_EN_INTRO",
                "URL", "PHONE1","PHONE2","EMAIL1","ADDR_KR","ADDR_EN","TAG_KR","TAG_EN"};
        public static final String[] ARRAY_TAG_SPOT_GET_SECTION = {"SECTION_CD", "SECTION_NM_EN", "CONTENTS_NM_EN"};


        public static int getIndexInCategoryCode(String code) {
                for (int i = 0; i < ARRAY_SPOT_CATEGORY_CODE.length; i++) {
                        if (ARRAY_SPOT_CATEGORY_CODE[i].equalsIgnoreCase(code)) {
                                return i;
                        }
                }
                return -1;

        }

        public static int getIconResForSpot(String code){
                int index = getIndexInCategoryCode(code);
                if(index!=-1){
                        return ARRAY_SPOT_CATEGORY_IMAGE[index];
                }else{
                        return 0;
                }
        }


}
