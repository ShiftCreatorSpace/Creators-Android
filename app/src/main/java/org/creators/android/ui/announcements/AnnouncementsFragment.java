package org.creators.android.ui.announcements;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;

import org.creators.android.R;
import org.creators.android.data.Announcement;
import org.creators.android.ui.common.ParseAdapter;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class AnnouncementsFragment extends Fragment implements ParseAdapter.ListCallbacks<Announcement> {
  public static final String TAG = "AnnouncementsFragment";

  private ListView mListView;
  private ParseAdapter<Announcement> mAdapter;

  public AnnouncementsFragment() {
    super();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_announcements, null);

    mListView = (ListView) view.findViewById(R.id.announcements_list);

    ParseQuery<Announcement> query = Announcement.query()
      .orderByDescending(Announcement.CREATED_AT);
    mAdapter = new ParseAdapter<>(getActivity(), R.layout.adapter_announcement, this, query);
    mListView.setAdapter(mAdapter);

    return view;
  }

  @Override
  public void fillView(ParseAdapter.ViewHolder holder, Announcement announcement) {
    TextView title = (TextView) holder.get(R.id.announcement_title);
    TextView details = (TextView) holder.get(R.id.announcement_details);
    ImageView pinned = (ImageView) holder.get(R.id.announcement_pinned_icon);

    title.setText(announcement.getTitle());
    details.setText(announcement.getDetails());
    pinned.setVisibility(announcement.isPinned() ? View.VISIBLE : View.GONE);
  }
}
