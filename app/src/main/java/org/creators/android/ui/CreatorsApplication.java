package org.creators.android.ui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import org.creators.android.R;
import org.creators.android.data.Announcement;
import org.creators.android.data.Config;
import org.creators.android.data.Event;
import org.creators.android.data.Project;
import org.creators.android.data.Request;
import org.creators.android.data.User;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class CreatorsApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    ParseObject.registerSubclass(Announcement.class);
    ParseObject.registerSubclass(Config.class);
    ParseObject.registerSubclass(Event.class);
    ParseObject.registerSubclass(Project.class);
    ParseObject.registerSubclass(Request.class);
    ParseObject.registerSubclass(User.class);

    Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_key));
  }
}
