package org.creators.android.data;

import com.parse.ParseClassName;
import com.parse.ParseQuery;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Announcement.CLASS)
public class Announcement extends CreatorsClass<Announcement> {
  public static final String CLASS = "Announcement";

  public static final String TITLE = "title";
  public static final String DETAILS = "details";
  public static final String POSTER = "poster";
  public static final String PINNED = "pinned";

  public Announcement() {

  }

  public static ParseQuery<Announcement> query() {
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

}
