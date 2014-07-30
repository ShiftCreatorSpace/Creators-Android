package org.creators.android.data.model;

import android.os.Parcel;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.data.DataClass;
import org.creators.android.data.sync.Synchronize;

import java.util.Date;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Event.CLASS)
public class Event extends DataClass<Event> {
  public static final String CLASS = "Event";

  public static final String TITLE = "title";
  public static final String DETAILS = "details";
  public static final String START_DATE = "startDate";
  public static final String END_DATE = "endDate";
  public static final String LOCATION = "location";
  public static final String LOCATION_NAME = "locationName";

  public Event() {
    super(false);
  }

  public static ParseQuery<Event> query() {
    return ParseQuery.getQuery(Event.class).fromLocalDatastore();
  }

  public static ParseQuery<Event> remoteQuery() {
    return ParseQuery.getQuery(Event.class);
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

  public static Synchronize<Event> getSync() {
    return new Synchronize<>(new ParseQueryAdapter.QueryFactory<Event>() {
      @Override
      public ParseQuery<Event> create() {
        return remoteQuery();
      }
    });
  }

}
