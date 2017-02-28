package com.hayro.androchat.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.hayro.androchat.R;
import com.hayro.androchat.utils.Message;
import com.hayro.androchat.utils.MessageView;
import com.hayro.androchat.utils.JSON;
import com.hayro.androchat.websocket.WSAdapter;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hayru on 2/17/2017.
 */


public class ChatroomActivity extends Activity  {
  Button bSend;
  LinearLayout ll_messages;
  ScrollView scrollView;
  EditText etMsg;
  String nickname = "";
  WebSocketAdapter wsa;
  WSAdapter socketAdapter;
  WebSocket ws;


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
	this.scrollView = (ScrollView) findViewById ( R.id.scrollView );
	bSend.setOnClickListener ( new View.OnClickListener () {
	  @Override
	  public void onClick(View view) {
		SendMessage ();
	  }
	} );
	this.ll_messages = (LinearLayout) findViewById ( R.id.ll_messages );
	this.etMsg = (EditText) findViewById ( R.id.etMessage );
	bSend.setEnabled ( false );

/*	socketAdapter = new WSAdapter ( wsa );
	socketAdapter.CreateSocket ();*/
	// Create a WebSocket factory. The timeout value remains 0.
	new Thread ( new Runnable () {
	  @Override
	  public void run() {
		WebSocketFactory factory = new WebSocketFactory ();
		// Create a WebSocket with a socket connection timeout value.


		try {
		  ws = factory.createSocket ( "ws://10.0.2.2:8080/chatroom" );
		} catch (IOException e) {
		  e.printStackTrace ();
		  Log.e ( "WEBIS", "HATAA" + e.toString () );
		}
		ws.addListener ( new WebSocketAdapter () {
		  @Override
		  public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
			super.onConnected ( websocket, headers );
			bSend.setEnabled ( true );
			Log.e ( "WEBIS", "Bağlandık" );
		  }

		  @Override
		  public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
			super.onDisconnected ( websocket, serverCloseFrame, clientCloseFrame, closedByServer );
			bSend.setEnabled ( false );
			Log.e ( "WEBIS", "Kapattık" );
		  }

		  @Override
		  public void onTextMessage(WebSocket websocket, String text) {
			super.onTextMessage ( websocket, text );
			Message msg = null;
			try {
			  Log.e ( "WEBIS", text );
			  msg = JSON.Unmarshall ( text );
			} catch (JSONException e) {
			  e.printStackTrace ();
			  Log.e ( "WEBIS", "Error : " + e.toString () );
			}
			if (msg == null) return;

			if (msg.ContentCode == 30) {
			  SupplyServerRequests ( msg );
			} else if (msg.ContentCode >= 20 && msg.ContentCode <= 29) {
			  AddMessage ( msg );

			} else if (msg.ContentCode == 1) {
			  AddMessage ( msg );

			}

		  }

		  @Override
		  public void onError(WebSocket websocket, WebSocketException cause) {
			super.onError ( websocket, cause );
			Log.e ( "WEBIS", "Hata:" + cause.toString () );

		  }

		  @Override
		  public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) {
			super.onSendError ( websocket, cause, frame );
			Log.e ( "WEBIS", "Hata2:" + cause.toString () );
		  }
		} );
		try {
		  ws.connect ();
		} catch (WebSocketException e) {
		  e.printStackTrace ();
		  Log.e ( "WEBIS", "HATAA2" + e.toString () );

		}
	  }
	} ).start ();

  }

  private void SendMessage() {
	String input = etMsg.getText ().toString ().trim ();
	if (input.length () >= 1) {
	  Message msg = new Message ();
	  msg.Client = nickname;
	  switch (input) {
		case "-help":
		case "-list":
		  msg.ContentCode = 20;
		  break;
		default:
		  msg.ContentCode = 1;

	  }
	  msg.Content = input;
	  if (ws != null && ws.isOpen ()) try {
		ws.sendText ( JSON.Marshall ( msg ) );
	  } catch (IOException e) {
		e.printStackTrace ();
	  }
	  etMsg.setText ( "" );
	}
  }

  @Override
  protected void onDestroy() {
	super.onDestroy ();
  }

  private void SupplyServerRequests(Message msg) {
	switch (msg.Content) {
	  case "UserName":
		Message temp = new Message ();
		temp.Client = nickname;
		temp.ContentCode = 31;
		temp.Content = "UserName";
		try {
		  ws.sendText ( JSON.Marshall ( temp ) );
		} catch (IOException e) {
		  e.printStackTrace ();
		  Toast.makeText ( this, "Error : " + e.toString (), Toast.LENGTH_LONG ).show ();
		}
		break;
	}
  }

  private void AddMessage(final Message msg) {
	runOnUiThread ( new Runnable () {
	  @Override
	  public void run() {
		MessageView mw = new MessageView ( ChatroomActivity.this );
		mw.setMessage ( msg );
		ll_messages.addView ( mw.prepareView () );

		scrollView.postDelayed(new Runnable() {
		  @Override
		  public void run() {
			scrollView.fullScroll(ScrollView.FOCUS_DOWN);
		  }
		},200);
	  }
	} );

  }
}
