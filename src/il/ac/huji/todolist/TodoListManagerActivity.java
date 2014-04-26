package il.ac.huji.todolist;

import il.ac.huji.todolist.CostumArrayAdapter.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;

public class TodoListManagerActivity extends Activity {
	static public CostumArrayAdapter adapter;
	static public ArrayList<Item> todoList;
	TodoDAL todoDal;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		todoDal = new TodoDAL(getBaseContext());
		final ListView listView =(ListView)findViewById(R.id.lstTodoItems);
		registerForContextMenu(listView);

		todoList = new ArrayList<Item>();//todoDal.all(TodoDAL.DB_TYPE.SQLITE);
	    adapter = new CostumArrayAdapter(this,android.R.layout.simple_list_item_1, todoList);
	    listView.setAdapter(adapter);
	    
	    new LoadAllTodos(todoList,adapter,getBaseContext() ).execute();
	}

	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		ListView listView = (ListView)v;
		AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		Item adapterItem = (Item)listView.getItemAtPosition(adapterContextMenuInfo.position);
		menu.setHeaderTitle(adapterItem.getTitle());  
		MenuInflater inflater = getMenuInflater();
		
		
		inflater.inflate(R.menu.context_item_menu, menu);
		
		MenuItem callItem = menu.findItem(R.id.menuItemCall);
		if(Item.isCallItem(adapterItem)){
			callItem.setVisible(true);
			callItem.setTitle(adapterItem.getTitle());
		}
		else{
			callItem.setVisible(false);
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	    Item todoItem = adapter.getItem(info.position);
	    
	    if(item.getItemId()==R.id.menuItemCall){
	    	Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Item.getPhoneNumberStr(todoItem))); 
	    	startActivity(dial);
	    }
	    else if(item.getItemId()==R.id.menuItemDelete){
		    todoList.remove(todoItem);
		    adapter.notifyDataSetChanged();
		    todoDal.delete(todoItem);
	    }

		return true;
	}
	
	static int ADD_ITEM_REQUEST_CODE = 1;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.menuItemAdd:
	        	Intent intent = new Intent(this,AddNewTodoItemActivity.class);
	        	//startActivity(intent);
	        	startActivityForResult(intent,ADD_ITEM_REQUEST_CODE);
	        	

	            	
	            return true;
	        default:
	            return false; //super.onOptionsItemSelected(item);
		}
	}
	
	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(resultCode != RESULT_CANCELED){
	        // check if the request code is same as what is passed  here it is 2
			if(requestCode==ADD_ITEM_REQUEST_CODE){
				if((Boolean)data.getExtras().get("isValid")){
					String title = (String) data.getExtras().get("title");
					Date dueDate = (Date) data.getExtras().get("dueDate");
					Item addedTodoItem = new Item(title,dueDate);
		    	    TodoListManagerActivity.todoList.add(addedTodoItem);
		    	    TodoListManagerActivity.adapter.notifyDataSetChanged();
		    	    todoDal.insert(addedTodoItem);
				}
			}
		
		}
            
  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
