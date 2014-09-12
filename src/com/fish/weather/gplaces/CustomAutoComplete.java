package com.fish.weather.gplaces;


import java.util.HashMap;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomAutoComplete extends AutoCompleteTextView {

	public CustomAutoComplete(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	@Override
	protected CharSequence convertSelectionToString(Object selectedItem) {
		HashMap<String, String> hm  =(HashMap<String, String>) selectedItem;
		return hm.get("description");
	}
	
	

}
