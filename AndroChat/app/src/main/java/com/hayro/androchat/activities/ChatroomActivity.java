package com.hayro.androchat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hayro.androchat.R;
import com.hayro.androchat.utils.JSON;
import com.hayro.androchat.utils.Message;
import com.hayro.androchat.utils.MessageView;
import com.hayro.androchat.websocket.AsyncTaskForConnection;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;


import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hayru on 2/17/2017.
 */
public class ChatroomActivity extends Activity {
  Button bSend;
  LinearLayout ll_messages;
  EditText etMsg;
  String nickname = "";
  WebSocketAdapter wsa;
  AsyncTaskForConnection worker;
  WebSocket socket;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate ( savedInstanceState );
	nickname = getIntent ().getExtras ().getString ( "NickName" );
	setContentView ( R.layout.layout_chatroom );
	initialize ();
  }

  // private final WebSocketConnection mConnection = new WebSocketConnection ();
  private void initialize() {
	this.bSend = (Button) findViewById ( R.id.bSend );
	this.ll_messages = (LinearLayout) findViewById ( R.id.ll_messages );
	this.etMsg = (EditText) findViewById ( R.id.etMessage );
	bSend.setEnabled ( false );
	wsa = new WebSocketAdapter () {
	  @Override
	  public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
		super.onConnected ( websocket, headers );
		bSend.setEnabled ( true );
		socket = websocket;
	  }

	  @Override
	  public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
		super.onDisconnected ( websocket, serverCloseFrame, clientCloseFrame, closedByServer );
		bSend.setEnabled ( false );
	  }

	  @Override
	  public void onTextMessage(WebSocket websocket, String text) {
		super.onTextMessage ( websocket, text );
		Message msg = null;
		try {
		  msg = JSON.Unmarshall ( text );
		} catch (JSONException e) {
		  e.printStackTrace ();
		  Log.e ( "WEBIS", "Error : " + e.toString () );
		}
		if (msg == null) return;

		if (msg.ContentCode == 30) {
		  SupplyServerRequests ( msg );
		} else if (msg.ContentCode >= 20 && msg.ContentCode <= 29) {
		  MessageView mw = new MessageView ( ChatroomActivity.this );
		  mw.setMessage ( msg );
		  ll_messages.addView ( mw.prepareView () );
		} else if (msg.ContentCode == 1) {
		  MessageView mw = new MessageView ( ChatroomActivity.this );
		  mw.setMessage ( msg );
		  ll_messages.addView ( mw.prepareView () );
		}
	  }

	  @Override
	  public void onError(WebSocket websocket, WebSocketException cause) {
		super.onError ( websocket, cause );
	  }

	  @Override
	  public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) {
		super.onSendError ( websocket, cause, frame );
	  }
	};

	worker = new AsyncTaskForConnection ( wsa );
	worker.execute ();

  }

  @Override
  protected void onDestroy() {
	worker.setIsWorking ( false );
	super.onDestroy ();
  }

  private void SupplyServerRequests(Message msg) {
	if (socket != null)
	  switch (msg.Content) {
		case "UserName":
		  Message temp = new Message ();
		  temp.Client = nickname;
		  temp.ContentCode = 31;
		  temp.Content = "UserName";
		  try {
			socket.sendText ( JSON.Marshall ( temp ) );
		  } catch (IOException e) {
			e.printStackTrace ();
			Toast.makeText ( this, "Error : " + e.toString (), Toast.LENGTH_LONG ).show ();
		  }
		  break;
	  }
  }
}
