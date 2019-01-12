package leonblejc.kambusvoznired;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;

public class settingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Create and set up toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar_settings_activity);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Nastavitve");

        //Access Shared Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor prefEditor = prefs.edit();

        //Initialize settings switches
        SwitchCompat keepLastStationsSwitch = findViewById(R.id.keepLastStationsSwitch);
        keepLastStationsSwitch.setChecked(prefs.getBoolean("keepLastStations",true));

        //Change settings according to switch positions
        keepLastStationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    prefEditor.putBoolean("keepLastStations",true);
                }
                else{
                    prefEditor.putBoolean("keepLastStations",false);
                }
                prefEditor.apply();
            }
        });


    }
}
