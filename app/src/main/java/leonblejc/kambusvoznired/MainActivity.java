package leonblejc.kambusvoznired;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void sendData(View view){
        //Intent za prikaz voznega reda
        Intent PrikaziVozniRed = new Intent(this, VozniRedActivity.class);

        //Deklaracija vpisnih polj v activity_main
        TextInputEditText VstopnaPostaja = findViewById(R.id.VstopnaPostajaInner);
        TextInputEditText IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);
        TextInputEditText Datum = findViewById(R.id.DatumInner);

        //Pridobivanje vpisanih vrednosti
        String VstopnaPostajaString = VstopnaPostaja.getText().toString();
        String IzstopnaPostajaString = IzstopnaPostaja.getText().toString();
        String DatumString = Datum.getText().toString();

        //Dodajanje vrednosti v intent za uporabo v naslednjem activityu
        PrikaziVozniRed.putExtra("VstopnaPostaja", VstopnaPostajaString);
        PrikaziVozniRed.putExtra("IzstopnaPostaja", IzstopnaPostajaString);
        PrikaziVozniRed.putExtra("Datum", DatumString);

        startActivity(PrikaziVozniRed);
    }

    public void zamenjajPostaji(View view){
        TextInputEditText VstopnaPostaja = findViewById(R.id.VstopnaPostajaInner);
        TextInputEditText IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);

        String temp = VstopnaPostaja.getText().toString();
        VstopnaPostaja.setText(IzstopnaPostaja.getText().toString());
        IzstopnaPostaja.setText(temp);
    }




}
