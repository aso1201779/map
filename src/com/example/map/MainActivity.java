package com.example.map;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWebView = (WebView)findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO 自動生成されたメソッド・スタブ
				return super.shouldOverrideUrlLoading(view, url);
			}

		});

		mWebView.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
				// TODO 自動生成されたメソッド・スタブ
				super.onGeolocationPermissionsShowPrompt(origin, callback);
				//常に位置情報を取得する
				callback.invoke(origin, true, false);
			}
		    });

		findViews();//viewの読み込み

		if(netWorkCheck(this.getApplicationContext()) ){
			WebSettings settings = mWebView.getSettings();
			settings.setJavaScriptEnabled(true);
			//Geolocationを有効化
			settings.setGeolocationEnabled(true);
			mWebView.loadUrl("file:///android_asset/html/test.html");//サイトの読み込み
		}else{
			// 確認ダイアログの生成
	        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
	        alertDlg.setTitle("エラー");
	        alertDlg.setMessage("メッセージ");
	        alertDlg.setPositiveButton(
	            "OK",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    // OK ボタンクリック処理
	                }
	            });
	        //表示
	        alertDlg.create().show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void findViews(){
		mWebView = (WebView)findViewById(R.id.webview);
	}
	//ネットワーク接続確認
	public static boolean netWorkCheck(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null){
			return info.isConnected();
		}else{
			return false;
		}
	}

}
