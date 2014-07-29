package org.creators.android.ui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import org.creators.android.R;
import org.creators.android.data.model.Announcement;
import org.creators.android.data.model.Config;
import org.creators.android.data.model.Event;
import org.creators.android.data.model.Project;
import org.creators.android.data.model.Request;
import org.creators.android.data.model.User;

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

    Parse.enableLocalDatastore(this);
    Parse.initialize(this, getString(R.string.parse_application_id), getString(R.string.parse_client_key));
  }
}
