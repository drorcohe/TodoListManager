package il.ac.huji.todolist;

import java.util.ArrayList;
import il.ac.huji.todolist.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CostumArrayAdapter extends ArrayAdapter<String>{
	public CostumArrayAdapter(Context context, int resource, ArrayList<String> list) {
		super(context, resource,list);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;

		 if(row==null){
		  LayoutInflater inflater=LayoutInflater.from(super.getContext());
		  row=inflater.inflate(R.layout.row, parent, false);
		 }
		 
		 TextView label=(TextView)row.findViewById(R.id.rowText);
		 label.setText(super.getItem(position));

		 if(position%2==0)
			 label.setTextColor(Color.RED);
		 else
			 label.setTextColor(Color.BLUE);
		 
		 return row;
	}
	
	
}