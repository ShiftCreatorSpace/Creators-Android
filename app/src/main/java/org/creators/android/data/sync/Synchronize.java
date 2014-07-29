package org.creators.android.data.sync;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.creators.android.data.DataClass;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/28/14.
 */
public class Synchronize<T extends ParseObject> {
  private static final int SYNC_TIMEOUT = 1;
  private static final TimeUnit SYNC_TIMEOUT_UNIT = TimeUnit.MINUTES;

  private Class<T> mClazz;

  public Synchronize(Class<T> clazz) {
    mClazz = clazz;
  }

  public void sync() throws SyncException {

    try {
      // Fetch all pinned objects and their ids
      Map<String, T> localObjects = getLocalObjects();

      // fetch all remote objects and their ids
      Map<String, T> remoteObjects = getRemoteObjects();

      MapDifference<String, T> difference = Maps.difference(remoteObjects, localObjects, DataClass.equivalentOn(DataClass.OBJECT_ID));

      // Pin all the new remote objects
      for (T object : difference.entriesOnlyOnLeft().values()) {
        object.pin();
      }

      // Unpin and delete all the objects deleted remotely
      for (T object : difference.entriesOnlyOnRight().values()) {
        object.unpin();
        object.delete();
      }

    } catch (ParseException e) {
      e.printStackTrace();
      throw new SyncException(e);
    }

  }

  private Map<String, T> getLocalObjects() {
    try {
      return DataClass.mapping(query().fromLocalDatastore().find());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new HashMap<>();
  }

  private Map<String, T> getRemoteObjects() throws ParseException {
    return DataClass.mapping(query().find());
  }

  private ParseQuery<T> query() {
    return ParseQuery.getQuery(mClazz);
  }

  public static class SyncException extends Exception {
    public SyncException() {
      super();
    }
    public SyncException(Throwable throwable) {
      super(throwable);
    }
  }

}