package org.creators.android.ui.calendar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.R;
import org.creators.android.data.model.Event;
import org.creators.android.ui.MainActivity;
import org.creators.android.ui.common.ParseAdapter;
import org.creators.android.ui.common.Util;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class CalendarFragment extends Fragment implements ParseAdapter.ListCallbacks<Event> {
  public static final String TAG = "CalendarFragment";

  private ListView mListView;
  private SwipeRefreshLayout mLayout;
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
        return Event.query().orderByDescending(Event.CREATED_AT);
      }
    };
    mAdapter = new ParseAdapter<>(getActivity(), R.layout.adapter_event, this, factory);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_calendar, null);

    mListView = (ListView) mLayout.findViewById(R.id.events_list);
    mListView.setAdapter(mAdapter);

    mAdapter.bindSync(mLayout);
    if (getArguments().getBoolean(MainActivity.SHOULD_SYNC, false)) mAdapter.onRefresh();

    return mLayout;
  }

  @Override
  public void fillView(ParseAdapter.ViewHolder holder, Event event) {
    TextView title = holder.get(R.id.event_title);
    TextView details = holder.get(R.id.event_details);
    TextView date = holder.get(R.id.event_date);
    TextView time = holder.get(R.id.event_time);
    // TODO: actually make this a spinner. right now it's just a placeholder
    ImageView pinned = (ImageView) holder.get(R.id.event_rsvp_spinner);


    title.setText(event.getTitle());
    details.setText(event.getDetails());
    date.setText(Util.Format.formatDate(event.getStartDate()));
    time.setText(Util.Format.roundTimeAndFormat(event.getStartDate(), 2) + '-' + Util.Format.roundTimeAndFormat(event.getEndDate(), 2));
  }

}
