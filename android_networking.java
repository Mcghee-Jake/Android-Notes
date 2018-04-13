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
	
	// In this example, the URL will = "https://www.example.com/q=query"

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


public class Activity extends AppCompatActivity {
	
	// 1A. Create resources
	private EditText mEditText;
	private TextView mTextView;

	private void onCreate(){
		// 1B. Ge references to resources
		mEditText = (EditText) findViewById(R.id.editText);
		mTextView = (TextView) findViewById(R.id.textView);
	}
	
	private void makeQuery() {
		// 4. Build the URL using NetworkUtils
		String query = mEditText.getText().toString();
		URL url = NetworkUtils.buildUrl(query);
		
		// 8. Execute the AsyncTask subclass
		new NetworkingTask().execute(url);
	}
	
	
	
	// 5. Create a subclass of AsyncTask to move networking off the main thread
	public class NetworkingTask extends AsyncTask<URL, Void, String> {
		
		// 6. Override doInBackground
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
		
		// 7. Override onPostExecute
		@Override
		protected void onPostExecute(String results) {
			if (results != null && !results .equals("")) {
				mTextView.setText(results);
			}
		}
		
	}
	
	// 2. Create options menu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	// 3. Call makeQuery() when options item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
		
}


// To add in a progress wheel and an error message

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

// Progress wheel and error message continued

public class Activity extends AppCompatActivity {
	
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
	}
	
	private void makeQuery() {
		String query = mEditText.getText().toString();
		// 2 Exit if no search was entered
		if (TextUtils.isEmpty(query)) {
            return;
        }
		URL url = NetworkUtils.buildUrl(query);
		
		new NetworkingTask().execute(url);
	}
	
	
	// 3. Create a method to show the data
	private void showData() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mTextView.setVisibility(View.VISIBLE);
	}
	
	// 4. Create a method for error handling
	private void showErrorMessage() {
		mTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}
	

	public class NetworkingTask extends AsyncTask<URL, Void, String> {
		
		
		// 7. override on PreExecute to set loading indicator to VISIBLE
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
			// 8. Hide the loading indicator after loading is complete
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			
			if (results != null && !results .equals("")) {
				// 5. call showData() if data is retrieved
				showData();
				mTextView.setText(results);
			} else { // 6. call showErrorMessage if not
				showErrorMessage();
			}
		}
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
		
}

// To preserve data when screen is rotated

public class Activity extends AppCompatActivity {
	// 1. Create a static final key to store the query
    /* A constant to save and restore the URL that is being displayed */
    private static final String SEARCH_QUERY_EXTRA = "query";

    // 2. Create a static final key to store the search's raw JSON
    /* A constant to save and restore the JSON that is being displayed */
    private static final String SEARCH_RESULTS_RAW_JSON = "results";
	
	private EditText mEditText;
	private TextView mTextView;
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;
	
	private class onCreate() {
		mEditText = (EditText) findViewById(R.id.editText);
		mTextView = (TextView) findViewById(R.id.textView);
		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
		
		// 9. If the savedInstanceState bundle is not null, set the text of the URL and search results TextView respectively
        if (savedInstanceState != null) {
            String query = savedInstanceState.getString(SEARCH_QUERY_EXTRA);
            String rawJsonSearchResults = savedInstanceState.getString(SEARCH_RESULTS_RAW_JSON);

            mEditText.setText(queryUrl);
            mTextView.setText(rawJsonSearchResults);
        }
	}
	
	private void makeQuery() {
		String query = mEditText.getText().toString();
		if (TextUtils.isEmpty(query)) {
            return;
        }
		URL url = NetworkUtils.buildUrl(query);
		
		new NetworkingTask().execute(url);
	}
	
	
	private void showData() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mTextView.setVisibility(View.VISIBLE);
	}
	
	private void showErrorMessage() {
		mTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}
	

	public class NetworkingTask extends AsyncTask<URL, Void, String> {
		
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
			mLoadingIndicator.setVisibility(View.INVISIBLE);
			
			if (results != null && !results .equals("")) {
				showData();
				mTextView.setText(results);
			} else {
				showErrorMessage();
			}
		}
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	// 3. Override onSaveInstanceState to persist data across Activity recreation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 4. Make sure super.onSaveInstanceState is called before doing anything else
        super.onSaveInstanceState(outState);

		// 5. Put the contents of the TextView that contains our search into a variable
        String queryUrl = mEditText.getText().toString();
		
        // 6. Using the key for the query URL, put the string in the outState Bundle
        outState.putString(SEARCH_QUERY_EXTRA, queryUrl);

        // 7. Put the contents of the TextView that contains our search results into a variable
        String rawJsonSearchResults = mSearchResultsTextView.getText().toString();

        // 8. Using the key for the raw JSON search results, put the search results into the outState Bundle
        outState.putString(SEARCH_RESULTS_RAW_JSON, rawJsonSearchResults);
    }
		
}

// Switch AsyncTask to AsyncTaskLoader

// 1. Implement LoaderManager.LoaderCallbacks<String> on your activity
public class Activity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {
			
	// 2. Create a static final key to store the query's URL
	private static final String SEARCH_QUERY_URL = "url";
	
