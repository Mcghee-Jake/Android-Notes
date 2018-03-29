// First add the RecyclerView dependency in the build.gradle file
dependencies {
	compile 'com.android.support:recyclerview-v7:25.1.0'
}

// Add a RecyclerView to your activity's xml file
class activity_main.xml {
	<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v7.widget.RecyclerView
		android:id="@+id/rv_items"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</FrameLayout>
}

// Create a layout for your item views
class list_item.xml {
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <TextView
        android:id="@+id/tv_item_number"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical|start"
        android:fontFamily="monospace"
        android:textSize="42sp" />
</FrameLayout>
}

// Create an adapter for your RecyclerView
public class myAdpater extends RecyclerView.Adapter<myAdapter.myViewHolder> {
	
	// 1. First create the view holder (see below)
	
	// 4. Declare whatever global variable you need
	private int mNumberItems;
	
	
	// 5. Write a constructor to initialize whatever data you have
	/**
     * Constructor for GreenAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param numberOfItems Number of items to display in list
     */
    public myAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
    }
	
	
	// 6. Override onCreateViewHolder and return a new viewHolder
	 /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

	
	// 7. Override onBindViewHolder and bind the views to their position
    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

	//9. Override getItemCount
    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }
	
	// 1. Create the viewholder class
	class myViewHolder extends RecyclerView.ViewHolder {
		
		TextView itemNumberView;
		
		// 2. Create a constructor that accepts a View as a parameter
		public myViewHolder(View itemView) {
			super(itemView);
			
			itemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
		}
		
		
		// 3. Create a bind method for use in OnBindViewHolder
		void bind(int listIndex) {
			listItemNumberView.setText(String.valueOf(listIndex));
		}
		
	}
	
	
}

// Wire the adapter to your activity
public class MainActivity extends AppCompatActivity {
	
	
	// Get whatever data you need
	private static final int ITEMS = 100;
	
	// Create the adapter
	private myAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		// Get a reference to the recyclerView
		RecyclerView mList = (RecyclerView) findViewById(R.id.rv_items);
		
		// Create a LinearLayoutManager
		/*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
		 LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		 
		 // Set the layout manager for the recyclerView
		 mList.setLayoutManger(layoutManger);
		 
		 /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
		 mList.setHasFixedSize(true);
		 
		 
		 // initialize the adapter
		 mAdapter = new myAdapter(ITEMS);
		 
		 // attach the adapter to the recyclerView
		 mList.setAdapter(mAdapter);
		 
		 
	}
	

}

// To create click handling for the recyclerView (optional) part 1
public class myAdpater extends RecyclerView.Adapter<myAdapter.myViewHolder> {
	
	
	// 2. Declare the click listener
	final private ListItemClickListener mOnClickListener;
	
	// 1. Add a click listener interface
	public interface ListItemClickListener {
		void onListItemClick(int clickedItemIndex);
	}
	
	private int mNumberItems;
	
	// 3. Add the click listener as a parameter to the constructor and initialize it within the body
    public myAdapter(int numberOfItems, ListItemClickListener listner) {
        mNumberItems = numberOfItems;
		mOnClickListener = listener;
    }
	
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }
	
	// 4. Implement the click listener within the view holder class
	class myViewHolder extends RecyclerView.ViewHolder
		implements OnClickListener {
		
		TextView itemNumberView;
		
	
		public myViewHolder(View itemView) {
			super(itemView);
			
			itemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
			// 5. Call setOnClickListener on the view
			itemView.setOnClickListener(this);
		}
		
		
		void bind(int listIndex) {
			listItemNumberView.setText(String.valueOf(listIndex));
		}
		
		// 6. Override onClick
		@Override
		public void onClick(View v) {
			// 7. Do stuff when clicked
			int clickedPosition = getAdapterPosition();
			mOnClickListener.onListItemClick(clickedPosition);
		}
		
	}
	
	
}

// To create click handling for the recyclerView (optional) part 2
public class MainActivity extends AppCompatActivity 
	implements myAdapter.ListItemClickListener {
		
		
	// 1. ^ Implement myAdapter.ListItemClickListener ^
	
	// Created a toast here for this example
	private Toast mToast
	
	private static final int ITEMS = 100;
	
	private myAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		RecyclerView mList = (RecyclerView) findViewById(R.id.rv_items);
		
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mList.setLayoutManger(layoutManger);
		mList.setHasFixedSize(true);
		mAdapter = new myAdapter(ITEMS);
		mList.setAdapter(mAdapter);

	}
	
	// 2. Override onListitemClick
	 @Override
    public void onListItemClick(int clickedItemIndex) {
		// 3. Do stuff when clicked
		String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
	}
}

// Note: there was also instruction on how to set a refresh button on the recyclerView, so check out this lesson (3.6) again if you need to