package com.wolliw.punchspork;

import android.util.Log;
import android.graphics.drawable.Drawable;

import org.json.JSONObject;
import org.json.JSONArray;

import java.text.DecimalFormat;
import java.io.InputStream;
import java.net.URL;

/*
*	Class that holds the information for a single
*	recipe from Punchfork
*/
public class Recipe {
	private Double rating;
	private String source_name;
	private String thumb;
	private String title;
	private String source_url;
	private String pf_url;
	
	public Recipe (JSONObject recipeJSON) {
		try {
			rating = recipeJSON.getDouble("rating");
			source_name = recipeJSON.getString("source_name");
			thumb = recipeJSON.getString("thumb");
			title = recipeJSON.getString("title");
			source_url = recipeJSON.getString("source_url");
			pf_url = recipeJSON.getString("pf_url");
		} catch (Exception e) {
			rating = 0.0;
			source_name = "Error";
			thumb = "Error";
			title = "Error";
			source_url = "Error";
			pf_url = "Error";
			Log.e("Punchspork","Recipe JSON malformed");
		}
		Log.d("Punchfork","Done setting recipe info");
	}

	
    public Drawable getThumbDrawable(){
		try {
			InputStream is = (InputStream) new URL(thumb).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			Log.e("Punchspork", e.toString());
			return null;
		}
	}

	// methods that return recipe info
	public String getThumb() {
		return thumb;
	}
	public Double getRating() {
		return rating;
	}
	public String getSourceName() {
		return source_name;
	}
	public String getTitle() {
		return title;
	}
	public String getSourceURL() {
		return source_url;
	}
	public String getPFURL() {
		return pf_url;
	}

}
