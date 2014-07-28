package org.creators.android.data;

import com.parse.ParseClassName;
import com.parse.ParseQuery;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Project.CLASS)
public class Project extends CreatorsClass<Project> {
  public static final String CLASS = "Project";

  public Project() {

  }

  public static ParseQuery<Project> query() {
    return ParseQuery.getQuery(Project.class);
  }

}
