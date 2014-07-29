package org.creators.android.ui.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/28/14.
 */
public abstract class Util {
  public static final double MINUTES_PER_HOUR = 60;

  private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("EEEE, MMMM d");
  private static final SimpleDateFormat sShortTimeFormat = new SimpleDateFormat("ha");
  private static final SimpleDateFormat sLongTimeFormat = new SimpleDateFormat("h:mma");

  public static String formatDate(Date date) {
    return sDateFormat.format(date);
  }

  public static String roundTimeAndFormat(Date date, int partsPerHour) {
    Calendar calendar = roundTime(date, partsPerHour);
    SimpleDateFormat format = calendar.get(Calendar.MINUTE) == 0 ? sShortTimeFormat : sLongTimeFormat;

    return format.format(calendar.getTime());
  }

  // to round to 1hr, use partsPerHour = 1,
  // to round to 30m, use partsPerHour = 2,
  // to round to 15m, use partsPerHour = 4... etc
  public static Calendar roundTime(Date date, int partsPerHour) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(date);

    calendar.set(Calendar.MINUTE, (int) (Math.floor(calendar.get(Calendar.MINUTE) * partsPerHour / MINUTES_PER_HOUR + 0.5) * (MINUTES_PER_HOUR / partsPerHour)));
    return calendar;
  }

}