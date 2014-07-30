package org.creators.android.data.model;

import android.os.Parcel;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.creators.android.data.DataClass;
import org.creators.android.data.sync.Synchronize;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Announcement.CLASS)
public class Announcement extends DataClass<Announcement> {
  public static final String CLASS = "Announcement";

  public static final String TITLE = "title";
  public static final String DETAILS = "details";
  public static final String POSTER = "poster";
  public static final String PINNED = "pinned";

  public Announcement() {
    super();
  }

  public static ParseQuery<Announcement> query() {
    return ParseQuery.getQuery(Announcement.class).fromLocalDatastore();
  }

  public static ParseQuery<Announcement> remoteQuery() {
    return ParseQuery.getQuery(Announcement.class);
  }

  public String getTitle() {
    return getString(TITLE);
  }

  public Announcement setTitle(String title) {
    return builderPut(TITLE, title);
  }

  public String getDetails() {
    return getString(DETAILS);
  }

  public Announcement setDetails(String details) {
    return builderPut(DETAILS, details);
  }

  public boolean isPinned() {
    return getBoolean(PINNED);
  }

  public Announcement setPinned(boolean pinned) {
    return builderPut(PINNED, pinned);
  }

  public User getPoster() {
    return getUser(POSTER);
  }

  public Announcement setPoster(User poster) {
    return builderPut(POSTER, poster);
  }

  public static final Creator<Announcement> CREATOR = new Creator<Announcement>() {
    @Override
    public Announcement createFromParcel(Parcel parcel) {
      try {
        return query().fromLocalDatastore().get(parcel.readString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    public Announcement[] newArray(int i) {
      return new Announcement[0];
    }
  };

  public static Synchronize<Announcement> getSync() {
    return new Synchronize<>(Announcement.class);
  }

}
