// 1. Use Android Studio's Activity wizard to create a new Activity

// 2. Create the layout in the xml file
class activity_two.xml {
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context=".ChildActivity">

    <TextView
        android:id="@+id/tv_display"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="This is default text. Hopefully it changes."
        android:textSize="30sp"/>

</FrameLayout>
}

public class MainActivity extends AppCompatActivity {
	
	private Button btn;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	
		btn = (Button) findViewById(R.id.btn);
		
		mDoSomethingCoolButton.setOnClickListener(new OnClickListener() {

            /**
             * The onClick method is triggered when this button (mDoSomethingCoolButton) is clicked.
             *
             * @param v The view that is clicked. In this case, it's mDoSomethingCoolButton.
             */
            @Override
            public void onClick(View v) {

                // 2. Store ChildActivity.class in a Class object called destinationActivity
                /* This is the class that we want to start (and open) when the button is clicked. */
                Class destinationActivity = ChildActivity.class;

                // 3. Create an Intent to start ChildActivity
                /*
                 * Here, we create the Intent that will start the Activity we specified above in
                 * the destinationActivity variable. The constructor for an Intent also requires a
                 * context, which is the activity that the new activity is being launched from.
                 */
                Intent startChildActivityIntent = new Intent(MainActivity.this, destinationActivity);

                // 4. Start the second activity
                /*
                 * Once the Intent has been created, we can use Activity's method, "startActivity"
                 * to start the ChildActivity.
                 */
                startActivity(startChildActivityIntent);
            }
        });
	}
}

// To pass data between activities 
public class MainActivity extends AppCompatActivity {
	
	private Button btn;
	private EditText et;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	
		btn = (Button) findViewById(R.id.btn);
		et = (EditText) findViewById(R.id.et);
		
		mDoSomethingCoolButton.setOnClickListener(new OnClickListener() {
			
			// Get something to pass to the next activity
			String txtEntry = et.getText().toString();
			
            /**
             * The onClick method is triggered when this button (mDoSomethingCoolButton) is clicked.
             *
             * @param v The view that is clicked. In this case, it's mDoSomethingCoolButton.
             */
            @Override
            public void onClick(View v) {

				
                Class destinationActivity = ChildActivity.class;
                Intent startChildActivityIntent = new Intent(MainActivity.this, destinationActivity);
				
				 // Use the putExtra method to put the String from the EditText in the Intent
                /*
                 * We use the putExtra method of the Intent class to pass some extra stuff to the
                 * Activity that we are starting. Generally, this data is quite simple, such as
                 * a String or a number. However, there are ways to pass more complex objects.
                 */
				 startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, textEntered);
				
                startActivity(startChildActivityIntent);
            }
        });
	}
}

public class childActivity extends AppCompatActivity {

    private TextView mDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mDisplayText = (TextView) findViewById(R.id.tv_display);

        // Use the getIntent method to store the Intent that started this Activity in a variable
        /*
         * Here is where all the magic happens. The getIntent method will give us the Intent that
         * started this particular Activity.
         */
        Intent intentThatStartedThisActivity = getIntent();

        // Create an if statement to check if this Intent has the extra we passed from MainActivity
        /*
         * Although there is always an Intent that starts any particular Activity, we can't
         * guarantee that the extra we are looking for was passed as well. Because of that, we need
         * to check to see if the Intent has the extra that we specified when we created the
         * Intent that we use to start this Activity. Note that this extra may not be present in
         * the Intent if this Activity was started by any other method.
         * */
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            // If the Intent contains the correct extra, retrieve the text
            /*
             * Now that we've checked to make sure the extra we are looking for is contained within
             * the Intent, we can extract the extra. To do that, we simply call the getStringExtra
             * method on the Intent. There are various other get*Extra methods you can call for
             * different types of data. Please feel free to explore those yourself.
             */
            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

            // If the Intent contains the correct extra, use it to set the TextView text
            /*
             * Finally, we can set the text of our TextView (using setText) to the text that was
             * passed to this Activity.
             */
            mDisplayText.setText(textEntered);
        }
    }
}
