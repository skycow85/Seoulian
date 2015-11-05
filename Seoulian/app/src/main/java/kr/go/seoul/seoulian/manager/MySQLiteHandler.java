package kr.go.seoul.seoulian.manager;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import kr.go.seoul.seoulian.data.SpotInfo;

public class MySQLiteHandler {
    private static MySQLiteHandler mInstance;
	private static MySQLiteOpenHelper helper;
	private static SQLiteDatabase db;
	
	private MySQLiteHandler(Context ctx){
		helper=new MySQLiteOpenHelper(ctx, "spot.sqlite", null, 1);
	}
	
	public static MySQLiteHandler open(Context ctx){
        if(mInstance==null){
            mInstance = new MySQLiteHandler(ctx);
        }
		return mInstance;
		
	}
	
	public void insert(SpotInfo seoulLife){

		ContentValues value=new ContentValues();
		value.put("CORP_SEQ", seoulLife.getCORP_SEQ());
        value.put("CATEGORY_CD", seoulLife.getCATEGORY_CD());
        value.put("CATEGORY_NM_KR", seoulLife.getCATEGORY_NM_KR());
        value.put("CATEGORY_NM_EN", seoulLife.getCATEGORY_NM_EN());
        value.put("SECTION_CD", seoulLife.getSECTION_CD());
        value.put("SECTION_NM_KR", seoulLife.getSECTION_NM_KR());
        value.put("SECTION_NM_EN", seoulLife.getSECTION_NM_EN());
        value.put("CONTENTS_CD", seoulLife.getCONTENTS_CD());
        value.put("CONTENTS_NM_KR", seoulLife.getCONTENTS_NM_KR());
        value.put("CONTENTS_NM_EN", seoulLife.getCONTENTS_NM_EN());
        value.put("D_CORP_KR_NM", seoulLife.getD_CORP_KR_NM());
        value.put("D_CORP_EN_NM", seoulLife.getD_CORP_EN_NM());
        value.put("INFO_CORP_KR_INTRO", seoulLife.getINFO_CORP_KR_INTRO());
        value.put("INFO_CORP_EN_INTRO", seoulLife.getINFO_CORP_EN_INTRO());
        value.put("URL", seoulLife.getURL());
        value.put("PHONE1", seoulLife.getPHONE1());
        value.put("PHONE2", seoulLife.getPHONE2());
        value.put("EMAIL1", seoulLife.getEMAIL1());
        value.put("ADDR_KR", seoulLife.getADDR_KR());
        value.put("ADDR_EN", seoulLife.getADDR_EN());
        value.put("TAG_KR", seoulLife.getTAG_KR());
        value.put("TAG_EN", seoulLife.getTAG_EN());

				
		db=helper.getWritableDatabase();
		db.insert("spot", null, value);
		
	}
	
	public void update(String corp_seq,String col ,String oldName,String newName){
		ContentValues value=new ContentValues();
		value.put(col, newName);
		
		db=helper.getWritableDatabase();
		db.update("spot", value, "_code_srq=? and "+col+"=?",
				new String[]{corp_seq, oldName});
	}
	
	public void update(String corp_seq, SpotInfo newName){
		ContentValues value=new ContentValues();
        value.put("CORP_SEQ", newName.getCORP_SEQ());
        value.put("CATEGORY_CD", newName.getCATEGORY_CD());
        value.put("CATEGORY_NM_KR", newName.getCATEGORY_NM_KR());
        value.put("CATEGORY_NM_EN", newName.getCATEGORY_NM_EN());
        value.put("SECTION_CD", newName.getSECTION_CD());
        value.put("SECTION_NM_KR", newName.getSECTION_NM_KR());
        value.put("SECTION_NM_EN", newName.getSECTION_NM_EN());
        value.put("CONTENTS_CD", newName.getCONTENTS_CD());
        value.put("CONTENTS_NM_KR", newName.getCONTENTS_NM_KR());
        value.put("CONTENTS_NM_EN", newName.getCONTENTS_NM_EN());
        value.put("D_CORP_KR_NM", newName.getD_CORP_KR_NM());
        value.put("D_CORP_EN_NM", newName.getD_CORP_EN_NM());
        value.put("INFO_CORP_KR_INTRO", newName.getINFO_CORP_KR_INTRO());
        value.put("INFO_CORP_EN_INTRO", newName.getINFO_CORP_EN_INTRO());
        value.put("URL", newName.getURL());
        value.put("PHONE1", newName.getPHONE1());
        value.put("PHONE2", newName.getPHONE2());
        value.put("EMAIL1", newName.getEMAIL1());
        value.put("ADDR_KR", newName.getADDR_KR());
        value.put("ADDR_EN", newName.getADDR_EN());
        value.put("TAG_KR", newName.getTAG_KR());
        value.put("TAG_EN", newName.getTAG_EN());
		
		db=helper.getWritableDatabase();
		db.update("spot", value, "corp_seq=?",
				new String[]{corp_seq});
	}
	
