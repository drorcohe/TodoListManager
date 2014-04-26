package il.ac.huji.todolist;

import il.ac.huji.todolist.CostumArrayAdapter.Item;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class LoadAllTodos extends AsyncTask<Void, ArrayList<Item>, Void>{

	private ArrayList<Item> todoList;
	private CostumArrayAdapter adapter;
	private Context context;
	TodoDBHelper helper;
	SQLiteDatabase db;

	public LoadAllTodos(ArrayList<Item> todoList, CostumArrayAdapter adapter, Context context) {
		this.todoList = todoList;
		this.adapter = adapter;
		this.context=context;
		helper = new TodoDBHelper(context);
		db = helper.getWritableDatabase();
	}

	@Override
	protected Void doInBackground(Void... params) {
		final int NUM_OF_ITEMS_PER_UPDATE = 2;
		Cursor cursor = db.rawQuery("select * from " + TodoDAL.TABLE_NAME,null);
		int counter = 0;
		ArrayList<Item> itemsList = new ArrayList<Item>();
		if (cursor .moveToFirst()) {
			do{
				counter++;
				String title = cursor.getString(cursor.getColumnIndex(TodoDAL.TITLE_COL));
				Date dueDate = new Date(cursor.getLong(cursor.getColumnIndex(TodoDAL.DUE_DATE_COL)));
				Item newItem = new Item(title,dueDate);
				itemsList.add(newItem);
				if(counter%NUM_OF_ITEMS_PER_UPDATE==0){
					counter = 0;
					publishProgress(itemsList);
					itemsList = new ArrayList<Item>();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {}
				}
			}while (cursor.moveToNext());
			
		}
		if(itemsList.size()>0){
			publishProgress(itemsList);
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(ArrayList<Item>... items) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(items);
		for(Item item: items[0]){
			todoList.add(item); 
		}

		TodoListManagerActivity.adapter.notifyDataSetChanged();
	}


	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}




}
