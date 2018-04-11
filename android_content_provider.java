// First, create a ContentProvider class in the data folder
public class myContentProvider extends ContentProvider {
	
	
	// 2. Declare a variable for the dbHelper
	private myDbHeleper mDbHelper; 
	
	 @Override
    public boolean onCreate() {
		// 3. Initialize the dbHelper
		mDbHelper = new myDbHelper(getContext());

        return false;
    }


	// 1. Implement the required functions
	
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// Next, add the Content Provider to the manifest file 
class manifest {
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.android.myapp">

    <application
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">

        <!-- The manifest entry for the MainActivity -->
        <activity android:name="com.example.android.myapp.MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

     

        // Register the ContentProvider and set name, authorities, and exported attributes
        // exported = false limits access to this ContentProvider to only this app
        <provider
            android:name="com.example.android.myapp.data.myContentProvider"
            android:authorities="com.example.android.myapp"
            android:exported="false"/> // 

    </application>

</manifest>
}

public class myContract {
	
	// Add content provider constants to the Contract
	
	 // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.myapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "items" directory
    public static final String PATH_ITEMS = "items";
	
	public static final class myTable implements BaseColumns {
		
		// Add the uri to the contract
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEMS).build();
		
		public static final String TABLE_NAME = "myTable";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_NUMBER = "number";
		public static final String COLUMN_TIMESTAMP = "timestamp";
	}
}

public class myContentProvider extends ContentProvider {
	
	/* 
	1. Define final integer constants for the directory of tasks and a single item. 
	It's convention to use 100, 200, 300, etc for directories,
    and related ints (101, 102, ..) for items in that directory.
	*/
	public static final int ITEM = 100;
	public static final int ITEM_WITH_ID = 101;
	
	// 3. Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();
	
	
	// 2. Define a static buildUriMatcher method that associates URI's with their int match
	/**
     Initialize a new matcher object without any matches,
     then use .addURI(String authority, String path, int match) to add matches
     */
	public static UriMatcher buildUriMatcher() {

		// Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }
	
	private myDbHeleper mDbHelper; 
	
	 @Override
    public boolean onCreate() {
		mDbHelper = new myDbHelper(getContext());

        return false;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
		// 4. Get access to the task database (to write new data to)
		final SQLiteDatabase db = mDbHelper.getWriteableDatabase();
		
		// 5. Write URI matching code to identify the match for the tasks directory
		int match = sUriMatcher.match(uri);
		Uri returnUri; // URI to be returned
		
       switch (match) {
            case TASKS:
                // 3. Insert new values into the database
                // Inserting values into tasks table
                long id = db.insert(myContract.myTable.TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(myContract.myTable.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // 4. Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }		

		// 5. Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);
		
		// Return the resulting Uri
        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
							
		 // 6. Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

        // 7. Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // 8. Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case TASKS:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // 9. Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
		// 10. Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        // 11. Write the code to delete a single row of data
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case TASK_WITH_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // 12. Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}

public class Activity() {
	
	// To insert an object into the database
	addItemToDatabase(View view) {
		String input = ((editText) findViewById(R.id.myEditText)).getText().toString();
		// Exit if edit text is empty
		 if (input.length() == 0) {
            return;
        }
	
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);
	}
	
	// To query database
	@Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // 11. Query and load all task data in the background; sort by priority

                try {
                    return getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            TaskContract.TaskEntry.COLUMN_PRIORITY);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // 12. deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
		
    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // 13. Update the data that the adapter uses to create ViewHolders
        mAdapter.swapCursor(data);
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
        }
	
	// Swipe to delete is covered in T09.07
}
