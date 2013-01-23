package ash.win.call.log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.ListActivity;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CallLogActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface DinDisplayProLight = Typeface.createFromAsset(getAssets(), "fonts/DinDisplayProLight.otf");
        //String callLogName = CallLog.;
        ArrayList<String> calllogs = new ArrayList<String>();
        calllogs = fetchCallLogs(this);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, calllogs));
        /*TextView tv = new TextView(this);
        tv.setText("hello ashwin");*/
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
              // When clicked, show a toast with the TextView text
              Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                  Toast.LENGTH_SHORT).show();
            }
          });
        //setContentView(lv);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true; 
    }
    /*
    public boolean onOptionsItemSelected(MenuItem item){
    	return true;
    }*/
    public static ArrayList<String> fetchCallLogs(Context context){
    	Cursor cursor = context.getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI, 
    			null, null, null, android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);
    	int numberColumn = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
    	int nameColumn = cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
    	int numberType = cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NUMBER_TYPE);
    	int dateColumn = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
    	int typeColumn = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
    	ArrayList<String> callList = new ArrayList<String>();
    	Set<String> callSet = new HashSet<String>();
    	int count = cursor.getCount();
    	if(count>0){
    		cursor.moveToFirst();
    		do{
    			String callerPhoneNumber = cursor.getString(numberColumn);
    			String callerName = cursor.getString(nameColumn);
    			String callerNumberType = cursor.getString(numberType);
    			int callType = cursor.getInt(typeColumn);
    			callSet.add((callerName != null) ? callerName : callerPhoneNumber);
    		} while(cursor.moveToNext());
    	}
    	callList.addAll(callSet);
    	return callList;
    }
}