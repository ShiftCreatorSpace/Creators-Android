package org.creators.android.ui.announcements;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.creators.android.data.Announcement;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class AnnouncementAdapter extends ArrayAdapter<Announcement> {
  public AnnouncementAdapter(Context context, int resource) {
    super(context, resource);
  }
}
