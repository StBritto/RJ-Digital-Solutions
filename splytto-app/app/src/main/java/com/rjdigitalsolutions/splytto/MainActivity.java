package com.rjdigitalsolutions.splytto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private WebView webView;
    private static final int PICK_IMAGES = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.loadUrl("file:///android_asset/splash.html");
    }

    public class AndroidBridge {
        @JavascriptInterface
        public void shareText(String text) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(i, "Share with"));
        }

        @JavascriptInterface
        public void openUrl(String url) {
            try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); } catch (Exception ignored) {}
        }

        @JavascriptInterface
        public void pickPhotos() {
            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i, PICK_IMAGES);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<String> uris = new ArrayList<>();
            if (data.getClipData() != null) {
                for (int i=0; i<data.getClipData().getItemCount(); i++) uris.add(data.getClipData().getItemAt(i).getUri().toString());
            } else if (data.getData() != null) uris.add(data.getData().toString());
            StringBuilder js = new StringBuilder("window.splyttoPhotosPicked && window.splyttoPhotosPicked([");
            for (int i=0;i<uris.size();i++) { if (i>0) js.append(','); js.append('\"').append(uris.get(i).replace("\"","\\\"")).append('\"'); }
            js.append("]); ");
            webView.evaluateJavascript(js.toString(), null);
        }
    }
}
