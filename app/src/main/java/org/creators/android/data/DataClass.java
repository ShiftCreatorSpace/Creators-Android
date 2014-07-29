package org.creators.android.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.parse.ParseObject;

import org.creators.android.data.model.User;


/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public abstract class DataClass<T extends DataClass<T>> extends ParseObject implements Parcelable {
  public static final String OBJECT_ID = "objectId";
  public static final String CREATED_AT = "createdAt";
  public static final String UPDATED_AT = "updatedAt";
  public static final String ACL = "ACL";

  public DataClass() {

  }

  public T builderPut(String key, Object value) {
    put(key, value);
    return (T) this;
  }

  public User getUser(String key) {
    return (User) getParseUser(key);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(getString(OBJECT_ID));
  }

  public static final Function<ParseObject, String> GET_ID = new Function<ParseObject, String>() {
    @Override
    public String apply(ParseObject input) {
      return input.getObjectId();
    }
  };

  public static <T extends ParseObject, V> Function<T, V> getter(final String key) {
    return new Function<T, V>() {
      @Override
      public V apply(T input) {
        return (V) input.get(key);
      }
    };
  }

  public static <T extends ParseObject> Equivalence<T> equivalentOn(String key) {
    return (Equivalence<T>) Equivalence.equals().onResultOf(GET_ID);
  }

  public static <T extends ParseObject> ImmutableMap<String, T> mapping(Iterable<T> objects) {
    return Maps.uniqueIndex(objects, GET_ID);
  }

}
