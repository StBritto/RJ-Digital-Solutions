package com.rjdigitalsolutions.splytto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private WebView webView;
    private static final int PICK_IMAGES = 1002;
    private static final int PICK_RECEIPT = 1003;
    private static final int CAPTURE_RECEIPT = 1004;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this); setContentView(webView);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient()); webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidBridge");
        webView.loadUrl("file:///android_asset/splash.html");
    }

    public class AndroidBridge {
        @JavascriptInterface public void shareText(String text) {
            Intent i=new Intent(Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(Intent.EXTRA_TEXT,text);
            startActivity(Intent.createChooser(i,"Share with"));
        }
        @JavascriptInterface public void openUrl(String url) {
            try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); } catch(Exception ignored){}
        }
        @JavascriptInterface public void pickPhotos() {
            Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT); i.setType("image/*"); i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            i.addCategory(Intent.CATEGORY_OPENABLE); startActivityForResult(i,PICK_IMAGES);
        }
        @JavascriptInterface public void pickReceipt() {
            Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT); i.setType("image/*"); i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i,PICK_RECEIPT);
        }
        @JavascriptInterface public void captureReceipt() {
            Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(i.resolveActivity(getPackageManager())!=null) startActivityForResult(i,CAPTURE_RECEIPT);
            else sendScanError("No camera app is available.");
        }
    }

    private void recognize(InputImage image) {
        runOnUiThread(() -> webView.evaluateJavascript("window.splyttoScanStarted&&window.splyttoScanStarted()",null));
        TextRecognizer recognizer=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        recognizer.process(image)
            .addOnSuccessListener(result -> {
                String safe=result.getText().replace("\\","\\\\").replace("'","\\'").replace("\n","\\n").replace("\r","");
                webView.evaluateJavascript("window.splyttoReceiptText&&window.splyttoReceiptText('"+safe+"')",null);
            })
            .addOnFailureListener(e -> sendScanError("We couldn't read that receipt. Try a clearer photo."));
    }
    private void sendScanError(String message) {
        runOnUiThread(() -> {
            String safe=message.replace("'","\\'");
            webView.evaluateJavascript("window.splyttoScanError&&window.splyttoScanError('"+safe+"')",null);
        });
    }

    @Override protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode!=RESULT_OK || data==null) return;
        if(requestCode==PICK_IMAGES){
            ArrayList<String> uris=new ArrayList<>();
            if(data.getClipData()!=null) for(int i=0;i<data.getClipData().getItemCount();i++) uris.add(data.getClipData().getItemAt(i).getUri().toString());
            else if(data.getData()!=null) uris.add(data.getData().toString());
            StringBuilder js=new StringBuilder("window.splyttoPhotosPicked&&window.splyttoPhotosPicked([");
            for(int i=0;i<uris.size();i++){if(i>0)js.append(',');js.append("'").append(uris.get(i).replace("'","\\'")).append("'");}
            js.append("]);"); webView.evaluateJavascript(js.toString(),null);
        } else if(requestCode==PICK_RECEIPT && data.getData()!=null){
            try { recognize(InputImage.fromFilePath(this,data.getData())); } catch(IOException e){ sendScanError("Could not open that image."); }
        } else if(requestCode==CAPTURE_RECEIPT){
            Bitmap bitmap=data.getExtras()==null?null:(Bitmap)data.getExtras().get("data");
            if(bitmap!=null) recognize(InputImage.fromBitmap(bitmap,0)); else sendScanError("No photo was captured.");
        }
    }

    @Override public void onBackPressed() {
        if(webView!=null && webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }
}