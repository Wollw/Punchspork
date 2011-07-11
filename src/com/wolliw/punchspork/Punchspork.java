package com.wolliw.punchspork;

import android.app.Activity;
import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.view.View;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONObject;
import org.json.JSONArray;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Punchspork extends Activity
{
	private JSONObject pfQuery;
	private JSONArray pfRecipes;
	private ImageView iv;
	private ListView lv;

	private String query;

	private Recipe recipe;
	private String[] recipes;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

/*		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			query = extras.getString("QUERY");
		} else {
			query = "";
		}
*/

		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
		} else {
			query = "";
		}


		//onSearchRequested();

		// Buile the query URL
		String api_key = getString(R.string.api_key);
	
		Log.d("Punchspork",api_key);
		String url = "http://api.punchfork.com/recipes?key="
			+api_key
			+"&q="+query
			+"&count="+getString(R.string.qry_count);
		// fix spaces in url for query
		String patternStr = " ";
		String replaceStr = "%20";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(url);
		url = matcher.replaceAll(replaceStr);
		Log.d("Punchspork",url);
	
		/* Query Punchfork */
		String data = getRequest(url);

		/* get the recipe array */
		try {
			pfQuery = new JSONObject(data);
			if (pfQuery.getInt("count") != 0) {
				pfRecipes = new JSONArray(pfQuery.getString("recipes"));
				recipes = new String[pfQuery.getInt("count")];
				// Built array of recipes
				for (int i = 0; i < pfQuery.getInt("count"); i++) {
					Log.d("Punchspork", "Setting Recipe: "+Integer.toString(i));
					recipe = new Recipe((JSONObject)pfRecipes.get(i));
					recipes[i] = recipe.getTitle();
				}
			}	else	{
				recipe = null;
				recipes = null;
				pfRecipes = null;
			}
		} catch (Exception e) {
			Log.e("Punchspork", "Error setting up recipe data");
		}

		if (recipes != null) {
			lv = (ListView)findViewById(R.id.resultsList);
			lv.setAdapter(
				new ArrayAdapter<String>(
					this, R.layout.result_item, recipes));
			lv.setTextFilterEnabled(true);

			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
					try {
						recipe = new Recipe((JSONObject)
							pfRecipes.get(position));
						showRecipe(recipe);
					} catch (Exception e) {
					}
				}
			});
		}


	}

	/** Open Punchfork main site in browser */
	public void openURL(View v) {
		Log.d("Punchspork","Opening Punchfork Homepage");
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("http://www.punchfork.com"));
		startActivity(i);
	}

	/** Load Recipe Activity*/
    public void showRecipe(Recipe r) {
        Intent i = new Intent(this,RecipeActivity.class);
        i.putExtra("rating", r.getRating());
        i.putExtra("source_name", r.getSourceName());
        i.putExtra("thumb", r.getThumb());
        i.putExtra("title", r.getTitle());
        i.putExtra("source_url", r.getSourceURL());
        i.putExtra("pf_url", r.getPFURL());
        startActivity(i);
    }

	/** Get Data from Punchfork */
	private String getRequest(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try{
			HttpResponse response = client.execute(request);
			return HttpHelper.request(response);
		}catch(Exception ex){
			Log.e("Punchspork","Failed to connect to Punchfork.");
			return "Failed";
		}
	}
}
