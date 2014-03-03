package il.ac.huji.todolist;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {
	static CostumArrayAdapter adapter;
	static final ArrayList<String> todoList = new ArrayList<String>();
	
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
		String title = listView.getItemAtPosition(adapterContextMenuInfo.position).toString();
		menu.setHeaderTitle(title);  
//	    menu.add(0, v.getId(), 0, "Delete Item");  
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_item_menu, menu);

	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
	    String itemStrRep = adapter.getItem(info.position);
	    todoList.remove(itemStrRep);
	    adapter.notifyDataSetChanged();
	    
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
	        case R.id.menuItemAdd:
	            EditText editText = (EditText)findViewById(R.id.edtNewItem);
	            String inputStr = editText.getText().toString();
	            editText.setText(new String());
	            if(!inputStr.equals("")){
	        	    todoList.add(inputStr);
	        	    adapter.notifyDataSetChanged();
	            }
	            	
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
