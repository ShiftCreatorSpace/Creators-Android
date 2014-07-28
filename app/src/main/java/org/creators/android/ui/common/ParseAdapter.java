package org.creators.android.ui.common;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.data.CreatorsClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class ParseAdapter<T extends CreatorsClass<T>> extends BaseAdapter {
  public static final String TAG = "ParseAdapter";
  public static final String ITEMS = "items";

  private final int mResId;
  private final Context mContext;
  private final ListCallbacks<T> mCallbacks;
  private final ArrayList<T> mItems = new ArrayList<>();
  private ParseQueryAdapter.QueryFactory<T> mQueryFactory;

  public ParseAdapter(Context context, int resource, ListCallbacks<T> callbacks) {
    mContext = context;
    mResId = resource;
    mCallbacks = callbacks;
    mQueryFactory = null;
  }

  public ParseAdapter(Context context, int resource, ListCallbacks<T> callbacks, List<T> items) {
    this(context, resource, callbacks);
    mItems.addAll(items);
  }

  public ParseAdapter(Context context, int resource, ListCallbacks<T> callbacks, ParseQueryAdapter.QueryFactory<T> queryFactory) {
    this(context, resource, callbacks);
    mQueryFactory = queryFactory;
    load();
  }

  public ParseAdapter(Context context, int resource, ListCallbacks<T> callbacks, List<T> items, ParseQueryAdapter.QueryFactory<T> queryFactory) {
    this(context, resource, callbacks, items);
    mQueryFactory = queryFactory;
    load();
  }

  public boolean load() {
    return load(true);
  }

  public boolean load(final boolean locally) {
    if (mQueryFactory == null) {
      clear();
      return false;
    }
    ParseQuery<T> query = mQueryFactory.create();
    if (locally) query.fromLocalDatastore();
    query.findInBackground(new FindCallback<T>() {
      @Override
      public void done(List<T> ts, ParseException e) {
        if (e != null) {
          e.printStackTrace();
          return;
        }
        clear();
        addAll(ts);
        notifyDataSetChanged();
        if (locally) load(false);
      }
    });
    return true;
  }

  private void add(T t) {
    mItems.add(t);
  }

  private void addAll(List<T> ts) {
    mItems.addAll(ts);
  }

  public void merge(List<Parcelable> list) {
    for (Parcelable parcelable : list) {
      add((T) parcelable);
    }
  }

  private void clear() {
    mItems.clear();
  }

  public boolean load(ParseQueryAdapter.QueryFactory<T> queryFactory) {
    mQueryFactory = queryFactory;
    return load();
  }

  public ParseQueryAdapter.QueryFactory<T> getQueryFactory() {
    return mQueryFactory;
  }

  public int getResId() {
    return mResId;
  }

  @Override
  public int getCount() {
    return mItems.size();
  }

  @Override
  public T getItem(int i) {
    return mItems.get(i);
  }

  public ArrayList<T> getItems() {
    return mItems;
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      view = inflater.inflate(mResId, null);
    }

    if (mCallbacks != null) mCallbacks.fillView(ViewHolder.from(view), getItem(position));

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
