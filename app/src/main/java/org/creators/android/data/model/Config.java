package org.creators.android.data.model;

import android.os.Parcel;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.data.DataClass;
import org.creators.android.data.sync.Synchronize;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Config.CLASS)
public class Config extends DataClass<Config> {
  public static final String CLASS = "Config";

  public Config() {
    super(false);
  }

  public static ParseQuery<Config> query() {
    return ParseQuery.getQuery(Config.class).fromLocalDatastore();
  }

  public static ParseQuery<Config> remoteQuery() {
    return ParseQuery.getQuery(Config.class);
  }

  public static final Creator<Config> CREATOR = new Creator<Config>() {
    @Override
    public Config createFromParcel(Parcel parcel) {
      try {
        return query().fromLocalDatastore().get(parcel.readString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    public Config[] newArray(int i) {
      return new Config[0];
    }
  };

  public static Synchronize<Config> getSync() {
    return new Synchronize<>(new ParseQueryAdapter.QueryFactory<Config>() {
      @Override
      public ParseQuery<Config> create() {
        return remoteQuery();
      }
    });
  }

}
