package com.droderino.xmpp;

import org.jivesoftware.smack.SmackAndroid;

import com.xmppclient.XMPPClientImpl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private XMPPClientImpl xmppClient;
	private EditText to;
	private EditText msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		to = (EditText)this.findViewById(R.id.editTo);
		msg = (EditText)this.findViewById(R.id.editText1);
		
		SmackAndroid.init(this);
		ConnectToServer cts = new ConnectToServer();
		cts.execute(new String[]{ "ratink.de", "5222" });
	}

	public void connect(View view) {
		
		try {
			xmppClient.login("testuser", "testuser");
			xmppClient.setStatus(true, "I'm right here, Mr. Monster");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void add(View view)
	{
		try {
			xmppClient.addBuddy(to.getText().toString(), to.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(View view)
	{
		try {
			xmppClient.sendMessage(msg.getText().toString(), to.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStop()
	{
		super.onStop();
		xmppClient.disconnect();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class ConnectToServer extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			String server = params[0];
			int port = Integer.parseInt(params[1]);
			
			xmppClient = new XMPPClientImpl(server, port);
			try {
				xmppClient.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
	}

}
