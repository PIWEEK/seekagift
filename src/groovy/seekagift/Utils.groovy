package seekagift

import java.text.SimpleDateFormat
import groovy.time.TimeCategory

class Utils {

    /**
     * Parse a String to a Date
     *
     * @param d The string to parse to a date
     * @param dateFormat The format of the string. The default is 'dd/MM/yyyy'
     *
     * @return The Date object or null if the string cann't be parse
     */
    static Date parseDate(String date, String dateFormat = 'dd/MM/yyyy') {

        if (date == null) {
            return null
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat)
            // Do not convert January 32th to February 1st
            format.setLenient(false);

            return format.parse(date)

        } catch (java.text.ParseException e) {
            return null
        }
    }
}
