package net.cosmio.pastak.android.redsheet;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Editsheet extends Activity{
	public EditText editor;
	public String SaveFileName;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editsheet);
		editor=(EditText)findViewById(R.id.EditText01);
		Intent i=getIntent();
		String sheetText=i.getStringExtra("sheetText");
		SaveFileName=i.getStringExtra("filename");
		editor.setText(sheetText);
		Button updateB=(Button)findViewById(R.id.Button01);
		updateB.setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Editsheet.this.Save();
				}
			});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,Menu.NONE,"save")
		.setIcon(android.R.drawable.ic_menu_save);
		menu.add(0,1,Menu.NONE,"Back to View")
		.setIcon(android.R.drawable.ic_menu_revert);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
		case 0:
			Editsheet.this.Save();
			return true;
		case 1: 
			Intent intent=new Intent(Editsheet.this,SheetView.class);
			intent.putExtra("filename", SaveFileName);
			intent.setAction(Intent.ACTION_VIEW);
			Editsheet.this.startActivity(intent);
			return true;
	}
	return super.onOptionsItemSelected(item);
	}
	public void Save(){
		try{
			String s=editor.getText().toString();
			OutputStream out = openFileOutput(SaveFileName,MODE_PRIVATE);
			PrintWriter writer =
			new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
			writer.append(s);
			writer.close();
			new AlertDialog.Builder(Editsheet.this)
	    	.setTitle("Success!")
	    	.setMessage("Update Sheet!!")
	    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent=new Intent(Editsheet.this,SheetView.class);
					intent.setAction(Intent.ACTION_VIEW);
					intent.putExtra("filename", SaveFileName);
					Editsheet.this.startActivity(intent);
				}
	    	}).show();
		}catch (Exception e) {}
	}
}
