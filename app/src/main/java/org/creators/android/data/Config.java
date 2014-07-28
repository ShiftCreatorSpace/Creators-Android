package org.creators.android.data;

import android.os.Parcel;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Config.CLASS)
public class Config extends CreatorsClass<Config> {
  public static final String CLASS = "Config";

  public Config() {
    super();
  }

  public static ParseQuery<Config> query() {
    return ParseQuery.getQuery(Config.class);
  }

  public static final Creator<Config> CREATOR = new Creator<Config>() {
    @Override
    public Config createFromParcel(Parcel parcel) {
      try {
        return query().get(parcel.readString());
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
}
