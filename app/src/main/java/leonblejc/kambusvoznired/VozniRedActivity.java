package leonblejc.kambusvoznired;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VozniRedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vozni_red);

        Intent intent = getIntent();
        String VstopnaPostajaString = intent.getStringExtra("VstopnaPostaja");
        String IzstopnaPostajaString = intent.getStringExtra("IzstopnaPostaja");
        String DatumString = intent.getStringExtra("Datum");

        TextView VstopnaPostaja = findViewById(R.id.VstopnaPostajaVR);
        TextView IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaVR);
        TextView Datum = findViewById(R.id.DatumVR);

        VstopnaPostaja.setText(VstopnaPostajaString);
        IzstopnaPostaja.setText(IzstopnaPostajaString);
        Datum.setText(DatumString);
    }
}
