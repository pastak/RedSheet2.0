package net.cosmio.pastak.android.redsheet;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TkFoxClient extends WebViewClient {
	public String sheetText;
	@Override
	public void onPageFinished(WebView webview, String url) {
		webview.loadUrl("javascript:setSheet('"+sheetText+"')");
	}
	public void setData(String text){
		sheetText=text;
	}
}
