package com.mattfenlon.osmap;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattfenlon.osmap.POJO.CircuitItem;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.co.ordnancesurvey.android.maps.BitmapDescriptor;
import uk.co.ordnancesurvey.android.maps.BitmapDescriptorFactory;
import uk.co.ordnancesurvey.android.maps.CameraPosition;
import uk.co.ordnancesurvey.android.maps.GridPoint;
import uk.co.ordnancesurvey.android.maps.MapFragment;
import uk.co.ordnancesurvey.android.maps.MarkerOptions;
import uk.co.ordnancesurvey.android.maps.OSMap;
import uk.co.ordnancesurvey.android.maps.OSTileSource;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OSMap.OnMapClickListener {

        // Log tag. Standard.
        private final static String TAG = MainActivity.class.getSimpleName();

        // This is your key. Straight forward, as they only give you one.
        private final static String OS_API_KEY = "YOUR API KEY GOES HERE";

        // Determines if you're using the paid "PRO" version. Of course I'm not. FALSE!
        private final static boolean OS_IS_PRO = false;

        // This'll be familiar to anyone who's used Google Maps before. Even the same var name.
        private OSMap mMap;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            /** ALL THE CODES LIVE HERE */

            // Get a handle on this map fragment so I can do stuff with it.
            MapFragment mf = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map_fragment));

            // Instantiating the map object.
            mMap = mf.getMap();

            // "create list of tileSources" - not sure what this does yet.
            ArrayList<OSTileSource> sources = new ArrayList<OSTileSource>();

            // This bit looks to be making sure that they don't give you nice things if you're not
            // paying for it... Even though I'm prettu sure that I am already, being a British
            // taxpayer and all. Meh.
            sources.add(mMap.webTileSource(OS_API_KEY, OS_IS_PRO, null));
            mMap.setTileSources(sources);

            // Ok, so it handles clicks. Good start.
            // Implemented OnMapClick listener to enable *this* to remain as such.
            mMap.setOnMapClickListener(this);




            // Now lets put a marker on there.

            // An icon too, because why not?
            BitmapDrawable lid = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lid), 64, 64, true));
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(lid.getBitmap());

            // Silverstone on its own.

            // Setting up a grid point to try it out.
            Double eastings = new Double(467467);
            Double northings = new Double(242706);

            // It looks like they've got their own proprietary Point class called GridPoint.
            GridPoint gp = new GridPoint(eastings, northings);

            // Location should be obvious, but anyway.
            String locationMessage = "Silverstone innit!";

            mMap.addMarker(new MarkerOptions()
                                   .gridPoint(gp)
                                   .snippet(locationMessage)
                                   .icon(icon));

            try {

                InputStream is = getResources().openRawResource(R.raw.circuits);

                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    output.append(line);
                }

                //output to LogCat
                Log.i("test", output.toString());

                Gson gson = new Gson();
                ArrayList<CircuitItem> circuitsArr = gson.fromJson(output.toString(), new TypeToken<List<CircuitItem>>(){}.getType());

                int i = 0;
                for (CircuitItem ci : circuitsArr) {

                    mMap.addMarker(new MarkerOptions().gridPoint(new GridPoint(ci.getLat(),ci.getLon())).snippet(ci.getName()).icon(icon));

                    i++;
                }
            }
            catch (IOException e) {
                //display an error toast message
                Toast toast = Toast.makeText(getActivity(), "File: not found!", Toast.LENGTH_LONG);
                toast.show();
            }

            // Set the location and zoom to match.
            Float cameraZoom = 3000.0f;
            CameraPosition cameraPosition = new CameraPosition(gp, cameraZoom);
            mMap.moveCamera(cameraPosition, true);  // The true means it animates. Why not? Animation is nice.

            /** YOU'VE GONE TOO FAR... */

            return rootView;
        }

        @Override
        public boolean onMapClick(GridPoint gridPoint) {

            Log.v(TAG, "Tapped the Map: X: " + Double.toString(gridPoint.x) + ", Y: " + Double.toString(gridPoint.y));



            return false;
        }
    }
}
