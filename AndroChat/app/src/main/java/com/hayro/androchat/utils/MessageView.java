package com.hayro.androchat.utils;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hayru on 2/17/2017.
 */
public class MessageView extends LinearLayout {
  private Message message;
  private Context context;

  public MessageView(Context _context) {
	super ( _context );
	this.context = _context;
  }

  public Message getMessage() {
	return message;
  }

  public void setMessage(Message message) {
	this.message = message;
  }

  public MessageView prepareView() {
	LayoutParams lp = new LayoutParams ( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
	lp.setMargins ( 10, 0, 10, 0 );
	this.setOrientation ( HORIZONTAL );
	this.setLayoutParams ( lp );
	TextView tv = new TextView ( this.context );
	lp = new LayoutParams ( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT );
	lp.gravity = Gravity.CENTER;
	tv.setText ( this.message.Client );
	tv.setTextSize ( TypedValue.COMPLEX_UNIT_DIP, 18 );
	tv.setGravity ( Gravity.BOTTOM );
	tv.setLayoutParams ( lp );

	this.addView ( tv );
	tv = new TextView ( this.context );
	lp = new LayoutParams ( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT );
	lp.gravity = Gravity.CENTER;
	tv.setText ( this.message.Content );
	tv.setTextSize ( TypedValue.COMPLEX_UNIT_DIP, 14 );
	tv.setGravity ( Gravity.RIGHT );
	tv.setLayoutParams ( lp );
	this.addView ( tv );
	return this;
  }
}
