
package com.example.guest.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.myrestaurants.Constants;
import com.example.guest.myrestaurants.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private EditText mLocationEditText;
    private TextView mAppNameTextView;
    private DatabaseReference mSearchedLocationReference;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private ValueEventListener mSearchedLocationReferenceListener;
    @Bind(R.id.savedRestaurantsButton) Button mSavedRestaurantsButton;
    @Bind(R.id.findRestaurantsButton) Button mFindRestaurantsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        mSearchedLocationReferenceListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location:" + location);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAppNameTextView = (TextView) findViewById(R.id.appNameTextView);


        Typeface font1 = Typeface.createFromAsset(getAssets(), "font1.ttf");
        mAppNameTextView.setTypeface(font1);

        mLocationEditText = (EditText) findViewById(R.id.locationEditText);
        mLocationEditText.setTypeface(font1);

        mFindRestaurantsButton = (Button) findViewById(R.id.findRestaurantsButton);
        mFindRestaurantsButton.setTypeface(font1);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mSavedRestaurantsButton.setOnClickListener(this);
        mFindRestaurantsButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }

    @Override
    public void onClick(View v) {
        String location = mLocationEditText.getText().toString();
        if (v == mFindRestaurantsButton) {
            if (location.equals("")) {
                Toast toast = Toast.makeText(MainActivity.this, "Please input a location", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
            } else {
                saveLocationToFirebase(location);
                addToSharedPreferences(location);
                Intent intent1 = new Intent(MainActivity.this, RestaurantsActivity.class);
                intent1.putExtra("location", location);
                mLocationEditText.setText("");
                startActivity(intent1);
            }

        } if(v == mSavedRestaurantsButton) {
            Intent intent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
            startActivity(intent);
        }
    }


    private void addToSharedPreferences(String location){
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }
}
