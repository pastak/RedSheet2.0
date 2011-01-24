package net.cosmio.pastak.android.redsheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class redsheet extends Activity{
	public Context me;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sheetlist);
		me=this;
		final String[] filenames =fileList();
		ListView lv=(ListView)findViewById(R.id.filelist);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				 android.R.layout.simple_list_item_1,filenames);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				TextView textview=(TextView)view;
				Intent intent=new Intent(me,SheetView.class);
				intent.putExtra("filename", textview.getText().toString());
				intent.setAction(Intent.ACTION_VIEW);
				redsheet.this.startActivity(intent);
			}	
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,Menu.NONE,"New")
		.setIcon(android.R.drawable.ic_menu_add);
		menu.add(0,1,Menu.NONE,"Download")
		.setIcon(android.R.drawable.ic_menu_set_as);
		menu.add(0,2,Menu.NONE,"About")
		.setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case 0:
			intent=new Intent(me,Makenew.class);
			intent.setAction(Intent.ACTION_VIEW);
			redsheet.this.startActivity(intent);
			return true;
		case 1: 
			intent=new Intent(me,sheetdownload.class);
			intent.setAction(Intent.ACTION_VIEW);
			redsheet.this.startActivity(intent);
			return true;
		case 2:
			intent=new Intent(me,About.class);
			intent.setAction(Intent.ACTION_VIEW);
			redsheet.this.startActivity(intent);
			return true;
			
	};
		return super.onOptionsItemSelected(item);
	}
}
