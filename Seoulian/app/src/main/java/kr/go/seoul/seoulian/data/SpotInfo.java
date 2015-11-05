package kr.go.seoul.seoulian.data;

/**
 * Created by hp on 2015-10-26.
 */
public class SpotInfo {

    String CORP_SEQ="";
    String CATEGORY_CD="";
    String CATEGORY_NM_KR="";
    String CATEGORY_NM_EN="";
    String SECTION_CD="";
    String SECTION_NM_KR="";
    String SECTION_NM_EN="";
    String CONTENTS_CD="";
    String CONTENTS_NM_KR="";


    String CONTENTS_NM_EN="";
    String D_CORP_KR_NM="";
    String D_CORP_EN_NM="";
    String INFO_CORP_KR_INTRO="";
    String INFO_CORP_EN_INTRO="";
    String URL="";
    String PHONE1="";
    String PHONE2="";

    String EMAIL1="";
    String ADDR_KR="";
    String ADDR_EN="";
    String TAG_KR="";
    String TAG_EN="";
    public SpotInfo(){}
    public SpotInfo(String CORP_SEQ,
                    String CATEGORY_CD,
                    String CATEGORY_NM_KR,
                    String CATEGORY_NM_EN,
                    String SECTION_CD,
                    String SECTION_NM_KR,
                    String SECTION_NM_EN,
                    String CONTENTS_CD,
                    String CONTENTS_NM_KR,
                    String CONTENTS_NM_EN,
                    String D_CORP_KR_NM,
                    String D_CORP_EN_NM,
                    String INFO_CORP_KR_INTRO,
                    String INFO_CORP_EN_INTRO,
                    String URL,
                    String PHONE1,
                    String PHONE2,
                    String EMAIL1,
                    String ADDR_KR,
                    String ADDR_EN,
                    String TAG_KR,
                    String TAG_EN){

        this.CORP_SEQ=CORP_SEQ;
        this.CATEGORY_CD=CATEGORY_CD;
        this.CATEGORY_NM_KR=CATEGORY_NM_KR;
        this.CATEGORY_NM_EN=CATEGORY_NM_EN;
        this.SECTION_CD=SECTION_CD;
        this.SECTION_NM_KR=SECTION_NM_KR;
        this.SECTION_NM_EN=SECTION_NM_EN;
        this.CONTENTS_CD=CONTENTS_CD;
        this.CONTENTS_NM_KR=CONTENTS_NM_KR;


        this.CONTENTS_NM_EN=CONTENTS_NM_EN;
        this.D_CORP_KR_NM=D_CORP_KR_NM;
        this.D_CORP_EN_NM=D_CORP_EN_NM;
        this.INFO_CORP_KR_INTRO=INFO_CORP_KR_INTRO;
        this.INFO_CORP_EN_INTRO=INFO_CORP_EN_INTRO;
        this.URL=URL;
        this.PHONE1=PHONE1;
        this.PHONE2=PHONE2;

        this.EMAIL1=EMAIL1;
        this.ADDR_KR=ADDR_KR;
        this.ADDR_EN=ADDR_EN;
        this.TAG_KR=TAG_KR;
        this.TAG_EN=TAG_EN;
    }
    public String getCORP_SEQ() {
        return CORP_SEQ;
    }

    public void setCORP_SEQ(String CORP_SEQ) {
        this.CORP_SEQ = CORP_SEQ;
    }

    public String getCATEGORY_CD() {
        return CATEGORY_CD;
    }

    public void setCATEGORY_CD(String CATEGORY_CD) {
        this.CATEGORY_CD = CATEGORY_CD;
    }

    public String getCATEGORY_NM_KR() {
        return CATEGORY_NM_KR;
    }

    public void setCATEGORY_NM_KR(String CATEGORY_NM_KR) {
        this.CATEGORY_NM_KR = CATEGORY_NM_KR;
    }

