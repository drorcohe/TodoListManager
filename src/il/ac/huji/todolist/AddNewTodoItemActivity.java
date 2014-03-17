package il.ac.huji.todolist;




import java.util.Calendar;
import java.util.Date;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTodoItemActivity extends Activity{
	public void onCreate(Bundle unused) { 
		 super.onCreate(unused); 
		 setContentView(R.layout.activity_add); 
		 setTitle(R.string.menuAddTitle);
		 final Button okButton = (Button)findViewById(R.id.btnOK);
		 final Button cancelButton = (Button)findViewById(R.id.btnCancel);
		 
		 

		 cancelButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent();
					intent.putExtra("isValid",false);
					setResult(TodoListManagerActivity.ADD_ITEM_REQUEST_CODE,intent);
					finish();
				}
	       });
		 
		 okButton.setOnClickListener(new OnClickListener() {
			 final EditText editNewItem = (EditText)findViewById(R.id.edtNewItem);
			 final DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View arg0) {
					String inputStr = editNewItem.getText().toString();
		            if(!inputStr.equals("")){
		            	Intent intent = new Intent();
		            	intent.putExtra("title",inputStr);
		            	datePicker.getDayOfMonth();
		            	Calendar cal = Calendar.getInstance();
		            	cal.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
		            	Date d = cal.getTime();
		            	intent.putExtra("dueDate",d);
		            	intent.putExtra("isValid",true);
		            	setResult(TodoListManagerActivity.ADD_ITEM_REQUEST_CODE,intent);
		        	    finish();
		            }
				}
	       });
	}

	
}
