package org.creators.android.data;

import com.parse.ParseObject;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public abstract class CreatorsClass<T extends CreatorsClass<T>> extends ParseObject {
  public static final String OBJECT_ID = "objectId";
  public static final String CREATED_AT = "createdAt";
  public static final String UPDATED_AT = "updatedAt";
  public static final String ACL = "ACL";

  public T builderPut(String key, Object value) {
    put(key, value);
    return (T) this;
  }

  public User getUser(String key) {
    return (User) getParseUser(key);
  }

}
