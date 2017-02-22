package com.hayro.androchat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hayro.androchat.R;

public class MainActivity extends AppCompatActivity {

  Button bLogin;
  EditText etNick;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate ( savedInstanceState );
	setContentView ( R.layout.activity_main );
	initialize ();
  }

  private void initialize() {
	this.bLogin = (Button) findViewById ( R.id.bLogin );
	this.etNick = (EditText) findViewById ( R.id.etNickName );
	bLogin.setOnClickListener ( new View.OnClickListener () {
	  @Override
	  public void onClick(View view) {
		if (CheckInput ( etNick.getText ().toString () )) {
		  Intent intent = new Intent ( MainActivity.this, ChatroomActivity.class );
		  intent.putExtra ( "NickName", etNick.getText ().toString ().trim () );
		  startActivity ( intent );
		  MainActivity.this.finish ();
		} else
		  Toast.makeText ( MainActivity.this, "You must enter at least 4 characters.", Toast.LENGTH_SHORT ).show ();
	  }
	} );
  }

  private boolean CheckInput(String str) {
	str = str.trim ();
	boolean result = true;
	if (str.length () < 4) return false;
	return result;
  }
}