    private static final String SEARCH_QUERY_EXTRA = "query";
	
	// 
	private static final String SEARCH_RESULTS_RAW_JSON = "results";

	
	// 3. Create a constant int to uniquely identify your loader.
    /*
     * This number will uniquely identify our Loader and is chosen arbitrarily. You can change this
     * to any number you like, as long as you use the same variable name.
     */
    private static final int MY_LOADER = 77;
	private EditText mEditText;
	private TextView mTextView;
	private TextView mErrorMessageDisplay;
	private ProgressBar mLoadingIndicator;

	
	private class onCreate() {
		
		mEditText = (EditText) findViewById(R.id.editText);
		mTextView = (TextView) findViewById(R.id.textView);
		mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        if (savedInstanceState != null) {
            String query = savedInstanceState.getString(SEARCH_QUERY_EXTRA);
            // 26. Remove the code that retrieves the JSON

            mEditText.setText(queryUrl);
            // 25. Remove the code that displays the JSON
        }
		
		// 24. Initialize the loader with GITHUB_SEARCH_LOADER as the ID, null for the bundle, and this for the context
        /*
         * Initialize the loader
         */
        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);
		
		
	}
	
	private void makeQuery() {
		String query = mEditText.getText().toString();
		if (TextUtils.isEmpty(query)) {
            return;
        }
		// 18. Remove the call to execute the AsyncTask
		
		 // 19. Create a bundle called queryBundle
        Bundle queryBundle = new Bundle();
		
        // 20. Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(SEARCH_QUERY_URL, url.toString());
		
		/*
         * Now that we've created our bundle that we will pass to our Loader, we need to decide
         * if we should restart the loader (if the loader already existed) or if we need to
         * initialize the loader (if the loader did NOT already exist).
         *
         * We do this by first storing the support loader manager in the variable loaderManager.
         * All things related to the Loader go through through the LoaderManager. Once we have a
         * hold on the support loader manager, (loaderManager) we can attempt to access our
         * searchLoader. To do this, we use LoaderManager's method, "getLoader", and pass in
         * the ID we assigned in its creation. You can think of this process similar to finding a
         * View by ID. We give the LoaderManager an ID and it returns a loader (if one exists). If
         * one doesn't exist, we tell the LoaderManager to create one. If one does exist, we tell
         * the LoaderManager to restart it.
         */
		 // 21. Call getSupportLoaderManager and store it in a LoaderManager variable
         LoaderManager loaderManager = getSupportLoaderManager();
         // 22. Get our Loader by calling getLoader and passing the ID we specified
         Loader<String> searchLoader = loaderManager.getLoader(MY_LOADER);
         // 23. If the Loader was null, initialize it. Else, restart it.
         if (githubSearchLoader == null) {
             loaderManager.initLoader(MY_LOADER, queryBundle, this);
         } else {
             loaderManager.restartLoader(MY_LOADER, queryBundle, this);
         }
	}
	
	private void showData() {
		mErrorMessageDisplay.setVisibility(View.INVISIBLE);
		mTextView.setVisibility(View.VISIBLE);
	}
	
	private void showErrorMessage() {
		mTextView.setVisibility(View.INVISIBLE);
		mErrorMessageDisplay.setVisibility(View.VISIBLE);
	}
	
	
	// 4. Override onCreateLoader
	@Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        // 5. Return a new AsyncTaskLoader<String> as an anonymous inner class with this as the constructor's parameter
        return new AsyncTaskLoader<String>(this) {

            // 6. Override onStartLoading
            @Override
            protected void onStartLoading() {

                // 7. If args is null, return.
                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                // 8. Show the loading indicator
                /*
                 * When we initially begin loading in the background, we want to display the
                 * loading indicator to the user
                 */
                mLoadingIndicator.setVisibility(View.VISIBLE);

                // 9. Force a load
                forceLoad();
            }

            // 10. Override loadInBackground
            @Override
            public String loadInBackground() {

                // 11. Get the String for our URL from the bundle passed to onCreateLoader
                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL);

                // 12. If the URL is null or empty, return null
                /* If the user didn't enter anything, there's nothing to search for */
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                // 13. Copy the try / catch block from the AsyncTask's doInBackground method
                /* Parse the URL from the passed in String and perform the search */
                try {
                    URL url = new URL(searchQueryUrlString);
                    String result = NetworkUtils.getResponseFromHttpUrl(url);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    // 14. Override onLoadFinished
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        // 15. Hide the loading indicator
        /* When we finish loading, we want to hide the loading indicator from the user. */
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        // 16. Use the same logic used in onPostExecute to show the data or the error message
        /*
         * If the results are null, we assume an error has occurred. There are much more robust
         * methods for checking errors, but we wanted to keep this particular example simple.
         */
        if (null == data) {
            showErrorMessage();
        } else {
            mSearchResultsTextView.setText(data);
            showJsonDataView();
        }
    }

    // 17. Override onLoaderReset as it is part of the interface we implement, but don't do anything in this method
    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }
	
	// 29. Delete the AsyncTask
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String queryUrl = mEditText.getText().toString();
        outState.putString(SEARCH_QUERY_EXTRA, queryUrl);

		// 27. Remove the code that persists the JSON
    }
		
}



