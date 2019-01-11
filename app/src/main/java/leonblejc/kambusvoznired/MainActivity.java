package leonblejc.kambusvoznired;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Declare views
    CalendarView datum;
    AutoCompleteTextView vstopnaPostaja;
    AutoCompleteTextView izstopnaPostaja;
    ProgressDialog progress;
    Button sendButton;

    //Declare variables
    private String dateString;
    private String[] stationsArray;
    ArrayAdapter<String> stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize variables
        stationsArray   = getResources().getStringArray(R.array.postajeArray);
        vstopnaPostaja  = findViewById(R.id.VstopnaPostajaInner);
        izstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);
        sendButton      = findViewById(R.id.button);
        datum           = findViewById(R.id.DatumInner);
        progress        = new ProgressDialog(this);
        stringAdapter   = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1, stationsArray);

        //Set View properties
        vstopnaPostaja.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        izstopnaPostaja.setImeOptions(EditorInfo.IME_ACTION_DONE);

        sendButton.setEnabled(false);
        vstopnaPostaja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> PostajeList = Arrays.asList(stationsArray);
                if (PostajeList.contains(s.toString()) &&
                        PostajeList.contains(izstopnaPostaja.getText().toString())) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        izstopnaPostaja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> PostajeList = Arrays.asList(stationsArray);
                if (PostajeList.contains(s.toString()) &&
                        PostajeList.contains(vstopnaPostaja.getText().toString())) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        vstopnaPostaja.setAdapter(stringAdapter);
        izstopnaPostaja.setAdapter(stringAdapter);
        vstopnaPostaja.setThreshold(2);
        izstopnaPostaja.setThreshold(2);
        progress.setTitle(getResources().getString(R.string.Nalagam));
        progress.setMessage(getResources().getString(R.string.Cakaj));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        //Go to izstopnaPostaja when vstopnaPostaja input is done
        vstopnaPostaja.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    izstopnaPostaja.requestFocus();
                    return true;
                }
                return false;
            }
        });

        //Close keyboard when izstopnaPostaja input is done
        izstopnaPostaja.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    izstopnaPostaja.clearFocus();
                    InputMethodManager imm =
                            (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(izstopnaPostaja.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

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
        /*
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
        */

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
            //vstopnaPostaja.setText("");
            //izstopnaPostaja.setText("");
            //datum.setDate(System.currentTimeMillis(),false,true);
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
