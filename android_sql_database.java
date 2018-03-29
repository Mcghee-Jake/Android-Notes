// First we are going to create the contract file

// Create a folder called 'data' and create a new class within that folder called '[yourDatabaseTitle]Contract'
// This will contain all your table names and column names
public class myContract {
	// Create an inner class that implements the BaseColumns interface
	public static final class myTable implements BaseColumns {
		public static final String TABLE_NAME = "myTable";
		// Populate the contract with the names of the table columns
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_NUMBER = "number";
		public static final String COLUMN_TIMESTAMP = "timestamp";
	}
}

// Create a database helper class within the data folder that extends SQLiteOpenHelper
// This will be used to actually initialize the tables within the SQL database
import com.example.android.appName.data.myContract.*;

public class myDbHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "myDatabase.db";
	private static final int DATABASE_VERSION = 1; // Note: If you change the database schema, you must increment the database version
	
	// Create a constructor that takes a context and calls the parent constructor
	public dbHelper(Context context) {
		super(context, DATABAS_NAME, null, DATABASE_VERSION);
	}
	
	// Override onCreate
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		// Create an sql query string that will create the table
		final String SQL_CREATE_TABLE = "CREATE TABLE " + myTable.TABLE_NAME + " (" +
			myTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			myTable.COLUMN_NAME + " TEXT NOT NULL, " +
			myTable.COLUMN_NUMBER + " INTEGER NOT NULL, " +
			myTable.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
			"):";
			
		// Execute the query
		sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
	}
	
	// Override the onUpgrade method
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
		
        // Inside, execute a drop table query, and then call onCreate to re-create it
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + myTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
	
}

// Create a class within the data folder to load in dummy data (optional)
public class TestUtil {
	
	
	
	
	 public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
		
        //create a list of fake data
        List<ContentValues> list = new ArrayList<ContentValues>();
		
		ContentValues cv = new ContentValues();
        cv.put(myContract.myTable.COLUMN_NAME, "John");
        cv.put(myContract.myTable.COLUMN_NUMBER, 12);
        list.add(cv);
		
		ContentValues cv = new ContentValues();
        cv.put(myContract.myTable.COLUMN_NAME, "Sophie");
        cv.put(myContract.myTable.COLUMN_NUMBER, 42);
        list.add(cv);
		
		ContentValues cv = new ContentValues();
        cv.put(myContract.myTable.COLUMN_NAME, "Larry");
        cv.put(myContract.myTable.COLUMN_NUMBER, 17);
        list.add(cv);
		
		//insert all guests in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (myContract.myTable.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                db.insert(myContract.myTable.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
     
        }
        finally
        {
            db.endTransaction();
        }
	 }
	
}


// First, make an xml layout for the data items you want to display
class myListItem.xml { 
	// Just a simple example
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:gravity="center_vertical"
    android:orientation="vertical">

    <TextView
        android:id="@+id/tv_number"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_gravity="center"
        android:background="@drawable/circle"
        android:fontFamily="sans-serif"
        android:gravity="center"
        android:textColor="@android:color/white"
        android:textSize="16sp"
        android:text="@string/default_party_size"/>

    <TextView
        android:id="@+id/tv_name"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp"
        android:layout_weight="1"
        android:fontFamily="sans-serif"
        android:textSize="16sp"
        android:text="@string/default_guest_name"/>


</LinearLayout>
}

// Copied from RecyclerView notes and modified slightly
public class myAdpater extends RecyclerView.Adapter<myAdapter.myViewHolder> {
	
	
	// 1. Replace count with a cursor
	private Cursor mCursor;
	// 2. Add a variable for the context
	private Context mContext;
	
