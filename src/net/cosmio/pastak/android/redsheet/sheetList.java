package net.cosmio.pastak.android.redsheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class sheetList extends Activity{
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
				sheetList.this.startActivity(intent);
			}	
		});
	}
}
