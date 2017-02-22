package com.hayro.androchat.utils;

import android.util.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by hayru on 2/17/2017.
 */
public class JSON {
  public static Message Unmarshall(String str) throws JSONException {
	Message msg = new Message ();
	try {
	  JSONObject job = new JSONObject ( str );
	  msg.Client = job.getString ( "client" );
	  msg.ContentCode = job.getInt ( "client_code" );
	  msg.Content = job.getString ( "content" );
	} catch (JSONException e) {
	  throw e;
	}
	return msg;
  }

  public static String Marshall(Message msg) throws IOException {
	StringWriter sw = new StringWriter ();
	try {
	  JsonWriter writer = new JsonWriter ( sw );
	  writer.beginObject ();
	  writer.setIndent ( "client" );
	  writer.value ( msg.Client );
	  writer.setIndent ( "client_code" );
	  writer.value ( msg.ContentCode );
	  writer.setIndent ( "content" );
	  writer.value ( msg.Content );
	  writer.endObject ();
	} catch (IOException e) {
	  throw e;
	}
	return sw.toString ();
  }
}
