package com.mattfenlon.osmap;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.lang.reflect.Array;
import java.util.ArrayList;

import uk.co.ordnancesurvey.android.maps.BitmapDescriptor;
import uk.co.ordnancesurvey.android.maps.BitmapDescriptorFactory;
import uk.co.ordnancesurvey.android.maps.CameraPosition;
import uk.co.ordnancesurvey.android.maps.GridPoint;
import uk.co.ordnancesurvey.android.maps.LocationSource;
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




            // An ArrayList of places.
            ArrayList<MarkerOptions> circuits = new ArrayList<>();

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(233225,368797))
                            .snippet("Anglesey").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(504433,260590))
                    .snippet("Bedford").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(485658,394521))
                    .snippet("Blyton").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(529154,380854))
                    .snippet("Cadwell Park").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(385343,176774))
                    .snippet("Castle Combe").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(314379,212519))
                    .snippet("Circuit of Wales").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(428444,506804))
                    .snippet("Croft").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(354699,679246))
                    .snippet("East Fortune").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(487429,107392))
                    .snippet("Goodwood").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(175237,512277))
                    .snippet("Kirkistown").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(306641,693922))
                    .snippet("Knockhill").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(295974,171353))
                    .snippet("Llandow").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(623738,146883))
                    .snippet("Lydden").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(444951,300297))
                    .snippet("Mallory Park").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(132017,534151))
                    .snippet("Nutts Corner").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(503988,503988))
                    .snippet("Oliver's Mount").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(359035,364675))
                    .snippet("Oulton Park").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(239752,203307))
                    .snippet("Pembrey").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(491200,291637))
                    .snippet("Rockingham").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(496008,260958))
                    .snippet("Santa Pod").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(600368,289484))
                    .snippet("Snetterton").icon(icon));

            circuits.add(new MarkerOptions().gridPoint(new GridPoint(427999,145750))
                    .snippet("Thruxton").icon(icon));


            for(int i = 0; i < circuits.size(); i++){
                mMap.addMarker(circuits.get(i));
            }


            // Set the location and zoom to match.
            Float cameraZoom = 3000.0f;
            CameraPosition cameraPosition = new CameraPosition(gp,cameraZoom);
            mMap.moveCamera(cameraPosition, true);  // The true means it animates. Why not? Animation is nice.

            /** YOU'VE GONE TOO FAR... */

            return rootView;
        }

        @Override
        public boolean onMapClick(GridPoint gridPoint) {

            Log.v(TAG, "Tapped the Map: X: " + Double.toString(gridPoint.x) + ", Y: " +  Double.toString(gridPoint.y));



            return false;
        }
    }
}
