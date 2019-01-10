package leonblejc.kambusvoznired;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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

        Intent intent                = getIntent();
        String VstopnaPostajaString  = intent.getStringExtra("VstopnaPostaja");
        String IzstopnaPostajaString = intent.getStringExtra("IzstopnaPostaja");
        String DatumString           = intent.getStringExtra("Datum");

        TextView VstopnaPostaja  = findViewById(R.id.VstopnaPostajaVR);
        TextView IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaVR);
        TextView Datum           = findViewById(R.id.DatumVR);
        TextView Cena            = findViewById(R.id.CenaVR);

        //Nastavi vrednosti polj
        VstopnaPostaja.setText(VstopnaPostajaString);
        IzstopnaPostaja.setText(IzstopnaPostajaString);
        Datum.setText(DatumString);

        Fetcher fetcher      = new Fetcher();
        ArrayList<Bus> buses = fetcher.fetchBuses(
            VstopnaPostajaString,
            IzstopnaPostajaString,
            DatumString,
            Calendar.getInstance().getTime()
        );

        LinearLayout vozniRedi = findViewById(R.id.TabelaVR);
        View viewDividerFirst = new View(this);
        viewDividerFirst.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 5)
        );
        viewDividerFirst.setBackgroundColor(Color.parseColor("#000000"));
        vozniRedi.addView(viewDividerFirst);

        if (buses.size() == 0) {
            TextView errorView = new TextView(this.getApplicationContext());
            errorView.setText(R.string.NiZadetkov);
            errorView.setTextSize(50);
            errorView.setTextColor(Color.RED);
            errorView.setGravity(Gravity.CENTER);

            vozniRedi.addView(errorView);
        }

        for (Bus currBus : buses) {
            if (Cena.getText().length() == 0) {
                Cena.setText(String.format(this.getString(R.string.EUR), currBus.getCena()));
            }

            Context appContext = this.getApplicationContext();

            LinearLayout currRow = new LinearLayout(appContext);
            currRow.setOrientation(LinearLayout.HORIZONTAL);
            currRow.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT)
            );

            TextView arrowView  = new TextView(appContext);
            TextView odhodView  = new TextView(appContext);
            TextView prihodView = new TextView(appContext);

            odhodView.setText(currBus.getOdhod());
            arrowView.setText(R.string.Puscica);
            prihodView.setText(currBus.getPrihod());

            arrowView.setTextSize(22);
            odhodView.setTextSize(22);
            prihodView.setTextSize(22);

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

            arrowView.setGravity(Gravity.CENTER);
            odhodView.setGravity(Gravity.CENTER);
            prihodView.setGravity(Gravity.CENTER);

            currRow.addView(odhodView);
            currRow.addView(arrowView);
            currRow.addView(prihodView);

            View viewDivider = new View(this);
            viewDivider.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 5)
            );
            viewDivider.setBackgroundColor(Color.parseColor("#000000"));

            vozniRedi.addView(currRow);
            vozniRedi.addView(viewDivider);

        }
    }
}
