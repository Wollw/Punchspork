package com.wolliw.punchspork;

import android.app.Activity;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

public class Punchspork extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.main);
	}

	public void queryPunchfork(View v) {
		EditText queryText = (EditText)this.findViewById(R.id.query);
		Intent i = new Intent(this,SearchResultsActivity.class);
		i.putExtra("URL", queryText.getText().toString());
		startActivity(i);
	}

}
