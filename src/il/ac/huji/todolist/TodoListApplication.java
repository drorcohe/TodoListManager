package il.ac.huji.todolist;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;

public class TodoListApplication extends Application{
	private static TodoListApplication singleton;
		
		public TodoListApplication getInstance(){
			return singleton;
		}
		@Override
		public void onCreate() {
			super.onCreate();
			singleton = this;
			Parse.initialize(this, "AfbBTbanB7yezapJR6vOr84gozdfc3lj5hd9hK7S", "LDDcB3xPnQbsPrmIksREaqJeg9O7h8Nbkf40HSd8");
			ParseUser.enableAutomaticUser();
		
		}
}
