package org.creators.android.data;

import com.parse.ParseClassName;
import com.parse.ParseQuery;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Config.CLASS)
public class Config extends CreatorsClass<Config> {
  public static final String CLASS = "Config";

  public Config() {

  }

  public static ParseQuery<Config> query() {
    return ParseQuery.getQuery(Config.class);
  }

}
