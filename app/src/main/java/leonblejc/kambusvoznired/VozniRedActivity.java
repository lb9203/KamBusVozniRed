package leonblejc.kambusvoznired;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;

public class VozniRedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vozni_red);

        //Get intent
        Intent intent                = getIntent();

        //Get input values from intent
        String vstopnaPostajaString = intent.getStringExtra("vstopnaPostaja");
        String izstopnaPostajaString = intent.getStringExtra("izstopnaPostaja");
        String d = intent.getStringExtra("datum");

        //Initialize pre-table views
        TextView vstopnaPostaja = findViewById(R.id.VstopnaPostajaVR);
        TextView izstopnaPostaja = findViewById(R.id.IzstopnaPostajaVR);
        TextView datum = findViewById(R.id.DatumVR);
        TextView cena = findViewById(R.id.CenaVR);

        //Set text positioning for pre-table views
        vstopnaPostaja.setGravity(Gravity.CENTER);
        izstopnaPostaja.setGravity(Gravity.CENTER);
        cena.setGravity(Gravity.CENTER);
        datum.setGravity(Gravity.CENTER);

        //Set pre-table output values
        vstopnaPostaja.setText(vstopnaPostajaString);
        izstopnaPostaja.setText(izstopnaPostajaString);
        datum.setText(d);

        //Make new fetcher to obtain buses
        Fetcher fetcher      = new Fetcher();
        ArrayList<Bus> buses = fetcher.fetchBuses(
                vstopnaPostajaString,
                izstopnaPostajaString,
                d,
            Calendar.getInstance().getTime()
        );

        //Create layout for table output
        LinearLayout vozniRedi = findViewById(R.id.TabelaVR);
        View viewDividerFirst  = new View(this);
        viewDividerFirst.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 5)
        );

        //First divider before table
        viewDividerFirst.setBackgroundColor(Color.parseColor("#000000"));
        vozniRedi.addView(viewDividerFirst);

        //Error if no buses
        if (buses.size() == 0) {
            TextView errorView = new TextView(this.getApplicationContext());
            errorView.setText(R.string.NiZadetkov);
            errorView.setTextSize(50);
            errorView.setTextColor(Color.RED);
            errorView.setGravity(Gravity.CENTER);
            vozniRedi.addView(errorView);
        }

        TextView arrowView;
        TextView odhodView;
        TextView prihodView;
        LinearLayout currRow;
        Context appContext = this.getApplicationContext();
        View viewDivider;

        for (Bus currBus : buses) {

            //Set price if not set yet
            if (cena.getText().length() == 0) {
                cena.setText(String.format(this.getString(R.string.EUR), currBus.getCena()));
            }



            //Make new row
            currRow = new LinearLayout(appContext);
            currRow.setOrientation(LinearLayout.HORIZONTAL);
            currRow.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT)
            );

            //Make new views for arrival, departure, arrow in row
            arrowView  = new TextView(appContext);
            odhodView  = new TextView(appContext);
            prihodView = new TextView(appContext);

            //Set values for views in row
            odhodView.setText(currBus.getOdhod());
            arrowView.setText(R.string.Puscica);
            prihodView.setText(currBus.getPrihod());

            //Set text size for views in row
            arrowView.setTextSize(22);
            odhodView.setTextSize(22);
            prihodView.setTextSize(22);

            //Set positioning and size for views in row
            odhodView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));

            arrowView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (float) 0.25
            ));

            prihodView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));

            //Set text positioning for views in row
            arrowView.setGravity(Gravity.CENTER);
            odhodView.setGravity(Gravity.CENTER);
            prihodView.setGravity(Gravity.CENTER);

            //Add views to row
            currRow.addView(odhodView);
            currRow.addView(arrowView);
            currRow.addView(prihodView);

            //Make divider between rows
            viewDivider = new View(this);
            viewDivider.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 5)
            );
            viewDivider.setBackgroundColor(Color.parseColor("#000000"));

            //Add row and divider to table
            vozniRedi.addView(currRow);
            vozniRedi.addView(viewDivider);
        }
    }
}
