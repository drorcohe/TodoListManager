package il.ac.huji.todolist;

import il.ac.huji.todolist.CostumArrayAdapter.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class TodoDAL {
	Context context;
	public static final String TABLE_NAME = "todo";
	public static final String TITLE_COL = "title";
	public static final String DUE_DATE_COL = "due";
	public static final String ID_COL = "_id";
	TodoDBHelper helper;
	SQLiteDatabase db;

	//whenever this parse is true, the parse server will be updated as well
	private boolean UPDATE_PARSE = false;
	
	public TodoDAL(Context context) { 
		this.context=context;
		helper = new TodoDBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public void setUpdateParse(boolean newVal){
		UPDATE_PARSE = newVal;
	}
	public boolean getUpdateParse(){
		return UPDATE_PARSE;
	}
	
	
	

	public boolean insert(Item todoItem) {
		if(UPDATE_PARSE){
			//parse
			ParseObject parse = new ParseObject(TABLE_NAME);
			parse.put(TITLE_COL, todoItem.getTitle());
			parse.put(DUE_DATE_COL, todoItem.getDueDate().getTime());
			parse.saveInBackground();
		}
		
		//SQLite
		ContentValues todoContentValue = new ContentValues();
		todoContentValue.put(TITLE_COL, todoItem.getTitle());
		todoContentValue.put(DUE_DATE_COL,todoItem.getDueDate().getTime());
		db.insert(TABLE_NAME, null, todoContentValue);
		return true;
	}

	public void delete(Item todoItem) {
		if(UPDATE_PARSE){
			ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
			query.whereMatches(TITLE_COL, todoItem.getTitle());
			query.whereEqualTo(DUE_DATE_COL, todoItem.getDueDate().getTime());
			query.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> objects, ParseException e) {
					if (e == null) {
						//assumes that there is only one object with such name and date.
						if(objects.size()==0){
							Log.e("Parse", "failed to delete an item.");
							return;
						}
						ParseObject delRow = objects.get(0);
						delRow.deleteEventually();
					} else {
						Log.e("Parse error", "failed to delete an item.");
					}
				}
			});
		}
		
		Cursor cursor =  db.query(TABLE_NAME, new String[] {ID_COL,TITLE_COL,DUE_DATE_COL},
				TITLE_COL + "=?", new String[] { todoItem.getTitle() },
				null, null, ID_COL + " desc");
		if (cursor.moveToFirst()) {
			do {
				Date tempDueDate = new Date(cursor.getLong(2));
				String tempTitle = cursor.getString(1);
				Item tempItem = new Item(tempTitle,tempDueDate);
				if(tempItem.isEqual(todoItem)){
					String whereClause = ID_COL+"=?";
					String[]whereArgs = new String[] {String.valueOf(cursor.getInt(0))};
					db.delete(TABLE_NAME, whereClause , whereArgs);
					return;
				}

			} while (cursor.moveToNext());
		}

	}

	public enum DB_TYPE{
		PARSE,SQLITE;
	}

	public ArrayList<Item> all(DB_TYPE type) {
		final ArrayList<Item> ret = new ArrayList<Item>();
		if(type==DB_TYPE.PARSE){
			ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(TABLE_NAME);
			List<ParseObject> objects;
			try {
				objects = query.find();
				if(objects!=null && objects.size()>0){
					for(ParseObject p : objects){
						String title = (String)p.get(TITLE_COL);
						Date date = new Date((Long)p.get(DUE_DATE_COL));
						Item nextItem = new Item(title,date);
						ret.add(nextItem);
					}
				}
			} catch (ParseException e) {
				Log.e("Parse error", "failed to retreive items from parse server.");
			}

		}else{
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME,null);
			if (cursor .moveToFirst()) {
				do{
					String title = cursor.getString(cursor.getColumnIndex(TITLE_COL));
					Date dueDate = new Date(cursor.getLong(cursor.getColumnIndex(DUE_DATE_COL)));
					Item newItem = new Item(title,dueDate);
					ret.add(newItem);
					
				}while (cursor.moveToNext());

			}
		}
		return ret;
	}
}