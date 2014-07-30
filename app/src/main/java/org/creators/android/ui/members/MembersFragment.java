package org.creators.android.ui.members;

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
import org.creators.android.data.model.User;
import org.creators.android.ui.common.ParseAdapter;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class MembersFragment extends Fragment implements ParseAdapter.ListCallbacks<User> {
  public static final String TAG = "MembersFragment";

  private ListView mListView;
  private SwipeRefreshLayout mLayout;
  private ParseAdapter<User> mAdapter;

  public MembersFragment() {
    super();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ParseQueryAdapter.QueryFactory<User> factory = new ParseQueryAdapter.QueryFactory<User>() {
      @Override
      public ParseQuery<User> create() {
        return User.query().orderByAscending(User.FIRST_NAME);
      }
    };
    mAdapter = new ParseAdapter<User>(getActivity(), R.layout.adapter_user, this, factory);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_calendar, null);

    mListView = (ListView) mLayout.findViewById(R.id.events_list);
    mListView.setAdapter(mAdapter);

    mAdapter.bindSync(mLayout);

    return mLayout;
  }

  @Override
  public void fillView(ParseAdapter.ViewHolder holder, User user) {
    ImageView selfie = holder.get(R.id.user_selfie);
    TextView fullName = holder.get(R.id.user_full_name);

    fullName.setText(user.getFullName());
  }

}
