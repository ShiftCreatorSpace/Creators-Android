package org.creators.android.data;

import com.parse.ParseClassName;
import com.parse.ParseQuery;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/26/14.
 */
@ParseClassName(Request.CLASS)
public class Request extends CreatorsClass<Request> {
  public static final String CLASS = "Request";

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String REQUESTER = "requester";

  public Request() {

  }

  public static ParseQuery<Request> query() {
    return ParseQuery.getQuery(Request.class);
  }

  public String getTitle() {
    return getString(TITLE);
  }

  public Request setTitle(String title) {
    return builderPut(TITLE, title);
  }

  public String getDescription() {
    return getString(DESCRIPTION);
  }

  public Request setDescription(String description) {
    return builderPut(DESCRIPTION, description);
  }

  public User getRequester() {
    return getUser(REQUESTER);
  }

  public Request setRequester(User requester) {
    return builderPut(REQUESTER, requester);
  }

}