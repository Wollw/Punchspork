package com.wolliw.punchspork;

import android.content.Context;
import android.widget.ArrayAdapter;

public class QueryAdapter extends ArrayAdapter {
	public QueryAdapter(Context context, List items) {
		super(context, R.layout.result_item, items);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		String s = getItem(position);
		boolean isResult = true;
		if ( s == null )
			s = "";
		if (s.
}
