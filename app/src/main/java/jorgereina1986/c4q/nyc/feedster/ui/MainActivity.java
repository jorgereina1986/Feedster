package jorgereina1986.c4q.nyc.feedster.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import jorgereina1986.c4q.nyc.feedster.R;
import jorgereina1986.c4q.nyc.feedster.ui.fragments.FragmentLeft;
import jorgereina1986.c4q.nyc.feedster.ui.fragments.MusicFragment;

public class MainActivity extends Activity implements FragmentLeft.FragmentLeftInterface {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    protected void onStop() {
        super.onStop();
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

    private boolean isTablet(){
        if (findViewById(R.id.right_fragment_container) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void cardSelected(String cardName) {

        if (isTablet()) {
            // use transaction manager to replace detail fragment

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            if (cardName.equals("music")) {
                ft.replace(R.id.right_fragment_container, new MusicFragment());
            }

            ft.commit();
        }
        else {
            //start detail activity
//            Intent intent = new Intent(MusicDetail.class);
//            startActivity(intent);
        }

    }
}