package com.example.map;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class Dmap extends Activity implements LocationListener ,View.OnClickListener{

	private WebView mWebView;
	private LocationManager mLocationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ロケーションマネージャを取得
		mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		setContentView(R.layout.dmap);
		mWebView = (WebView)findViewById(R.id.webview);
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO 自動生成されたメソッド・スタブ
				return super.shouldOverrideUrlLoading(view, url);
			}

		});



	}

	@Override
	protected void onResume() {

		Button MapEndBtn = (Button)findViewById(R.id.MapEndBtn);
		MapEndBtn.setOnClickListener(this);


		// TODO 自動生成されたメソッド・スタブ
		if(mLocationManager != null){
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER,
					//LocationManager.NETWORK_PROVIDER,
					5000,
					0,
					this);
		}
		chkGpsService();
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
		super.onResume();
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

	//GPSが有効かチェック
	//無効になっていれば、設定画面の表示確認ダイアログ
	private void chkGpsService(){
		//GPSセンサーが利用可能か？
		if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setMessage("GPSが有効になっていません。\n有効化しますか？")
			.setCancelable(false)

			//GPS設定画面起動用ボタンとイベントの定義
			.setPositiveButton("GPS設定起動",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							// TODO 自動生成されたメソッド・スタブ
							Intent callGPSSettingIntent = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(callGPSSettingIntent);
						}
			});
			//キャンセルボタン処理
			alertDialogBuilder.setNegativeButton("キャンセル",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// TODO 自動生成されたメソッド・スタブ
							dialog.cancel();
						}
					});
			AlertDialog alert = alertDialogBuilder.create();
			//設定画面へ移動するかの問い合わせダイアログを表示
			alert.show();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){
			case R.id.MapEndBtn:
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setMessage("ドライブを終了しますか？")
				.setCancelable(false)

				//GPS設定画面起動用ボタンとイベントの定義
				.setPositiveButton("終了",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								Intent intent = new Intent(Dmap.this,D_entry.class);
								startActivity(intent);
							}
				});
				//キャンセルボタン処理
				alertDialogBuilder.setNegativeButton("キャンセル",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO 自動生成されたメソッド・スタブ
								dialog.cancel();
							}
						});
				AlertDialog alert = alertDialogBuilder.create();
				//設定画面へ移動するかの問い合わせダイアログを表示
				alert.show();
				break;
		}
	}

}