	public void delete(String corp_seq){
		db=helper.getWritableDatabase();
		db.delete("spot", "corp_seq=?", new String[]{""+corp_seq});
	}
	
	public ArrayList<SpotInfo>  select(){
		ArrayList<SpotInfo> data=new ArrayList<SpotInfo>();

		db=helper.getReadableDatabase();
		Cursor c=db.query("spot", null, null, null, null, null, null);
		
		while(c.moveToNext()){
			String CORP_SEQ=c.getString(c.getColumnIndex("CORP_SEQ"));
            String CATEGORY_CD=c.getString(c.getColumnIndex("CATEGORY_CD"));
            String CATEGORY_NM_KR=c.getString(c.getColumnIndex("CATEGORY_NM_KR"));
            String CATEGORY_NM_EN=c.getString(c.getColumnIndex("CATEGORY_NM_EN"));
            String SECTION_CD=c.getString(c.getColumnIndex("SECTION_CD"));
            String SECTION_NM_KR=c.getString(c.getColumnIndex("SECTION_NM_KR"));
            String SECTION_NM_EN=c.getString(c.getColumnIndex("SECTION_NM_EN"));
            String CONTENTS_CD=c.getString(c.getColumnIndex("CONTENTS_CD"));
            String CONTENTS_NM_KR=c.getString(c.getColumnIndex("CONTENTS_NM_KR"));


            String CONTENTS_NM_EN=c.getString(c.getColumnIndex("CONTENTS_NM_EN"));
            String D_CORP_KR_NM=c.getString(c.getColumnIndex("D_CORP_KR_NM"));
            String D_CORP_EN_NM=c.getString(c.getColumnIndex("D_CORP_EN_NM"));
            String INFO_CORP_KR_INTRO=c.getString(c.getColumnIndex("INFO_CORP_KR_INTRO"));
            String INFO_CORP_EN_INTRO=c.getString(c.getColumnIndex("INFO_CORP_EN_INTRO"));
            String URL=c.getString(c.getColumnIndex("URL"));
            String PHONE1=c.getString(c.getColumnIndex("PHONE1"));
            String PHONE2=c.getString(c.getColumnIndex("PHONE2"));

            String EMAIL1=c.getString(c.getColumnIndex("EMAIL1"));
            String ADDR_KR=c.getString(c.getColumnIndex("ADDR_KR"));
            String ADDR_EN=c.getString(c.getColumnIndex("ADDR_EN"));
            String TAG_KR=c.getString(c.getColumnIndex("TAG_KR"));
            String TAG_EN=c.getString(c.getColumnIndex("TAG_EN"));

			data.add(new SpotInfo( CORP_SEQ,
                     CATEGORY_CD,
                     CATEGORY_NM_KR,
                     CATEGORY_NM_EN,
                     SECTION_CD,
                     SECTION_NM_KR,
                     SECTION_NM_EN,
                     CONTENTS_CD,
                     CONTENTS_NM_KR,
                     CONTENTS_NM_EN,
                     D_CORP_KR_NM,
                     D_CORP_EN_NM,
                     INFO_CORP_KR_INTRO,
                     INFO_CORP_EN_INTRO,
                     URL,
                     PHONE1,
                     PHONE2,
                     EMAIL1,
                     ADDR_KR,
                     ADDR_EN,
                     TAG_KR,
                     TAG_EN));
		}
		
	//	c.close();
		return data;
		
		
	}

