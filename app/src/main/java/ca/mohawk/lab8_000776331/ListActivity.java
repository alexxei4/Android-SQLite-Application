package ca.mohawk.lab8_000776331;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {
    public static final String TAG = "==ListActivity==";

    // Create a global instance of our SQL Helper class
    MyDbHelper mydbhelp = new MyDbHelper(this);
    /**
     * onCreate - get an instance of our database, use a cursor to display the values
     * @param savedInstanceState (default)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.d(TAG, "onCreate");
        // Get an instance of the database using our helper class
        SQLiteDatabase db = mydbhelp.getReadableDatabase();
        // A projection defines what fields we want to retrieve.
        String[] projection = { MyDbHelper.ID, MyDbHelper.PRODUCTNAME, MyDbHelper.SERIALNUMBER};
        // db.query will retreive the data and return a Cursor to access it
        Cursor mycursor = db.query(MyDbHelper.MYTABLE, projection, null,
                null, null, null, null);
        String results = "";
        String array[] = new String[]{};
        String tempstring = "";
        ArrayList<String> myValueList = new ArrayList<String>();
        int i = 0;
        if (mycursor != null) {
            // Loop through our returned results from the start
            while (mycursor.moveToNext()) {
                Log.d(TAG, "found DB item");
                String productname = mycursor.getString(
                        mycursor.getColumnIndex(MyDbHelper.PRODUCTNAME));
                String serialnumber = mycursor.getString(
                        mycursor.getColumnIndex(MyDbHelper.SERIALNUMBER));
                long itemID = mycursor.getLong(
                        mycursor.getColumnIndex(MyDbHelper.ID));

                results += itemID + " " + productname + " " + serialnumber + "\n";

                i +=1 ;// XXX hackish
                myValueList.add(itemID + " " + productname + " " + serialnumber);



            }
            // Close the cursor when we're done
            mycursor.close();
        }
        if (results == "") {
            results = "!! no data !!";
        }
        // Show our results
      //  TextView output = (TextView) findViewById(R.id.outputtext);
       // output.setText(results);
        ArrayAdapter<String> myAdapter= new ArrayAdapter(ListActivity.this, android.R.layout.simple_list_item_1,myValueList);
        ListView MyListView = findViewById(R.id.mylistt);

        MyListView.setAdapter(myAdapter);// XXX TODO fix this
        MyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long id) {
               Toast.makeText(ListActivity.this,myValueList.get(position),Toast.LENGTH_SHORT).show();

            }
        });
    }

}