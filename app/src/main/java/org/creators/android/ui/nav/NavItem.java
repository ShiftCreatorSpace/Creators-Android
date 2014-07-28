package org.creators.android.ui.nav;

import android.app.Fragment;
import android.app.FragmentManager;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/27/14.
 */
public class NavItem<T extends Fragment> {
  private FragmentManager mFragmentManager;
  private T mFragment;
  private Class<T> mClazz;
  private String mTitle;
  private int mIconId;

  public NavItem(FragmentManager fm, T fragment, Class<T> clazz, String title, int iconId) {
    mFragmentManager = fm;
    mFragment= fragment;
    mClazz = clazz;
    mTitle = title;
    mIconId = iconId;
  }

  public T getFragment() {
    if (mFragment == null) {
      mFragment = (T) mFragmentManager.findFragmentByTag(mClazz.getSimpleName());
      if (mFragment == null) {
        try {
          mFragment = mClazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
          e.printStackTrace();
        }
      }
    }
    return mFragment;
  }

  public String getTitle() {
    return mTitle;
  }

  public int getIconId() {
    return mIconId;
  }
}