    public ArrayList<SpotInfo>  select(int start, int count){
        ArrayList<SpotInfo> data=new ArrayList<SpotInfo>();

        db=helper.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM spot LIMIT "+start+", "+count+";", null);

        while(c.moveToNext()){
            String CORP_SEQ=c.getString(c.getColumnIndex("CORP_SEQ"));
            String CATEGORY_CD=c.getString(c.getColumnIndex("CATEGORY_CD"));
            String CATEGORY_NM_KR=c.getString(c.getColumnIndex("CATEGORY_NM_KR"));
            String CATEGORY_NM_EN=c.getString(c.getColumnIndex("CATEGORY_NM_EN"));
            String SECTION_CD=c.getString(c.getColumnIndex("SECTION_CD"));
            String SECTION_NM_KR=c.getString(c.getColumnIndex("SECTION_NM_KR"));
            String SECTION_NM_EN=c.getString(c.getColumnIndex("SECTION_NM_EN"));
            String CONTENTS_CD=c.getString(c.getColumnIndex("CONTENTS_CD"));
            String CONTENTS_NM_KR=c.getString(c.getColumnIndex("CONTENTS_NM_KR"));


            String CONTENTS_NM_EN=c.getString(c.getColumnIndex("CONTENTS_NM_EN"));
            String D_CORP_KR_NM=c.getString(c.getColumnIndex("D_CORP_KR_NM"));
            String D_CORP_EN_NM=c.getString(c.getColumnIndex("D_CORP_EN_NM"));
            String INFO_CORP_KR_INTRO=c.getString(c.getColumnIndex("INFO_CORP_KR_INTRO"));
            String INFO_CORP_EN_INTRO=c.getString(c.getColumnIndex("INFO_CORP_EN_INTRO"));
            String URL=c.getString(c.getColumnIndex("URL"));
            String PHONE1=c.getString(c.getColumnIndex("PHONE1"));
            String PHONE2=c.getString(c.getColumnIndex("PHONE2"));

            String EMAIL1=c.getString(c.getColumnIndex("EMAIL1"));
            String ADDR_KR=c.getString(c.getColumnIndex("ADDR_KR"));
            String ADDR_EN=c.getString(c.getColumnIndex("ADDR_EN"));
            String TAG_KR=c.getString(c.getColumnIndex("TAG_KR"));
            String TAG_EN=c.getString(c.getColumnIndex("TAG_EN"));

            data.add(new SpotInfo( CORP_SEQ,
                    CATEGORY_CD,
                    CATEGORY_NM_KR,
                    CATEGORY_NM_EN,
                    SECTION_CD,
                    SECTION_NM_KR,
                    SECTION_NM_EN,
                    CONTENTS_CD,
                    CONTENTS_NM_KR,
                    CONTENTS_NM_EN,
                    D_CORP_KR_NM,
                    D_CORP_EN_NM,
                    INFO_CORP_KR_INTRO,
                    INFO_CORP_EN_INTRO,
                    URL,
                    PHONE1,
                    PHONE2,
                    EMAIL1,
                    ADDR_KR,
                    ADDR_EN,
                    TAG_KR,
                    TAG_EN));
        }

        //	c.close();
        return data;


    }

    public SpotInfo select(String corp_seq){
        SpotInfo data = new SpotInfo();;

        db=helper.getReadableDatabase();
        try {


           Cursor c=db.rawQuery("SELECT * FROM spot WHERE CORP_SEQ = '"+corp_seq+"'",null);



            while(c.moveToNext()){

                data.setCORP_SEQ(c.getString(c.getColumnIndex("CORP_SEQ")));
                data.setCATEGORY_CD(c.getString(c.getColumnIndex("CATEGORY_CD")));
                data.setCATEGORY_NM_KR(c.getString(c.getColumnIndex("CATEGORY_NM_KR")));
                data.setCATEGORY_NM_EN(c.getString(c.getColumnIndex("CATEGORY_NM_EN")));
                data.setSECTION_CD(c.getString(c.getColumnIndex("SECTION_CD")));
                data.setSECTION_NM_KR(c.getString(c.getColumnIndex("SECTION_NM_KR")));
                data.setSECTION_NM_EN(c.getString(c.getColumnIndex("SECTION_NM_EN")));
                data.setCONTENTS_CD(c.getString(c.getColumnIndex("CONTENTS_CD")));
                data.setCONTENTS_NM_KR(c.getString(c.getColumnIndex("CONTENTS_NM_KR")));


                data.setCONTENTS_NM_EN(c.getString(c.getColumnIndex("CONTENTS_NM_EN")));
                data.setD_CORP_KR_NM(c.getString(c.getColumnIndex("D_CORP_KR_NM")));
                data.setD_CORP_EN_NM(c.getString(c.getColumnIndex("D_CORP_EN_NM")));
                data.setINFO_CORP_KR_INTRO(c.getString(c.getColumnIndex("INFO_CORP_KR_INTRO")));
                data.setINFO_CORP_EN_INTRO(c.getString(c.getColumnIndex("INFO_CORP_EN_INTRO")));
                data.setURL(c.getString(c.getColumnIndex("URL")));
                data.setPHONE1(c.getString(c.getColumnIndex("PHONE1")));
                data.setPHONE2(c.getString(c.getColumnIndex("PHONE2")));

                data.setEMAIL1(c.getString(c.getColumnIndex("EMAIL1")));
                data.setADDR_KR(c.getString(c.getColumnIndex("ADDR_KR")));
                data.setADDR_EN(c.getString(c.getColumnIndex("ADDR_EN")));
                data.setTAG_KR(c.getString(c.getColumnIndex("TAG_KR")));
                data.setTAG_EN(c.getString(c.getColumnIndex("TAG_EN")));


                }

        }catch (Exception e){

        }

        //	c.close();
        return data;


    }
	public void close(){
		helper.close();
	}

}