	// 3. And add the context and the cursor to the constructor
    public myAdapter(Context context, Cursor cursor) {
		this.mContext = context;
		this.mCursor = cursor;
        
    }
	
	
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.myListItem, viewGroup, false);
        return new myViewHolder(view);
    }

	
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
		// 5. Move the cursor to the passed in position of the item to be displayed, return if moveToPosition returns false
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null
		// 6. Call getString to get on the cursor to get the name
		String name = mCursor.getString(mCursor.getColumnIndex(myContract.myTable.COLUMN_NAME));
		// 7. Call getInt to get on the cursor to get the number
		int number = mCursor.getInt(mCursor.getColumnIndex(myContract.myTable.COLUMN_NUMBER));
		
		// 8. Set the text views to the relevant data
		holder.nameTextView.setText(name);
		holder.numTextView.setText(String.valueOf(number));
    }


    @Override
    public int getItemCount() {
		// 4. Replace with the cursor count
        return mCursor.getCount;
    }
	
	
	class myViewHolder extends RecyclerView.ViewHolder {
		
		TextView nameTextView;
		TextView numTextView;
		
		
	
		public myViewHolder(View itemView) {
			super(itemView);
			
			nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
			numTextView = (TextView) itemView.findViewById(R.id.tv_number);
		}
		
		
		
	}
	
	
}

// MainActivity copied and modified slightly from notes on RecyclerView
public class MainActivity extends AppCompatActivity {
	
	// 1. Declare a SQliteDatabase
	private SQLiteDatabase mDb;
	
	
	private myAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		RecyclerView mList = (RecyclerView) findViewById(R.id.rv_items);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mList.setLayoutManger(layoutManger);
		
		// 2. Create an instance of your database helper class
		myDbHelper dbHelper = new myDbHelper(this);
		
		// 3. Get a writable database reference
		mDb = dbHelper.getWritableDatabase();
		
		// 4. call insertFakeData
		TestUtil.insertFakeData(mDb);
		
		// 6. run getAllItems
		Cursor cursor = getAllItems();
		
		// 7. Create an adapter that passes in the context and the cursor data
		mAdapter = new GuestListAdapter(this, cursor);

		
		mList.setAdapter(mAdapter);
		
		 
	}
	
	// 5. Create a method that queries the database and returns a cursor
	private Cursor getAllItems() {
		return mDb.query(
			myContract.myTable.TABLE_NAME, // table name
			null,	// columns
			null,	// selection
			null,	// slectionArgs
			null,	// groupBy
			myContract.myTable.COLUMN_TIMESTAMP // order by
		);
	}
	

}

// Next, lets add the ability to add new items to the database
public class myAdpater extends RecyclerView.Adapter<myAdapter.myViewHolder> {
	
	
	private Cursor mCursor;
	private Context mContext;
	
    public myAdapter(Context context, Cursor cursor) {
		this.mContext = context;
		this.mCursor = cursor;
        
    }
	
	
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.myListItem, viewGroup, false);
        return new myViewHolder(view);
    }

	
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return; 
		String name = mCursor.getString(mCursor.getColumnIndex(myContract.myTable.COLUMN_NAME));
		int number = mCursor.getInt(mCursor.getColumnIndex(myContract.myTable.COLUMN_NUMBER));
		
		holder.nameTextView.setText(name);
		holder.numTextView.setText(String.valueOf(number));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount;
    }
	
	// Add updateCursor function
	public void updateCursor(Cursor newCursor) {
		if (mCursor != null) mCursor.close();
		mCursor = newCursor;
		if (newCursor != null) {
			this.notifyDataSetChanged(); // this forces the recyclerView to refresh
		}
	}
	
	class myViewHolder extends RecyclerView.ViewHolder {
		
		TextView nameTextView;
		TextView numTextView;
		
		
	
		public myViewHolder(View itemView) {
			super(itemView);
			
			nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
			numTextView = (TextView) itemView.findViewById(R.id.tv_number);
		}
		
		
		
	}
	
	
}

