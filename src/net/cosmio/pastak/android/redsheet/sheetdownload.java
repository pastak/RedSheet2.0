package net.cosmio.pastak.android.redsheet;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;

import android.util.Xml;
import android.widget.Button;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


public class sheetdownload extends Activity{
	public String SaveFileName="";
	public Context me=this;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sheetdownload);
		final EditText edtInput=new EditText(this);
        new AlertDialog.Builder(this)
        .setTitle("Sheet Download")
        .setMessage("type your sheet id \n"+
        		"(http://tyage.sakura.ne.jp/dev/study/view?id=[here is id])")
        .setView(edtInput)
        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
        	public void onClick(DialogInterface dialog, int whichButton){
        		String sheetid=edtInput.getText().toString();
        		EditText et=(EditText)findViewById(R.id.EditText01);
        		String getHTML=getSheet(sheetid);
        		XmlPullParser xmlPullParser = Xml.newPullParser();
        		try {
					xmlPullParser.setInput(new StringReader(getHTML));
					String text="";
					for(int e = xmlPullParser.getEventType(); e != XmlPullParser.END_DOCUMENT; e = xmlPullParser.next()){
						switch(e){
						case XmlPullParser.START_TAG:
							String tagName=xmlPullParser.getName();
							if(tagName=="textarea"){
								text=text+xmlPullParser.nextText()+"\n";
								et.setText(text);
								break;
							}
						}
						}
				} catch (XmlPullParserException e) {} catch (IOException e) {}
        	}
        }).show();
        Button button=(Button)findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				EditText et=(EditText)findViewById(R.id.EditText01);
				final String s=et.getText().toString();
					final EditText edtInput=new EditText(me);
					new AlertDialog.Builder(me)
					.setTitle("file name")
			        .setMessage("plz file name to save\n(only use alphabet and number)")
			        .setView(edtInput)
			        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
			        	public void onClick(DialogInterface dialog, int whichButton){
			        		SaveFileName=edtInput.getText().toString();
			        		OutputStream out;
							try {
								out = openFileOutput(SaveFileName+".txt",MODE_PRIVATE);
								PrintWriter writer =
								new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
								writer.append(s);
								writer.close();
								new AlertDialog.Builder(me)
						    	.setTitle("Success!")
						    	.setMessage("Download New Sheet!!\n go to view this sheet?")
						    	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										Intent intent=new Intent(me,SheetView.class);
										intent.putExtra("filename", SaveFileName+".txt");
										intent.setAction(Intent.ACTION_VIEW);
										sheetdownload.this.startActivity(intent);
									}
						    	})
						    	.setNeutralButton("Back to Top", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										Intent intent=new Intent(me,redsheet.class);
										intent.setAction(Intent.ACTION_VIEW);
										sheetdownload.this.startActivity(intent);
									}
						    	})
						    	.setNegativeButton("download more", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										Intent intent=new Intent(me,sheetdownload.class);
										intent.setAction(Intent.ACTION_VIEW);
										sheetdownload.this.startActivity(intent);
									}
								})
						    	.show();
							} catch (FileNotFoundException e) {} catch (IOException e) {}
			        	}
			        }).show();
			}
		});
	}
	public String getSheet(String sheetid){
		try{
			HttpGet method=new HttpGet("http://tyage.sakura.ne.jp/dev/study/view?id="+sheetid);
			DefaultHttpClient client = new DefaultHttpClient();
			method.setHeader( "Connection", "Keep-Alive" );
			HttpResponse response = client.execute( method );
			int status=response.getStatusLine().getStatusCode();
			if(status!=HttpStatus.SC_OK){
				throw new Exception("");
			}
			return EntityUtils.toString( response.getEntity(), "UTF-8" );
		}catch ( Exception e )
	    {
	        return null;
	    }
	}	
}
