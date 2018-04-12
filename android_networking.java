// Add internet permission to the manifest file

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.example.android.yourApp">

    <uses-permission android:name="android.permission.INTERNET" />

	 <application...
	 

// Create a 'NetworkUtils' under a 'utils' android resource directory
public class NetworkUtils {

	// Create the URL that you wish to connect to
	
	final static String BASE_URL = "https://www.example.com/";
	final static String PARAM_QUERY = "q";
	
	// in this example, the URL will = "https://www.example.com/q=query"

	 /**
     * Builds the URL
     *
     * @param query The keyword that will be queried for.
     * @return The URL
     */
	public static URL buildUrl(String query) {
			Uri builtUri = Uri.parse(BASE_URL).buildUpon()
					.appendQueryParameter(PARAM_QUERY, query)
					.build();
					
			URL url = null;
		   
			 try {
				url = new URL(builtUri.toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			return url;
		}
		
	// Fetch the HTTP Request
	
	 /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
	public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
		
	

}



public class yourActivity.xml {
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.example.android.yourApp.yourActivity">	

<EditText
	android:id="@+id/edit_text"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="Enter a query"
	android:textSize="22sp" />


<ScrollView
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:layout_marginTop="16dp">

	<TextView
		android:id="@+id/text_view"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:text="Make a search!"
		android:textSize="18sp" />
    </ScrollView>

<LinearLayout/>
}


public class Activity {
	
	// 1A. Create resources
	private EditText mEditText;
	private TextView mTextView;

	private void onCreate(){
		// 1B. Ge references to resources
		mEditText = (EditText) findViewById(R.id.editText);
		mTextView = (TextView) findViewById(R.id.textView);
		
		// 2. Build the URL using NetworkUtils
		String query = mEditText.getText().toString();
		URL url = NetworkUtils.builtUrl(query);
		
		// 6. Execute the AsyncTask subclass
		new NetworkingTask().execute(url);
	}
	
	
	
	// 3. Create a subclass of AsyncTask to move networking off the main thread
	public class NetworkingTask extends AsyncTask<URL, Void, String> {
		
		// 4. Override doInBackground
		@Overide
		protected String doInBackground(URL... params) {
			URL url = params[0];
			String results = null;
			try {
				results = NetworkUtils.getResponseFromHttpUrl(url);
			} catch (IOException e) {
				e.printStackTrace;
			}
			return results;
		}
		
		// 5. Override onPostExecute
		@Override
		protected void onPostExecute(String results) {
			if (results != null && !results .equals("")) {
				mTextView.setText(results);
			}
		}
		
	}
		
}


public class yourActivity.xml {
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.example.android.yourApp.yourActivity">	

<EditText
	android:id="@+id/edit_text"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:hint="Enter a query"
	android:textSize="22sp" />

// Wrap the scroll view in a frame layout
	
<FrameLayout
	android:layout_width="match_parent"
	android:layout_height="match_parent">
	
	<ScrollView
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_marginTop="16dp">

		<TextView
			android:id="@+id/text_view"
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:text="Make a search!"
			android:textSize="18sp" />
    </ScrollView>

	// Add the error text view and a progress bar
	
	<TextView
		android:id="@+id/tv_error_message_display"
		android:textSize="22sp"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:padding="16dp"
		android:text="Could not connect to internet"
		android:visibility="invisible" />
		
	<ProgressBar
		android:id="@+id/pb_loading_indicator"
		android:layout_height="42dp"
		android:layout_width="42dp"
		android:layout_gravity="center"
		android:visibility="invisible" />
		
</FrameLayout>
<LinearLayout/>
}


public class Activity {
	
	private EditText mEditText;
	private TextView mTextView;
	// 1A. Create variables for resources
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;
	
	private class onCreate() {
		mEditText = (EditText) findViewById(R.id.editText);
		mTextView = (TextView) findViewById(R.id.textView);
		// 1B. Get a reference to resources
		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
		
		String query = mEditText.getText().toString();
		URL url = NetworkUtils.builtUrl(query);
		
		new NetworkingTask().execute(url);
	}
	
	
	
	// 2. Create a method to show the data
	private void showData() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mTextView.setVisibility(View.VISIBLE);
	}
	
	// 3. Create a method for error handling
	private void showErrorMessage() {
		mTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}
	

	public class NetworkingTask extends AsyncTask<URL, Void, String> {
		
		
		// 6. override on PreExecute to set loading indicator to VISIBLE
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadingIndicator.setVisibility.View.VISIBLE);
		}
	
		@Overide
		protected String doInBackground(URL... params) {
			URL url = params[0];
			String results = null;
			try {
				results = NetworkUtils.getResponseFromHttpUrl(url);
			} catch (IOException e) {
				e.printStackTrace;
			}
			return results;
		}
		
		
		@Override
		protected void onPostExecute(String results) {
			// 7. Hide the loading indicator after loading is complete
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			
			if (results != null && !results .equals("")) {
				// 4. call showData() if data is retrieved
				showData();
				mTextView.setText(results);
			} else { // 5. call showErrorMessage if not
				showErrorMessage();
			}
		}
		
	}
		
}



