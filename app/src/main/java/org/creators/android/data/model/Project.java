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
@ParseClassName(Project.CLASS)
public class Project extends DataClass<Project> {
  public static final String CLASS = "Project";

  public Project() {
    super();
  }

  public static ParseQuery<Project> query() {
    return ParseQuery.getQuery(Project.class);
  }

  public static final Creator<Project> CREATOR = new Creator<Project>() {
    @Override
    public Project createFromParcel(Parcel parcel) {
      try {
        return query().fromLocalDatastore().get(parcel.readString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    public Project[] newArray(int i) {
      return new Project[0];
    }
  };

  public static Synchronize<Project> getSync() {
    return new Synchronize<>(Project.class);
  }

  public static void sync() throws Synchronize.SyncException {
    getSync().sync();
  }
}
