/* Create a new Empty Activity named SettingsActivity; 
* make sure to generate the activity_settings.xml layout file as well and add the activity to the manifest
*/

 // Add a new resource folder called menu and create activity_name_menu.xml

class activity_name_menu.xml {
	<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
	// Create an item to navigate to settings
    <item
        android:id="@+id/action_settings"
        android:orderInCategory="100"
        android:title="@string/action_settings"
        app:showAsAction="never" />
</menu>
 }
 
public class activity extends AppCompatActivity {
	
	// Add the menu to the activity
	    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_name_menu, menu);
        // Return true so that the visualizer_menu is displayed in the Toolbar
        return true;
    }
	
	// When the "Settings" menu item is pressed, open SettingsActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

// Modify the manifest file so that the back/up button works properly
class manifiest {
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
	
	<application
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="GoogleAppIndexingWarning">
        <activity android:name=".MainActivity"
            android:launchMode="singleTop">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
	
		<activity
            android:name=".SettingsActivity"
            android:label="@string/action_settings"
            android:parentActivityName=".VisualizerActivity">
            <meta-data
                android:name="android.support.PARENT_ACTIVITY"
                android:value=".VisualizerActivity" />
        </activity>
	</application>
</manifestt>
}

public class SettingsActivity extends AppCompatActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the Parent Activity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

build.gradle(Module: app) {
	
	dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.1.0'
    // Add the gradle dependency for the support preference fragment
	compile 'com.android.support:preference-v7:25.1.0'
	}
}

// Create a class called SettingsFragment that extends PreferenceFragmentCompat
// In res -> xml create a file for the preference fragment

class preferences.xml {
	// Add a preference screen and whatever layouts you need
	<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">

    <CheckBoxPreference
        android:defaultValue="true"
        android:key="show_bass"
        android:summaryOff="Hidden"
        android:summaryOn="Shown"
        android:title="Show Bass" />

	</PreferenceScreen>
}

 