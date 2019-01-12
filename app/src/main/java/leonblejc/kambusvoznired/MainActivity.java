package leonblejc.kambusvoznired;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, settingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //Declare views
    CalendarView datum;
    AutoCompleteTextView vstopnaPostaja;
    AutoCompleteTextView izstopnaPostaja;
    ProgressDialog progress;


    //Declare variables
    private String dateString;
    ArrayAdapter<String> stringAdapter;
    SharedPreferences prefs;
    SharedPreferences.Editor prefEditor;
    boolean keepLastStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize variables
        String[] stationsArray = getResources().getStringArray(R.array.postajeArray);
        vstopnaPostaja  = findViewById(R.id.VstopnaPostajaInner);
        izstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);
        datum           = findViewById(R.id.DatumInner);
        progress        = new ProgressDialog(this);
        stringAdapter   = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1, stationsArray);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = prefs.edit();

        //Create toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar_main_activity);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;

        vstopnaPostaja.setAdapter(stringAdapter);
        izstopnaPostaja.setAdapter(stringAdapter);
        vstopnaPostaja.setThreshold(2);
        izstopnaPostaja.setThreshold(2);
        progress.setTitle(getResources().getString(R.string.Nalagam));
        progress.setMessage(getResources().getString(R.string.Cakaj));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        //Set View properties
        vstopnaPostaja.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        izstopnaPostaja.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Set default settings
        if(!prefs.contains("keepLastStations")){
            prefEditor.putBoolean("keepLastStations",true);
            prefEditor.apply();
        }

        //Get settings
        keepLastStations = prefs.getBoolean("keepLastStations",true);

        if(keepLastStations){
            vstopnaPostaja.setText(prefs.getString("lastVstopnaPostaja",""));
            izstopnaPostaja.setText(prefs.getString("lastIzstopnaPostaja",""));
        }

        //Go to izstopnaPostaja when autoComplete value is selected on vstopnaPostaja
        vstopnaPostaja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                izstopnaPostaja.requestFocus();
            }
        });

        //Close keyboard when autoComplete value is selected on izstopnaPostaja
        izstopnaPostaja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                izstopnaPostaja.clearFocus();
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(izstopnaPostaja.getWindowToken(), 0);
            }
        });

        //Clear if focused

        vstopnaPostaja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    vstopnaPostaja.setText("");
                }
            }
        });

        izstopnaPostaja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    izstopnaPostaja.setText("");
                }
            }
        });


        //Date formatter dd.MM.yyyy
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
        );

        //Calendar value updater
        dateString = simpleDateFormat.format(Calendar.getInstance().getTime());
        datum.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateString = dayOfMonth + "." + (month + 1) + "." + year;
            }
        });

    }

    //Send request for bus timetable, triggered by sendButton
    public void sendData(View view){
        //Show progress bar for data request
        progress.show();

        //Intent za prikaz voznega reda
        Intent PrikaziVozniRed = new Intent(this, VozniRedActivity.class);

        //Get input values
        String VstopnaPostajaString  = vstopnaPostaja.getText().toString();
        String IzstopnaPostajaString = izstopnaPostaja.getText().toString();

        //Remember last stations
        if(keepLastStations){
            prefEditor.putString("lastVstopnaPostaja",VstopnaPostajaString);
            prefEditor.putString("lastIzstopnaPostaja",IzstopnaPostajaString);
            prefEditor.apply();
        }

        //Add input values to intent for use in next activity
        PrikaziVozniRed.putExtra("vstopnaPostaja", VstopnaPostajaString);
        PrikaziVozniRed.putExtra("izstopnaPostaja", IzstopnaPostajaString);
        PrikaziVozniRed.putExtra("datum", dateString);

        //Start vozni red activity
        startActivityForResult(PrikaziVozniRed, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Reset inputs when VozniRedActivity closes
        if (requestCode == 1000) {
            progress.dismiss();
        }
    }

    public void zamenjajPostaji(View view){
        izstopnaPostaja.clearFocus();
        vstopnaPostaja.clearFocus();

        izstopnaPostaja.setAdapter(null);
        vstopnaPostaja.setAdapter(null);

        String temp = vstopnaPostaja.getText().toString();
        vstopnaPostaja.setText(izstopnaPostaja.getText().toString());
        izstopnaPostaja.setText(temp);

        izstopnaPostaja.setAdapter(stringAdapter);
        vstopnaPostaja.setAdapter(stringAdapter);
    }
}
