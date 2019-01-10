package leonblejc.kambusvoznired;

import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Fetcher {
    public ArrayList<Bus> fetchBuses(
            String vstopnaPostaja,
            String izstopnaPostaja,
            String datum,
            Date danasnjiDatum
    ) {
        ArrayList<Bus> buses = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            String fetchUrl = "https://www.kam-bus.si/index.php?page=ajax_pot&in_pot="
                    + vstopnaPostaja.trim().replace(' ', '+') + "&out_pot="
                    + izstopnaPostaja.trim().replace(' ', '+') + "&datum="
                    + datum           + "&mode=select";
            Document doc = Jsoup.connect(fetchUrl).get();


            Element table = doc.getElementsByTag("table").first();

            int i = 0;
            for (Element tableRow : table.getElementsByTag("tr")) {
                if (i > 0) {
                    Elements tableCell = tableRow.getElementsByTag("td");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    Date selectedDate           = dateFormat.parse(datum);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(danasnjiDatum);

                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(dateFormat.parse(datum));

                    if (cal.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm");
                        Date busOdhod               = hourFormat.parse(tableCell.get(3).text());

                        cal.setTime(danasnjiDatum);
                        int hours = cal.get(Calendar.HOUR_OF_DAY);
                        int minutes = cal.get(Calendar.MINUTE);

                        Date currUra = hourFormat.parse(hours + ":" + minutes);

                        if (busOdhod.before(currUra)) {
                            continue;
                        }
                    }

                    Bus bus = new Bus(
                            tableCell.get(2).text(),
                            tableCell.get(1).text(),
                            tableCell.get(3).text(),
                            tableCell.get(4).text()
                    );

                    buses.add(bus);
                }

                i ++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buses;
    }
}