package net.cosmio.pastak.android.redsheet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;

public class redsheet_old extends Activity{
	private String[] mStrings = { "Make New Sheet", "Sheet List", "Download Sheet", "About" };
	//private TextView textview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ListView lv=new ListView(this);
		setContentView(lv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				 android.R.layout.simple_list_item_1,mStrings);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				TextView textview=(TextView)view;
				String listtext=textview.getText().toString();
				if(listtext==mStrings[3]){
					Intent intent = new Intent(redsheet_old.this,About.class);
					intent.setAction(Intent.ACTION_VIEW);
					redsheet_old.this.startActivity(intent);
				}else if(listtext==mStrings[0]){
					Intent intent=new Intent(redsheet_old.this,Makenew.class);
					intent.setAction(Intent.ACTION_VIEW);
					redsheet_old.this.startActivity(intent);
				}else if(listtext==mStrings[1]){
					Intent intent=new Intent(redsheet_old.this,redsheet.class);
					intent.setAction(Intent.ACTION_VIEW);
					redsheet_old.this.startActivity(intent);
				}else if(listtext==mStrings[2]){
					Intent intent=new Intent(redsheet_old.this,sheetdownload.class);
					intent.setAction(Intent.ACTION_VIEW);
					redsheet_old.this.startActivity(intent);
				}
			}
		});
		// lv.setSelection(1);
		// lv.setTextFilterEnabled(true);
	}
}