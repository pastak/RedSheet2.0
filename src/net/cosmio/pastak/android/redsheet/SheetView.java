package net.cosmio.pastak.android.redsheet;
import android.app.Activity;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
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
		Button largeB=(Button)findViewById(R.id.Button01);
		largeB.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						webview.loadUrl("javascript:ChangeFontSize('30px')");
					}
				});
		Button mediumB=(Button)findViewById(R.id.Button02);
		mediumB.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						webview.loadUrl("javascript:ChangeFontSize('20px')");
					}
				});
		Button smallB=(Button)findViewById(R.id.Button03);
		smallB.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						webview.loadUrl("javascript:ChangeFontSize('12px')");
					}
				});
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
		Button editB=(Button)findViewById(R.id.Button04);
		editB.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(SheetView.this,Editsheet.class);
						intent.setAction(Intent.ACTION_VIEW);
						intent.putExtra("sheetText", EditSheetData);
						intent.putExtra("filename", sheetFileName);
						SheetView.this.startActivity(intent);
					}
				});
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient());
		TkFoxClient TkFox=new TkFoxClient();
		TkFox.setData(sheetData);
		webview.setWebViewClient(TkFox);
		webview.loadUrl("file:///android_asset/sheetview.htm");
	}
}
