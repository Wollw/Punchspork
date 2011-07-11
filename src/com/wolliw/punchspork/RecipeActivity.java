package com.wolliw.punchspork;

import android.app.Activity;
import android.os.Bundle;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.net.Uri;

import org.json.JSONObject;
import org.json.JSONArray;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.InputStream;
import java.net.URL;

public class RecipeActivity extends Activity
{
	private ImageView iv;
	private TextView tv;
	private Bundle extras;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipeactivity);

		extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		try {
			iv = (ImageView) this.findViewById(R.id.thumb);
			iv.setImageDrawable(thumbnail(extras.getString("thumb")));
		} catch (Exception e) {
			Log.e("Punchspork","Error displaying recipe image.");
		}

		try {
			tv = (TextView) this.findViewById(R.id.title);
			tv.setText(extras.getString("title"));
		} catch (Exception e) {
			Log.e("Punchspork","Error displaying recipe title.");
		}

		try {
			tv = (TextView) this.findViewById(R.id.source_name);
			tv.setText(extras.getString("source_name"));
		} catch (Exception e) {
			Log.e("Punchspork","Error displaying recipe source name.");
		}

		try {
			tv = (TextView) this.findViewById(R.id.rating);
			tv.setText(Double.toString(extras.getDouble("rating")));
		} catch (Exception e) {
			Log.e("Punchspork","Error displaying recipe title.");
		}

	}

	/** Open source_url in browser */
	public void openURL(View v) {
		Log.d("Punchspork",extras.getString("source_url"));
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(extras.getString("source_url")));
		startActivity(i);
	}

	private Drawable thumbnail (String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			Log.e("Punchspork", e.toString());
			return null;
		}
	}

}
