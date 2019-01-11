package leonblejc.kambusvoznired;

import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class Fetcher {
    ArrayList<Bus> fetchBuses(
            String vstopnaPostaja,
            String izstopnaPostaja,
            String datum,
            Date currDate
    ) {
        //Arraylist for buses
        ArrayList<Bus> buses = new ArrayList<>();

        //Change permissions to allow network connection on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Make connection to bus timetable website
        try {

            //Create url of requested bus timetable
            String fetchUrl = "https://www.kam-bus.si/index.php?page=ajax_pot&in_pot="
                    + vstopnaPostaja.trim().replace(' ', '+') + "&out_pot="
                    + izstopnaPostaja.trim().replace(' ', '+') + "&datum="
                    + datum           + "&mode=select";

            //Connect to url
            Document doc = Jsoup.connect(fetchUrl).get();

            //Grab table element from website
            Element table = doc.getElementsByTag("table").first();

            //Iterate through table table rows
            int i = 0;
            for (Element tableRow : table.getElementsByTag("tr")) {
                if (i > 0) {    //Skip first row, because it's metadata

                    //Collect all table data elements of row
                    Elements tableCell = tableRow.getElementsByTag("td");

                    //Create valid date from date string (datum)
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy",Locale.GERMANY);
                    Date selectedDate           = dateFormat.parse(datum);

                    //Set current date to first calendar variable
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(currDate);

                    //Set selected date to second calendar variable
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(dateFormat.parse(datum));

                    //Skip rows before current time, if it's the same day
                    if (cal.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm", Locale.GERMANY);
                        Date busOdhod               = hourFormat.parse(tableCell.get(3).text());

                        cal.setTime(currDate);
                        int hours = cal.get(Calendar.HOUR_OF_DAY);
                        int minutes = cal.get(Calendar.MINUTE);

                        Date currUra = hourFormat.parse(hours + ":" + minutes);

                        if (busOdhod.before(currUra)) {
                            continue;
                        }
                    }

                    //Create bus from data
                    Bus bus = new Bus(
                            tableCell.get(2).text(),
                            tableCell.get(1).text(),
                            tableCell.get(3).text(),
                            tableCell.get(4).text()
                    );

                    //Add bus to buses array
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