package il.ac.huji.todolist;

import il.ac.huji.todolist.CostumArrayAdapter.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
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

public class TodoListManagerActivity extends Activity {
	static public CostumArrayAdapter adapter;
	static public final ArrayList<Item> todoList = new ArrayList<Item>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ListView listView =(ListView)findViewById(R.id.lstTodoItems);
		registerForContextMenu(listView);

	    adapter = new CostumArrayAdapter(this,android.R.layout.simple_list_item_1, todoList);
	    listView.setAdapter(adapter);
	}

	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		ListView listView = (ListView)v;
		AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		Item adapterItem = (Item)listView.getItemAtPosition(adapterContextMenuInfo.position);
		menu.setHeaderTitle(adapterItem.getRep());  
		MenuInflater inflater = getMenuInflater();
		
		
		inflater.inflate(R.menu.context_item_menu, menu);
		
		MenuItem callItem = menu.findItem(R.id.menuItemCall);
		if(adapterItem.isCallItem()){
			callItem.setVisible(true);
			callItem.setTitle(adapterItem.getRep());
		}
		else{
			callItem.setVisible(false);
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	    Item adapterItem = adapter.getItem(info.position);
	    
	    if(item.getItemId()==R.id.menuItemCall){
	    	Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + adapterItem.getPhoneNumberStr())); 
	    	startActivity(dial);
	    }
	    else if(item.getItemId()==R.id.menuItemDelete){
		    todoList.remove(adapterItem);
		    adapter.notifyDataSetChanged();
	    }

		return true;
	}
	
	static int ADD_ITEM_REQUEST_CODE = 1;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
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
				//	Calendar calendar = Calendar.getInstance();
		    	    TodoListManagerActivity.todoList.add(new Item(title,dueDate));
		    	    TodoListManagerActivity.adapter.notifyDataSetChanged();
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
