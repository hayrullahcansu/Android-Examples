package com.hayro.androchat.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by hayru on 2/17/2017.
 */
public class JSON {
  public static Message Unmarshall(String str) throws JSONException {
	Message msg = new Message ();
	try {
	  JSONObject job = new JSONObject ( str );
	  msg.Client = job.getString ( "client" );
	  msg.ContentCode = job.getInt ( "content_code" );
	  msg.Content = job.getString ( "content" );
	} catch (JSONException e) {
	  throw e;
	}
	return msg;
  }

  public static String Marshall(Message msg) throws IOException {
	JSONObject json = new JSONObject ();
	try {
	  json.put ( "client", msg.Client );
	  json.put ( "content_code", msg.ContentCode );
	  json.put ( "content", msg.Content );
	} catch (JSONException e) {
	  e.printStackTrace ();
	}
	return json.toString ();
  }
}
