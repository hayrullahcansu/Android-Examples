package com.hayro.androchat.websocket;

import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

/**
 * Created by hayru on 2/22/2017.
 */
public class WSAdapter {
  WebSocket ws;
  WebSocketAdapter adapter;
  AsyncTaskForConnection worker;

  public WSAdapter(WebSocketAdapter _adapter) {
	this.adapter = _adapter;
  }

  public WebSocket getWs() {
	return ws;
  }

  public void CreateSocket() {

	worker = new AsyncTaskForConnection ( this.adapter );
	worker.execute ();

  }

  public void SendText(String msg) {
	if (ws != null && ws.isOpen ()) {
	  ws.sendText ( msg );
	} else {
	  Log.e ( "WEBIS", "Error Socket is null" );
	}
  }
}
