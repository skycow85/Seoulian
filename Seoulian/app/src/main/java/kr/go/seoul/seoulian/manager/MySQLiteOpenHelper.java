package kr.go.seoul.seoulian.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String sql="create table spot ("+
				"_id integer primary key,"+
				"CORP_SEQ text," +
                "CATEGORY_CD text," +
                "CATEGORY_NM_KR text," +
                "CATEGORY_NM_EN text," +
                "SECTION_CD text," +
                "SECTION_NM_KR text," +
                "SECTION_NM_EN text," +
                "CONTENTS_CD text," +
                "CONTENTS_NM_KR text," +
                "CONTENTS_NM_EN text," +
                "D_CORP_KR_NM text," +
                "D_CORP_EN_NM text," +
                "INFO_CORP_KR_INTRO text," +
                "INFO_CORP_EN_INTRO text," +
                "URL text," +
                "PHONE1 text," +
                "PHONE2 text," +
                "EMAIL1 text," +
                "ADDR_KR text," +
                "ADDR_EN text," +
                "TAG_KR text," +
                "TAG_EN text" +
                ")";
		
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql="drop table if exists spot";
		db.execSQL(sql);
		
		onCreate(db);
		
	}

}
