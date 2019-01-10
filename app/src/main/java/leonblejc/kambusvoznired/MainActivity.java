package leonblejc.kambusvoznired;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES31Ext;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String DatumString;

    private String[] Postaje = {"Baderna","Bakovnik Lidl","Bakovnik Pošta","Bašanija","Bašarinka","Bela/Motniku","Bela/Špitaliču","Bistrica/Vranskem","Bišče rondo","Bišče vas","Blagovica","BLAGOVICA PARK","Bled","Bled Union","Bočna","Brezje","Brezje pri Dobu","Buč","Buč Hruševka","Češenik","Češenik vas","Češnjice","Črna pri Kamniku N.Jašek","Črna pri Kamniku Petek","Črna pri Kamniku rudnik","Črnevšek","Črnivec nad Kamnikom","Dajla","Delce","Depala vas","Dob OŠ","Dob vas","Dob/Domžalah","Dob2/Domžalah","Dobrava","Dobrava/Moravčah","Dol pri Ljubljani","Dol/G.Gradu","Dol/G.Gradu Rogačnik","Dolenje/Radomljah","Domžale","Domžale K","Domžale Lidl","Domžale Petrol","Domžale stad.","Domžale Univ.","Dragomelj","Drtija K","Duplica","Duplica Qlandia","Duplica Stol N O","G.Grad","G.Grad sp.trg","Garaža","Garaža Perovo","Godič","Golice","Gora pri Pečah","Gorica/Moravčah","Gozd","Gozd1","Grušovlje/Ljubnem","Homec","Hudo Polje","Hudo vas","Igla","Ihan Emona","Ihan farma","Ihan Flerin","Ihan Termit","Ince Mengeš","Izola","Jarše","Jarše Bunkež","Jarše I.C. Sam","Jarše OŠ","Jastroblje","Kamnik","Kamnik KIK","Kamnik Mercator","Kamnik Metalka","Kamnik Perovo","Kamnik Svetilnik","Kamnik ŠCRM","Kamnik ŽP","Kamnik ŽTP","Kamniška Bistrica","Karigador","Kavran","Klemenčevo","Kokarje","Količevo","Količevo Helios","Količevo samopost.","Komenda Š","Komenda vas","Kompolje/Lukovici","Konjski vrh","Koper","Kopišča","Krašce/Moravčah","Krašnja","Križ/G.Gradu","Križ/Kamniku","Kropa/G.Gradu","Krtina","Krtina Mostnar","Krtina vas","Krtina/Dobu","Krunčiči","Kureš","Lačja vas","Lahovče","Laniše","Laniše","Laze","Laze v Tuhinju","Letališče Brnik","Ljubljana","Ljubljana AMZS","Ljubljana Belinka","Ljubljana Beričevo","Ljubljana Brinje","Ljubljana BTC","Ljubljana Črnuče","Ljubljana Hrastje","Ljubljana Kino Šiška","Ljubljana Kodrova","Ljubljana Nadgorica","Ljubljana Nove Jarše","Ljubljana Podgorica","Ljubljana Ruski car","Ljubljana Sav.nas.","Ljubljana Slov.avto","Ljubljana Sneberje","Ljubljana stad.","Ljubljana Šentjakob","Ljubljana Šentvid","Ljubljana Tivoli","Ljubljana Trata","Ljubljana Viadukt","Ljubno/Savinji","Ljubno/Savinji Alpe","Ljubno/Savinji Rore","Ločica pri Vranskem","Log.dolina PD","Log.dolina Sestre","Log.dolina slap","Loka pri Mengšu","Loke v Tuhinju","Lovreč","Lovrečica","Lucija TPC","Luče ob Savinji","Luče ob Savinji Stoglej","Lukovica pri Domžalah","Lukovica trgovina","Markovo Kavran","Mekinje","Meliše K","Mengeš Lek","Mengeš Lovec","Mengeš Pavovec","Mengeš Petrol","Mini bar","Moravče","Moste  INCE","Moste GD","Moste Petrol","Motnik","Motnik Park","Motnik zg.","Mozirje","Mozirje Celinšek","Nadlog","Nasovče","Nazarje","Negrad","Negrad Osovnik","Negrod","Nizka","Novigrad","Nožice","Okonina","OŠ Dob","OŠ Venclja Perka","Peče","Peče K","Perovo Coprnica","Petelinjek/Blagovici","Ples","Plovanija MP","Podgorica GD","Podgorje vas","Podgorje/Kamniku","Podgorje/Kamniku Slavka","Podhruška","Podmilj","Podrečje","Podrečje gostilna","Podrečje most","Poreč","Portorož","Potok","Potok/Tuhinju","Prelog","Prelog Magan","Preserje Gaj","Preserje pri Radomljah","Prevoje","Pri Amru","Pri Barbi","Pri Barbi Pavelnu","Pri Klusu","Pšata Janežič","Pšata most","Pšata mrliška vežica","Pusto Polje","Radmirje","Radmirje K","Radmirje PTT","Radomlje","Radomlje cerkev","Radomlje Opekarniška","Radomlje plinska","Radomlje Pošta","Raduha","Raduha Proše","Raduha Rogački most","Rafolče","Rečica/Savinji","Rečica/Savinji K","Remiza","Robanov kot Rogovilec","Rodica","Rodica Cerkev","Rodica OŠ","Rova Pirc","Rova Rodex","Rova/Radomljah","Rovinj","Rovinjsko selo","Rudnik K","Sečovlje MP","Sela","Selo pri Moravčah","Smrečje v Črni","Snovik","Solčava","Solčava Lašekar","Solčava Oprešnik","Sosiči","Soteska/Kamniku","Sp.Brnik","Sp.Kraše","Sp.Loke/Blagovici","Sp.Rečica/Savinji","Sp.Tuštanj","Sr.vas pri Kamniku","Srednja Vas","Stadion Mekinje","Stahovica","Stebljevk","Stegne","Stranje","Stranje Koritnik","Stranje Š","Stranje Šlebir","Strunjan","Sveta Trojica","Šentjanž/Ljubnem","Šentožbolt","Šentvid pri Lukovici","Škrjančevo","Šmarca K","Šmarca Kik","Šmarca vas","Šmartno ob Dreti","Šmartno šola","Šmartno v Tuhinju","Šmiklavž/G.Gradu","Šola Brdo","Šola Dol","Šola Domžale","Šola Dragomelj","Šola F. Albrehta","Šola Jarše","Šola Marije Vere","Šola Nevlje","Šola Rodica","Šola Roje","Šola Šmartno","Šola Toma Brejca","Šola Trzin","Špitalič/Motniku","Študa polje","Študa trgovina","Študa vas 1","Študa vas 2","Terme Snovik","Tirosek","Topole/Mengšu","Trgoavto Središka","Trnjava","Trojane","Trzin-DSO","Trzin GD","Trzin IC 1 Peske","Trzin IC 2 Špruha","Trzin IC 3 Motnica","Trzin ind.cona","Trzin Jemčeva 53","Trzin križišče","Trzin Mlake","Trzin vas","Tunjice GD","Tunjice trg.","Turnše","Turnše HŠ 25","Umag","Varpolje","Varvari","Veselka","Videm/Ljubljani","Vir Cerkev","Vir/Domžalah","Volčji Potok","Volčji Potok Arboretum","Volčji Potok Šraj","Volog","Vopovlje","Vransko","Vrh Kozjaka/Črnem Vrhu","Vrhovlje na Rafolčah K","Vrhpolje/Kamniku","Vrhpolje/Kamniku most","Vrhpolje/Moravčah","Zaboršt","Zalog","Zalog pod Trojico","Zduša","Zg.Kraše","Zg.Loke","Zg.Tuhinj Mlinar","Žaga/Moravčah","Žbandaj","Žeje","Želodnik","Žiče/Radomljah","Žičnica V.planina"};
    ProgressDialog progress;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AutoCompleteTextView VstopnaPostaja  = findViewById(R.id.VstopnaPostajaInner);
        final AutoCompleteTextView IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);
        final Button PosljiButton                  = findViewById(R.id.button);

        IzstopnaPostaja.setImeOptions(EditorInfo.IME_ACTION_DONE);


        VstopnaPostaja.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    IzstopnaPostaja.requestFocus();
                    return true;
                }
                return false;
            }
        });

        VstopnaPostaja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IzstopnaPostaja.requestFocus();
            }
        });

        IzstopnaPostaja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IzstopnaPostaja.clearFocus();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(IzstopnaPostaja.getWindowToken(), 0);

            }
        });

        IzstopnaPostaja.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    IzstopnaPostaja.clearFocus();

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(IzstopnaPostaja.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        PosljiButton.setEnabled(false);

        progress = new ProgressDialog(this);

        VstopnaPostaja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> PostajeList = Arrays.asList(Postaje);
                if (PostajeList.contains(s.toString()) && PostajeList.contains(IzstopnaPostaja.getText().toString())) {
                    PosljiButton.setEnabled(true);
                } else {
                    PosljiButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        IzstopnaPostaja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> PostajeList = Arrays.asList(Postaje);
                if (PostajeList.contains(s.toString()) && PostajeList.contains(VstopnaPostaja.getText().toString())) {
                    PosljiButton.setEnabled(true);
                } else {
                    PosljiButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1,Postaje);

        VstopnaPostaja.setAdapter(adapter);
        IzstopnaPostaja.setAdapter(adapter);

        CalendarView Datum = findViewById(R.id.DatumInner);

        //Pridobivanje vpisanih vrednosti
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
        );

        DatumString = simpleDateFormat.format(Calendar.getInstance().getTime());

        Datum.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                DatumString = dayOfMonth + "." + (month + 1) + "." + year;
            }
        });

    }

    public void sendData(View view){
        progress.setTitle(getResources().getString(R.string.Nalagam));
        progress.setMessage(getResources().getString(R.string.Cakaj));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        //Intent za prikaz voznega reda
        Intent PrikaziVozniRed = new Intent(this, VozniRedActivity.class);

        //Deklaracija vpisnih polj v activity_main
        AutoCompleteTextView VstopnaPostaja  = findViewById(R.id.VstopnaPostajaInner);
        AutoCompleteTextView IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);

        VstopnaPostaja.setThreshold(2);
        IzstopnaPostaja.setThreshold(2);


        String VstopnaPostajaString  = VstopnaPostaja.getText().toString();
        String IzstopnaPostajaString = IzstopnaPostaja.getText().toString();

        //Dodajanje vrednosti v intent za uporabo v naslednjem activityu
        PrikaziVozniRed.putExtra("VstopnaPostaja", VstopnaPostajaString);
        PrikaziVozniRed.putExtra("IzstopnaPostaja", IzstopnaPostajaString);
        PrikaziVozniRed.putExtra("Datum", DatumString);

        startActivityForResult(PrikaziVozniRed, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            progress.dismiss();
        }
    }

    public void zamenjajPostaji(View view){
        AutoCompleteTextView VstopnaPostaja = findViewById(R.id.VstopnaPostajaInner);
        AutoCompleteTextView IzstopnaPostaja = findViewById(R.id.IzstopnaPostajaInner);

        IzstopnaPostaja.clearFocus();
        VstopnaPostaja.clearFocus();

        IzstopnaPostaja.setAdapter(null);
        VstopnaPostaja.setAdapter(null);

        String temp = VstopnaPostaja.getText().toString();
        VstopnaPostaja.setText(IzstopnaPostaja.getText().toString());
        IzstopnaPostaja.setText(temp);

        IzstopnaPostaja.setAdapter(adapter);
        VstopnaPostaja.setAdapter(adapter);
    }
}
