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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	InputStream is = null;
	String result = null,s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.enableDefaults();
		final EditText mobile = (EditText) findViewById(R.id.mobile1);
		final EditText password = (EditText) findViewById(R.id.pass1);
		Button login = (Button) findViewById(R.id.login1);
		Button register = (Button) findViewById(R.id.register1);
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,Registration.class);
				startActivity(i);
				
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String mob = mobile.getText().toString();
				String pass = password.getText().toString();
				if(mob.equals(null) || pass.equals(null))
				{
					Toast.makeText(getBaseContext(), "Please enter the details", Toast.LENGTH_LONG).show();
				}
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("Mobile", mob));
				nameValuePairs.add(new BasicNameValuePair("Password", pass));
				try
				{
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://192.168.230.1/locator/login.php");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
				}
				catch(Exception e)
				{
					Toast.makeText(getBaseContext(), "Details Not Found", Toast.LENGTH_LONG).show();
				}
				try
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					StringBuilder builder = new StringBuilder();
					String line = null;
					while((line=reader.readLine())!=null)
					{
						builder.append(line + '\n');
					}
					is.close();
					result = builder.toString();
					
				}
				catch(Exception e)
				{
					Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_LONG).show();
				}
				
				//Parsing Json String 
				try
				{
					JSONObject ob = new JSONObject(result);
					s = ob.getString("Password");
				}
				
				catch(Exception e)
				{
					Toast.makeText(getBaseContext(), "Failed To parse", Toast.LENGTH_LONG).show();
				}
				String valid = "1";
				if(s.equals(valid))
					Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(getBaseContext(), "Invalid Details", Toast.LENGTH_LONG).show();
				
			}
		});
	}

}
