// First, create a ContentProvider class in the data folder
public class myContentProvider extends ContentProvider {
	// 1. Implement the required functions
	
	// 2. Declare a variable for the dbHelper
	private myDbHeleper mDbHelper; 
	
	 @Override
    public boolean onCreate() {
		// 3. Initialize the dbHelper
		mDbHelper = new myDbHelper(getContext());

        return false;
    }


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

