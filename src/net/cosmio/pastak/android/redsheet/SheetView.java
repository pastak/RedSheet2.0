package net.cosmio.pastak.android.redsheet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class SheetView extends Activity{
	public String viewsource="";
	public String html;
	public String sheetData;
	public WebView webview;
	public String sheetFileName;
	public String EditSheetData;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sheetview);
		Intent i=getIntent();
		String sheetText=i.getStringExtra("text");
		sheetFileName=i.getStringExtra("filename");
		webview=(WebView) findViewById(R.id.webview);
		if(sheetFileName!=null){
			try{
				sheetText="";
				EditSheetData="";
				InputStream in = openFileInput(sheetFileName);
				BufferedReader reader =
				new BufferedReader(new InputStreamReader(in,"UTF-8"));
				String s;
				while((s = reader.readLine())!= null){
					sheetText=sheetText+s+"<br />";
					EditSheetData=EditSheetData+s+"\n";
				}
				reader.close();
			}catch(IOException e){}
		}
		sheetData=sheetText;
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient());
		TkFoxClient TkFox=new TkFoxClient();
		TkFox.setData(sheetData);
		webview.setWebViewClient(TkFox);
		webview.loadUrl("file:///android_asset/sheetview.htm");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,Menu.NONE,"Change Font Size");
		menu.add(0,1,Menu.NONE,"Edit")
		.setIcon(android.R.drawable.ic_menu_edit);
		menu.add(0,2,Menu.NONE,"Back to Top")
		.setIcon(android.R.drawable.ic_menu_revert);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case 0:
			new AlertDialog.Builder(SheetView.this)
	    	.setTitle("change font size")
	    	.setMessage("choose font size")
	    	.setPositiveButton("Large", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					webview.loadUrl("javascript:ChangeFontSize('30px')");
				}
	    	})
	    	.setNeutralButton("Medium", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					webview.loadUrl("javascript:ChangeFontSize('20px')");
				}
	    	})
	    	.setNegativeButton("Small", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					webview.loadUrl("javascript:ChangeFontSize('12px')");
				}
			})
	    	.show();
			return true;
		case 1: 
			intent=new Intent(SheetView.this,Editsheet.class);
			intent.putExtra("sheetText", EditSheetData);
			intent.putExtra("filename", sheetFileName);
			intent.setAction(Intent.ACTION_VIEW);
			SheetView.this.startActivity(intent);
			return true;
		case 2: 
			intent=new Intent(SheetView.this,redsheet.class);
			intent.setAction(Intent.ACTION_VIEW);
			SheetView.this.startActivity(intent);
			return true;
	}
	return super.onOptionsItemSelected(item);
	}
}
