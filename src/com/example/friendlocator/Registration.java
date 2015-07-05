package com.example.friendlocator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends MainActivity{

	int id = 0;
	InputStream is = null;
	String result = null,s=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		StrictMode.enableDefaults();
		final EditText name = (EditText) findViewById(R.id.uname2);
		final EditText mobile = (EditText) findViewById(R.id.mobile);
		final EditText password = (EditText) findViewById(R.id.pass2);
		final EditText conpass = (EditText) findViewById(R.id.conpass);
		Button register = (Button) findViewById(R.id.button1);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				// TODO Auto-generated method stub
				String uname = name.getText().toString();
				String umob = mobile.getText().toString();
				String upass = password.getText().toString();
				String ucon = conpass.getText().toString();
				if(uname==null || umob==null || upass==null || ucon==null)
				{
					Toast.makeText(getBaseContext(), "Please Enter Details", Toast.LENGTH_LONG).show();
				}
				else if(!upass.equals(ucon))
				{
					Toast.makeText(getBaseContext(), "Passwords Don't Match", Toast.LENGTH_LONG).show();
				}
				else
				{	
					//Toast.makeText(getBaseContext(), umob, Toast.LENGTH_LONG).show();
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("Name", uname));
					nameValuePairs.add(new BasicNameValuePair("Mobile", umob));
					nameValuePairs.add(new BasicNameValuePair("Password", upass));
					try
					{
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://192.168.230.1/locator/register.php");
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();
						is = entity.getContent();
						
					}
					catch(Exception e)
					{
						Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
					}
					try
					{
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						StringBuilder sb = new StringBuilder();
						String line = null;
						while((line=reader.readLine())!=null)
							sb.append(line + '\n');
						is.close();
						result = sb.toString();
						
					}
					catch(Exception e)
					{
						Toast.makeText(getBaseContext(), "Failure", Toast.LENGTH_LONG).show();
					}
					try{
						JSONObject json = new JSONObject(result);
						s = json.getString("Code");
					}
					catch(Exception e)
					{
						Toast.makeText(getBaseContext(), "Failed parse", Toast.LENGTH_LONG).show();
					}
					String exists = "1";
					if(s.equals(exists))
						Toast.makeText(getBaseContext(), "User Already Exists", Toast.LENGTH_LONG).show();
					
					else{
					Intent i = new Intent(Registration.this,MainActivity.class);
					startActivity(i);
					Toast.makeText(getBaseContext(), "Account Created", Toast.LENGTH_LONG).show();
					}
					
				}
				
				
			}
		});
		
	}
	
}
