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
@ParseClassName(Request.CLASS)
public class Request extends DataClass<Request> {
  public static final String CLASS = "Request";

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String REQUESTER = "requester";

  public Request() {
    super();
  }

  public static ParseQuery<Request> query() {
    return ParseQuery.getQuery(Request.class);
  }

  public String getTitle() {
    return getString(TITLE);
  }

  public Request setTitle(String title) {
    return builderPut(TITLE, title);
  }

  public String getDescription() {
    return getString(DESCRIPTION);
  }

  public Request setDescription(String description) {
    return builderPut(DESCRIPTION, description);
  }

  public User getRequester() {
    return getUser(REQUESTER);
  }

  public Request setRequester(User requester) {
    return builderPut(REQUESTER, requester);
  }

  public static final Creator<Request> CREATOR = new Creator<Request>() {
    @Override
    public Request createFromParcel(Parcel parcel) {
      try {
        return query().fromLocalDatastore().get(parcel.readString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    public Request[] newArray(int i) {
      return new Request[0];
    }
  };

  public static Synchronize<Request> getSync() {
    return new Synchronize<>(Request.class);
  }

  public static void sync() throws Synchronize.SyncException {
    getSync().sync();
  }
}