//Add edit text fields and a button to an activity
class activity_main.xml {
	<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="@dimen/activity_horizontal_margin"
    tools:context="com.example.android.waitlist.MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:id="@+id/name_edit_text"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"

            android:hint="@string/name_hint"
            android:inputType="textPersonName|textCapWords"
            android:textAppearance="@style/TextAppearance.AppCompat.Headline" />

        <EditText
            android:id="@+id/number_edit_text"
            android:layout_width="40dp"
            android:layout_height="wrap_content"
            android:contentDescription="@string/number_hint"
            android:gravity="center"
            android:inputType="number"
            android:maxLength="2"
            android:textAppearance="@style/TextAppearance.AppCompat.Headline" />
    </LinearLayout>


    <Button
        android:id="@+id/add_to_database_button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp"
        android:layout_marginTop="8dp"
        android:background="@color/colorPrimary"
        android:onClick="addToWaitlist"
        android:text="@string/add_button_text"
        android:textAppearance="@style/TextAppearance.AppCompat.Widget.Button.Inverse" />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_items"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>

	
}

public class MainActivity extends AppCompatActivity {
	
	private SQLiteDatabase mDb;
	private myAdapter mAdapter;
	// 1. Create local EditText fields
	private EditText mNameEditText;
	private EditText mNumberEditText;
	
	// 2. Add a LOG_TAG
	private final static String LOG_TAG = MainActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		// 3. Initialize the EditTexts
		mNameEditText = (EditText) this.findViewById(R.id.name_edit_text);
        mNumberEditText = (EditText) this.findViewById(R.id.number_edit_text);
		
		RecyclerView mList = (RecyclerView) findViewById(R.id.rv_items);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mList.setLayoutManger(layoutManger);
		
		myDbHelper dbHelper = new myDbHelper(this);
		mDb = dbHelper.getWritableDatabase();
		
		// 4. Remove the call to insert fake data if you added it
		
		Cursor cursor = getAllItems();
		mAdapter = new GuestListAdapter(this, cursor);
		mList.setAdapter(mAdapter);
		
		 
	}
	
	
	// 5. Create the addToDatabase method
	public void addToDatabase(View view) {
		// 6. Check if the editTexts are empty
		if (mNameEditText.getText.length() == 0 ||
			mNumberEditText.getText.length() == 0) {
				return;
			}
		
		// 7. Create a default number
		int number = 1;
		
		// 8. Try to parse the number
		try {
			number = Integer.parseInt(mNumberEditText.getText().toString());
		} catch (NumberFormatException ex) {
			Log.e(LOG_TAG, "Failed to parse number: " + ex.getMessage());
		}
		
		// 10. Call addItem
		addItem(mNameEditText.getText().toString(), number);
		
		// 11. Call updateCursor
		mAdapter.updateCursor(getAllItems());
		
		// 12. Clear fields to add some polish
		mNameEditText.clearFocus();
		mNumberEditText.clearFocus();
		mNameEditText.getText().clear();
		mNumber.getText().clear();
	}
	
	private Cursor getAllItems() {
		return mDb.query(
			myContract.myTable.TABLE_NAME, // table name
			null,	// columns
			null,	// selection
			null,	// slectionArgs
			null,	// groupBy
			myContract.myTable.COLUMN_TIMESTAMP // order by
		);
	}
	

	
	// 9. Create the addItem method
	private long addItem(String name, int number) {
		ContentValues cv = new ContentValues();
		cv.put(myContract.myTable.COLUMN_NAME, name);
		cv.put(myCotnract.myTable.COLUMN_NUMBER, number);

		return mDb.insert(myContract.myTable.TABLE_NAME, null, cv);
	}
	

}

// Next, lets add the ability to add new items to the database
public class myAdpater extends RecyclerView.Adapter<myAdapter.myViewHolder> {
	
	
	private Cursor mCursor;
	private Context mContext;
	
