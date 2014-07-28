package org.creators.android.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class ParseAdapter<T extends ParseObject> extends ArrayAdapter<T> {
  private final int mResId;
  private final ListCallbacks<T> mCallbacks;
  private ParseQuery<T> mQuery;

  public ParseAdapter(Context context, int resource, ListCallbacks<T> callbacks) {
    super(context, resource);
    mResId = resource;
    mCallbacks = callbacks;
    mQuery = null;
  }

  public ParseAdapter(Context context, int resource, ListCallbacks<T> callbacks, ParseQuery<T> query) {
    this(context, resource, callbacks);
    mQuery = query;
    reload();
  }

  public boolean reload() {
    if (mQuery == null) {
      clear();
      return false;
    }
    mQuery.findInBackground(new FindCallback<T>() {
      @Override
      public void done(List<T> ts, ParseException e) {
        if (e != null) {
          e.printStackTrace();
          return;
        }
        clear();
        addAll(ts);
        notifyDataSetChanged();
      }
    });
    return true;
  }

  public boolean reload(ParseQuery<T> query) {
    mQuery = query;
    return reload();
  }

  public ParseQuery<T> getQuery() {
    return mQuery;
  }

  public int getResId() {
    return mResId;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      view = inflater.inflate(mResId, null);
    }

    mCallbacks.fillView(ViewHolder.from(view), getItem(position));

    return view;
  }

  public static class ViewHolder {
    public final View root;
    private final Map<Integer, View> mMap = new ConcurrentHashMap<>();

    private ViewHolder(View root) {
      this.root = root;
      this.root.setTag(this);
    }

    public static ViewHolder from(View view) {
      ViewHolder holder = (ViewHolder) view.getTag();
      if (holder == null) {
        holder = new ViewHolder(view);
      }
      return holder;
    }

    public View get(int id) {
      if (!mMap.containsKey(id)) {
        mMap.put(id, root.findViewById(id));
      }
      return mMap.get(id);
    }
  }

  public static interface ListCallbacks<T extends ParseObject> {
    public void fillView(ViewHolder holder, T t);
  }
}
