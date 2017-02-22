package com.hayro.androchat.websocket;

import android.os.AsyncTask;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;

/**
 * Created by hayru on 2/22/2017.
 */
public class AsyncTaskForConnection extends AsyncTask<Void, Void, String> {
  WebSocketAdapter adapter;
  boolean isWorking = true;

  public AsyncTaskForConnection(WebSocketAdapter _adapter) {
	this.adapter = _adapter;
  }

  public void setIsWorking(boolean i) {
	this.isWorking = i;
  }

  @Override
  protected String doInBackground(Void... voids) {
	start ();
	return "";
  }

  @Override
  protected void onPreExecute() {
	super.onPreExecute ();
  }

  private void start() {
	// Create a WebSocket factory. The timeout value remains 0.
	WebSocketFactory factory = new WebSocketFactory ();

	// Create a WebSocket with a socket connection timeout value.
	WebSocket ws = null;
	try {
	  ws = factory.createSocket ( "ws://10.0.2.2:8080/chatroom" );
	} catch (IOException e) {
	  e.printStackTrace ();
	  Log.e ( "WEBIS", "HATAA" + e.toString () );
	}
	ws.addListener ( adapter );
	try {
	  ws.connect ();
	} catch (WebSocketException e) {
	  e.printStackTrace ();
	  Log.e ( "WEBIS", "HATAA2" + e.toString () );

	}
	return;
  }
}