    public myAdapter(Context context, Cursor cursor) {
		this.mContext = context;
		this.mCursor = cursor;
        
    }
	
	
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.myListItem, viewGroup, false);
        return new myViewHolder(view);
    }

	
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return; 
		String name = mCursor.getString(mCursor.getColumnIndex(myContract.myTable.COLUMN_NAME));
		int number = mCursor.getInt(mCursor.getColumnIndex(myContract.myTable.COLUMN_NUMBER));
		
		 // Retrieve the id from the cursor
        long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));
		
		holder.nameTextView.setText(name);
		holder.numTextView.setText(String.valueOf(number));
		
		// Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(id);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount;
    }
	
	public void updateCursor(Cursor newCursor) {
		if (mCursor != null) mCursor.close();
		mCursor = newCursor;
		if (newCursor != null) {
			this.notifyDataSetChanged();
		}
	}
	
	class myViewHolder extends RecyclerView.ViewHolder {
		
		TextView nameTextView;
		TextView numTextView;
		
		
	
		public myViewHolder(View itemView) {
			super(itemView);
			
			nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
			numTextView = (TextView) itemView.findViewById(R.id.tv_number);
		}
		
		
		
	}
	
	
}

public class MainActivity extends AppCompatActivity {
	
	private SQLiteDatabase mDb;
	private myAdapter mAdapter;
	private EditText mNameEditText;
	private EditText mNumberEditText;
	
	private final static String LOG_TAG = MainActivity.class.getSimpleName();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	
		mNameEditText = (EditText) this.findViewById(R.id.name_edit_text);
        mNumberEditText = (EditText) this.findViewById(R.id.number_edit_text);
		
		RecyclerView mList = (RecyclerView) findViewById(R.id.rv_items);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mList.setLayoutManger(layoutManger);
		
		myDbHelper dbHelper = new myDbHelper(this);
		mDb = dbHelper.getWritableDatabase();
		
		Cursor cursor = getAllItems();
		mAdapter = new GuestListAdapter(this, cursor);
		mList.setAdapter(mAdapter);
		
		// Create an ItemTouchHelper to handle swiping items off the list
		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
			
			// Override onMove
			@Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }
			
			// Override onSwiped
			@Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // call removeGuest 
                removeGuest(id);
                // call updateCursor on mAdapter
                mAdapter.updateCursor(getAllItems());
            }
			
			
			
		}).attachToRecyclerView(mList);
		
		 
	}
	
	
	public void addToDatabase(View view) {
		if (mNameEditText.getText.length() == 0 ||
			mNumberEditText.getText.length() == 0) {
				return;
			}
		
		int number = 1;

		try {
			number = Integer.parseInt(mNumberEditText.getText().toString());
		} catch (NumberFormatException ex) {
			Log.e(LOG_TAG, "Failed to parse number: " + ex.getMessage());
		}
		
		addItem(mNameEditText.getText().toString(), number);

		mAdapter.updateCursor(getAllItems());

		mNameEditText.clearFocus();
		mNumberEditText.clearFocus();
		mNameEditText.getText().clear();
		mNumber.getText().clear();
	}
	
	private Cursor getAllItems() {
		return mDb.query(
			myContract.myTable.TABLE_NAME, // table name
			null,	// columns
			null,	// selection
			null,	// slectionArgs
			null,	// groupBy
			myContract.myTable.COLUMN_TIMESTAMP // order by
		);
	}
	
	private long addItem(String name, int number) {
		ContentValues cv = new ContentValues();
		cv.put(myContract.myTable.COLUMN_NAME, name);
		cv.put(myCotnract.myTable.COLUMN_NUMBER, number);

		return mDb.insert(myContract.myTable.TABLE_NAME, null, cv);
	}
	
	// 1. Add the function to remove items
	private boolean removeItem(long id) {
		return mDb.delete(myContract.myTable.TABLE_NAME, myContract.myTable._ID + "=" + id, null) > 0;
	}
	

}

