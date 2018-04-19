// 1. Add a new resource directory under res/ called menu, make sure it is of type menu

// 2. Inside of the menu folder, create a menu resource file called 'activity_name.xml'

class main.xml {
	<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">
		<item
			android:id="@+id/action_search"
			android:orderInCategory="1"
			app:showAsAction="ifRoom"
			android:title="@string/search"/>
	</menu>
}

public class MainActivity extends AppCompatActivity {
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }
	
	// 3. Override onCreateOptionsMenu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// 4. Get a the menu inflater and inflate the menu with the xml file
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	// 5. Override onOptionsMenuItemSlected
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// 6. Check the item ID to see which menu item was clicked
        int id = item.getItemId();
        if (id == R.id.action_search) {
			// 7. Do something when clicked
            doSomething();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
}