package org.creators.android.data.model;

import com.google.common.base.Converter;
import com.google.common.base.Enums;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.creators.android.data.DataClass;
import org.creators.android.data.sync.Synchronize;

/**
 * Created by Damian Wieczorek <damianw@umich.edu> on 7/30/14.
 */
@ParseClassName(EventResponse.CLASS)
public class EventResponse extends DataClass<EventResponse> {
  public static final String CLASS = "EventResponse";

  public static final String WHO = "who";
  public static final String EVENT = "event";
  public static final String STATUS = "status";

  public EventResponse() {
    super(false);
  }

  public EventResponse(User who, Event event, Status status) {
    super(true);
    setWho(who);
    setEvent(event);
    setStatus(status);
    saveEventually();
  }

  public static ParseQuery<EventResponse> query() {
    return ParseQuery.getQuery(EventResponse.class).fromLocalDatastore();
  }

  public static ParseQuery<EventResponse> remoteQuery() {
    return ParseQuery.getQuery(EventResponse.class);
  }

  public static Synchronize<EventResponse> getSync() {
    return new Synchronize<>(new ParseQueryAdapter.QueryFactory<EventResponse>() {
      @Override
      public ParseQuery<EventResponse> create() {
        return remoteQuery();
      }
    });
  }

  public static EventResponse from(User who, Event event) {
    try {
      return query().whereEqualTo(WHO, who).whereEqualTo(EVENT, event).getFirst();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new EventResponse(who, event, Status.NO_RESPONSE);
  }

  public User getWho() {
    return getUser(WHO);
  }

  public EventResponse setWho(User who) {
    return builderPut(WHO, who);
  }

  public Event getEvent() {
    return (Event) getParseObject(EVENT);
  }

  public EventResponse setEvent(Event event) {
    return builderPut(EVENT, event);
  }

  public Status getStatus() {
    return Status.CONVERTER.convert(getString(STATUS));
  }

  public EventResponse setStatus(Status status) {
    return builderPut(STATUS, Status.CONVERTER.reverse().convert(status));
  }

  public static enum Status {
    NO_RESPONSE("no_response"),
    GOING("going"),
    MAYBE_GOING("maybe_going"),
    NOT_GOING("not_going");


    public final String val;
    Status(String val) {
      this.val = val;
    }

    public static final Converter<String, Status> CONVERTER = Enums.stringConverter(Status.class);
  }
}
