package org.creators.android.data;

import android.os.Parcel;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Event.CLASS)
public class Event extends CreatorsClass<Event> {
  public static final String CLASS = "Event";

  public static final String TITLE = "title";
  public static final String DETAILS = "details";
  public static final String START_DATE = "startDate";
  public static final String END_DATE = "endDate";
  public static final String LOCATION = "location";
  public static final String LOCATION_NAME = "locationName";
  public static final String GOING = "going";
  public static final String NOT_GOING = "notGoing";
  public static final String MAYBE_GOING = "maybeGoing";

  public Event() {
    super();
  }

  public static ParseQuery<Event> query() {
    return ParseQuery.getQuery(Event.class);
  }

  public static ParseQuery<Event> queryWithAttendance() {
    ParseQuery<Event> result = query();
    result.include(GOING);
    result.include(NOT_GOING);
    result.include(MAYBE_GOING);
    return result;
  }

  public String getTitle() {
    return getString(TITLE);
  }

  public Event setTitle(String title) {
    return builderPut(TITLE, title);
  }

  public String getDetails() {
    return getString(DETAILS);
  }

  public Event setDetails(String details) {
    return builderPut(DETAILS, details);
  }

  public Date getStartDate() {
    return getDate(START_DATE);
  }

  public Event setStartDate(Date startDate) {
    return builderPut(START_DATE, startDate);
  }

  public Date getEndDate() {
    return getDate(END_DATE);
  }

  public Event setEndDate(String endDate) {
    return builderPut(END_DATE, endDate);
  }

  public ParseGeoPoint getLocation() {
    return getParseGeoPoint(LOCATION);
  }

  public Event setLocation(ParseGeoPoint location) {
    return builderPut(LOCATION, location);
  }

  public String getLocationName() {
    return getString(LOCATION_NAME);
  }

  public Event setLocationName(String locationName) {
    return builderPut(LOCATION_NAME, locationName);
  }

  public List<User> getGoing() {
    return getList(GOING);
  }

  public List<User> getNotGoing() {
    return getList(NOT_GOING);
  }

  public List<User> getMaybeGoing() {
    return getList(MAYBE_GOING);
  }

  public static final Creator<Event> CREATOR = new Creator<Event>() {
    @Override
    public Event createFromParcel(Parcel parcel) {
      try {
        return query().fromLocalDatastore().get(parcel.readString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    public Event[] newArray(int i) {
      return new Event[0];
    }
  };

}
