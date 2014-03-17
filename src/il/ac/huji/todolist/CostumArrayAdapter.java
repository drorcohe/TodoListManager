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
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private Context context;
	
	public static class Item{
		private final String rep;
		private final Date date;
		private boolean isCallItem = false;
		private String phoneNumberStr = "";
		public Item(String rep, Date date){
			this.rep = rep;
			this.date = date;
			if(rep.toLowerCase().startsWith("call ")||rep.toLowerCase().startsWith("call\t")){
				this.isCallItem = true;
				this.phoneNumberStr = rep.split("\\s+")[1];
			}
			
		}
		
		public boolean isCallItem(){
			return this.isCallItem;
		}
		
		public String getPhoneNumberStr(){
			return this.phoneNumberStr;
		}
		
		public String getRep(){
			return this.rep;
		}
		
		public Date getDate(){
			return this.date;
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
		 labelTv.setText(super.getItem(position).getRep());
		 Date dueDate = super.getItem(position).getDate();
		 String dueDateStr = context.getResources().getString(R.string.noDueDate);
		 if(dueDate!=null){
			 dueDateStr = dateFormat.format(super.getItem(position).getDate().getTime());
		 }
		 TextView dueDateTv=(TextView)row.findViewById(R.id.txtTodoDueDate);
		 dueDateTv.setText(dueDateStr);
		 
		 if(dueDate!=null){
			 Calendar yesterday = Calendar.getInstance();
			 yesterday.add(Calendar.DAY_OF_YEAR, -1);
			 
			 if(dueDate.before(yesterday.getTime())){
				 labelTv.setTextColor(Color.RED);
				 dueDateTv.setTextColor(Color.RED);
			 }
		 }

		 
		 return row;
	}
	
	
}