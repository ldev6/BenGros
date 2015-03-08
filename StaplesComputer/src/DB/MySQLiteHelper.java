package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{
	public static final String TABLE_PLAN_ORDI_BUREAU = "PlanOrdiBureau";
	public static final String TABLE_PLAN_ORDI_PORTATIF = "PlanPortatifs";
	public static final String TABLE_PLAN_RECUP = "PlanRecup";
	public static final String TABLE_ORDI_CONFIG = "Configuration";
	
	public static final String DATABASE_NAME = "staplesCalculater.db";
	
	
	private String createTablePLAN( String TABLE){
		String database_create_table = "create table" + TABLE + "( _ID" +
				"integer primary key autoincrement, "+"NAME"+ " text not null, " +"PRIX, "+"SKU, "+"YEAR);";
		return database_create_table;
	}
	
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(createTablePLAN(TABLE_PLAN_ORDI_BUREAU));
		database.execSQL(createTablePLAN(TABLE_PLAN_ORDI_PORTATIF));
		database.execSQL(createTablePLAN(TABLE_PLAN_RECUP));
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
