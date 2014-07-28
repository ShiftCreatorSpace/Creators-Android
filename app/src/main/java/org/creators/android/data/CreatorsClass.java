package org.creators.android.data;

import com.parse.ParseObject;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public abstract class CreatorsClass<T extends CreatorsClass<T>> extends ParseObject {

  public T builderPut(String key, Object value) {
    put(key, value);
    return (T) this;
  }

  public User getUser(String key) {
    return (User) getParseUser(key);
  }

}