    public String getCATEGORY_NM_EN() {
        return CATEGORY_NM_EN;
    }

    public void setCATEGORY_NM_EN(String CATEGORY_NM_EN) {
        this.CATEGORY_NM_EN = CATEGORY_NM_EN;
    }

    public String getSECTION_CD() {
        return SECTION_CD;
    }

    public void setSECTION_CD(String SECTION_CD) {
        this.SECTION_CD = SECTION_CD;
    }

    public String getSECTION_NM_KR() {
        return SECTION_NM_KR;
    }

    public void setSECTION_NM_KR(String SECTION_NM_KR) {
        this.SECTION_NM_KR = SECTION_NM_KR;
    }

    public String getSECTION_NM_EN() {
        return SECTION_NM_EN;
    }

    public void setSECTION_NM_EN(String SECTION_NM_EN) {
        this.SECTION_NM_EN = SECTION_NM_EN;
    }

    public String getCONTENTS_CD() {
        return CONTENTS_CD;
    }

    public void setCONTENTS_CD(String CONTENTS_CD) {
        this.CONTENTS_CD = CONTENTS_CD;
    }

    public String getCONTENTS_NM_KR() {
        return CONTENTS_NM_KR;
    }

    public void setCONTENTS_NM_KR(String CONTENTS_NM_KR) {
        this.CONTENTS_NM_KR = CONTENTS_NM_KR;
    }

    public String getCONTENTS_NM_EN() {
        return CONTENTS_NM_EN;
    }

    public void setCONTENTS_NM_EN(String CONTENTS_NM_EN) {
        this.CONTENTS_NM_EN = CONTENTS_NM_EN;
    }

    public String getD_CORP_KR_NM() {
        return D_CORP_KR_NM;
    }

    public void setD_CORP_KR_NM(String d_CORP_KR_NM) {
        D_CORP_KR_NM = d_CORP_KR_NM;
    }

    public String getD_CORP_EN_NM() {
        return D_CORP_EN_NM;
    }

    public void setD_CORP_EN_NM(String d_CORP_EN_NM) {
        D_CORP_EN_NM = d_CORP_EN_NM;
    }

    public String getINFO_CORP_KR_INTRO() {
        return INFO_CORP_KR_INTRO;
    }

    public void setINFO_CORP_KR_INTRO(String INFO_CORP_KR_INTRO) {
        this.INFO_CORP_KR_INTRO = INFO_CORP_KR_INTRO;
    }

    public String getINFO_CORP_EN_INTRO() {
        return INFO_CORP_EN_INTRO;
    }

    public void setINFO_CORP_EN_INTRO(String INFO_CORP_EN_INTRO) {
        this.INFO_CORP_EN_INTRO = INFO_CORP_EN_INTRO;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPHONE1() {
        return PHONE1;
    }

    public void setPHONE1(String PHONE1) {
        this.PHONE1 = PHONE1;
    }

    public String getPHONE2() {
        return PHONE2;
    }

    public void setPHONE2(String PHONE2) {
        this.PHONE2 = PHONE2;
    }

    public String getEMAIL1() {
        return EMAIL1;
    }

    public void setEMAIL1(String EMAIL1) {
        this.EMAIL1 = EMAIL1;
    }

    public String getADDR_KR() {
        return ADDR_KR;
    }

    public void setADDR_KR(String ADDR_KR) {
        this.ADDR_KR = ADDR_KR;
    }

    public String getADDR_EN() {
        return ADDR_EN;
    }

    public void setADDR_EN(String ADDR_EN) {
        this.ADDR_EN = ADDR_EN;
    }

    public String getTAG_KR() {
        return TAG_KR;
    }

    public void setTAG_KR(String TAG_KR) {
        this.TAG_KR = TAG_KR;
    }

    public String getTAG_EN() {
        return TAG_EN;
    }

    public void setTAG_EN(String TAG_EN) {
        this.TAG_EN = TAG_EN;
    }
}
