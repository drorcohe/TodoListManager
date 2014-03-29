package il.ac.huji.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDBHelper extends SQLiteOpenHelper{
	public static final String TABLE_NAME = TodoDAL.TABLE_NAME;
	public static final String DB_NAME = "todo_db";
	private static final String TITLE_COL = TodoDAL.TITLE_COL;
	private static final String DUE_DATE_COL =  TodoDAL.DUE_DATE_COL;
	private static final String ID_COL = TodoDAL.ID_COL;	
	public TodoDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL("create table " + TABLE_NAME + " ( " +
	    	      "  " + ID_COL + " integer primary key autoincrement, " +
	    	      "  " + TITLE_COL + " string, " +
	    	      "  " + DUE_DATE_COL + " long );");

		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
	}

}
