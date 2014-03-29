package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CostumArrayAdapter extends ArrayAdapter<CostumArrayAdapter.Item>{
	@SuppressLint("SimpleDateFormat")
	
	private Context context;
	
	public static class Item{
		@SuppressLint("SimpleDateFormat")
		private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		private final String title;
		private final Date date;
		public Item(String rep, Date date){
			this.title = rep;
			this.date = date;
			
		}
		
		public static boolean isCallItem(Item item){
			return (item.getTitle().toLowerCase().startsWith("call ")||item.title.toLowerCase().startsWith("call\t"));
		}
		
		public static String getPhoneNumberStr(Item item){
			return item.getTitle().split("\\s+")[1];
		}
		
		public String getTitle(){
			return this.title;
		}
		
		public Date getDueDate(){
			return this.date;
		}
		
		public boolean isEqual(Item other){
			return (this.getTitle().equals(other.getTitle()) && this.getDueDate().getTime()==other.getDueDate().getTime());
		}
		
		public String getDueDateStr(){
			 String dueDateStr = "no due date";
			 if(this.date!=null){
				 dueDateStr = dateFormat.format(this.getDueDate().getTime());
			 }
			 return dueDateStr;
		}
	}
	
	public CostumArrayAdapter(Context context, int resource, ArrayList<CostumArrayAdapter.Item> list) {
		super(context, resource,list);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;

		 if(row==null){
		  LayoutInflater inflater=LayoutInflater.from(super.getContext());
		  row=inflater.inflate(R.layout.row, parent, false);
		 
		 }
		 
		 TextView labelTv=(TextView)row.findViewById(R.id.txtTodoTitle);
		 labelTv.setText(super.getItem(position).getTitle());
		 Date dueDate = super.getItem(position).getDueDate();
		 String dueDateStr = super.getItem(position).getDueDateStr();
		 TextView dueDateTv=(TextView)row.findViewById(R.id.txtTodoDueDate);
		 dueDateTv.setText(dueDateStr);
		 
		 if(dueDate!=null){
			 Calendar yesterday = Calendar.getInstance();
			 yesterday.add(Calendar.DAY_OF_YEAR, -1); 
			 if(dueDate.before(yesterday.getTime())){
				 labelTv.setTextColor(Color.RED);
				 dueDateTv.setTextColor(Color.RED);
			 }else{
				 labelTv.setTextColor(Color.BLACK);
				 dueDateTv.setTextColor(Color.BLACK);
			 }
		 }

		 
		 return row;
	}
	
	
}