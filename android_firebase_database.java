// Declare database objects
private FirebaseDatabase mFirebaseDatabase;
private DatabaseReference mMessagesDatabase;
private ChildEventListener mChildEventListener;

	... onCreate(){
		
		// Initializing the database objects
		mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseName = mFirebaseDatabase.getReference().child("giveThisChildAReleventName");
		
		someButton.setOnClickListener(){
			// Write to database
			mDatabaseName.push().setValue(myObject);
		}
		
		// Read from database
		mChildEventListner = new ChildEventListener() {
			@Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				myObject mObject = dataSnapshot.getValue(myObject.class);
                mMessageAdapter.add(mObject);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
		};
		
		mDatabaseName.addChildEventListener(mChildEventListener);
	}

// For authorization

public static final int RC_SIGN_IN = 1;

private FirebaseDatabase mFirebaseDatabase;
private DatabaseReference mMessagesDatabase;
private ChildEventListener mChildEventListener;
// Add FirebaseAuth objects
private FirebaseAuth mFirebaseAuth;
private FirebaseAuth.AuthStateListener mAuthStateListener;

	... onCreate(){
		

		mFirebaseDatabase = FirebaseDatabase.getInstance();
		// Initialize FirebaseAuth object
		mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseName = mFirebaseDatabase.getReference().child("giveThisChildAReleventName");
		
		someButton.setOnClickListener(){
			mDatabaseName.push().setValue(myObject);
		}
		
		// Initialize AuthStateListener
		mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // Check if user is logged in
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
					onSignInInitialized(user.getDisplayName());
                } else {
                    // User is not signed in
					onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Exit app if sign in was cancelled by the back button
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                // Sign out
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
        mMessageAdapter.clear();
    }

    private void onSignInInitialized(String username) {
        mUsername = username;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
        detachDatabaseReadListener();
    }

    private void attachDatabaseReadListener(){
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                    mMessageAdapter.add(friendlyMessage);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) { }
            };

            mMessagesDatabase.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener(){
        if (mChildEventListener != null) {
            mMessagesDatabase.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }

    }