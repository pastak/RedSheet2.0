package net.cosmio.pastak.android.redsheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Makenew extends Activity{
	public Context me=this;
	public String SaveFileName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newmake);
		LinearLayout ilayout=(LinearLayout)findViewById(R.id.LinearLayout01);
		ilayout.setOrientation(LinearLayout.VERTICAL);
		TextView text=new TextView(this);
		text.setText("赤シートにしたい部分を?で囲んで下さい");
		final EditText editor=new EditText(this);
		editor.setMinLines(10);
		editor.setMaxLines(15);
		editor.setGravity(Gravity.TOP);
		Button button=new Button(this);
		button.setText("Save");
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				final String s=editor.getText().toString();
					final EditText edtInput=new EditText(me);
					new AlertDialog.Builder(me)
					.setTitle("file name")
			        .setMessage("plz file name to save\n (only use alphabet and number)")
			        .setView(edtInput)
			        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
			        	public void onClick(DialogInterface dialog, int whichButton){
			        		SaveFileName=edtInput.getText().toString();
			        		try{
			        			OutputStream out = openFileOutput(SaveFileName+".txt",MODE_PRIVATE);
								PrintWriter writer =
								new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
								writer.append(s);
								writer.close();
								new AlertDialog.Builder(me)
						    	.setTitle("Success!")
						    	.setMessage("Make New Sheet!!\n Back to Top?")
						    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										Intent intent=new Intent(me,redsheet.class);
										intent.setAction(Intent.ACTION_VIEW);
										Makenew.this.startActivity(intent);
									}
						    	})
						    	.setNegativeButton("make more", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										editor.setText("");
									}
								})
						    	.show();
			        		}catch(IOException e){
								e.printStackTrace();
							}
			        	}	
			        }).show();
			}
		});
		ilayout.addView(text);
		ilayout.addView(editor);
		ilayout.addView(button);
	}
}
