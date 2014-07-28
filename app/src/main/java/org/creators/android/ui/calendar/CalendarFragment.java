package org.creators.android.ui.calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.R;
import org.creators.android.data.Announcement;
import org.creators.android.data.Event;
import org.creators.android.ui.common.ParseAdapter;
import org.creators.android.ui.common.Util;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class CalendarFragment extends Fragment implements ParseAdapter.ListCallbacks<Event> {
  public static final String TAG = "CalendarFragment";

  private ListView mListView;
  private ParseAdapter<Event> mAdapter;

  public CalendarFragment() {
    super();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ParseQueryAdapter.QueryFactory<Event> factory = new ParseQueryAdapter.QueryFactory<Event>() {
      @Override
      public ParseQuery<Event> create() {
        return Event.query().orderByDescending(Announcement.CREATED_AT);
      }
    };
    mAdapter = new ParseAdapter<>(getActivity(), R.layout.adapter_event, this, factory);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_calendar, null);

    mListView = (ListView) view.findViewById(R.id.events_list);
    mListView.setAdapter(mAdapter);

    return view;
  }

  @Override
  public void fillView(ParseAdapter.ViewHolder holder, Event event) {
    TextView title = (TextView) holder.get(R.id.event_title);
    TextView details = (TextView) holder.get(R.id.event_details);
    TextView date = (TextView) holder.get(R.id.event_date);
    TextView time = (TextView) holder.get(R.id.event_time);
    // TODO: actually make this a spinner. right now it's just a placeholder
    ImageView pinned = (ImageView) holder.get(R.id.event_rsvp_spinner);


    title.setText(event.getTitle());
    details.setText(event.getDetails());
    date.setText(Util.formatDate(event.getStartDate()));
    time.setText(Util.roundTimeAndFormat(event.getStartDate(), 2) + '-' + Util.roundTimeAndFormat(event.getEndDate(), 2));
  }
}
