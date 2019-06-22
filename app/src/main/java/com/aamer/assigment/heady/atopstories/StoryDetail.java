package com.aamer.assigment.heady.atopstories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class StoryDetail extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_story_detail);

        webView=findViewById(R.id.articleWebView);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress)
                {
                    //Make the bar disappear after URL is loaded, and changes string to Loading...
                    setTitle("Loading...");
                    setProgress(progress * 100); //Make the bar disappear after URL is loaded

                    // Return the app name after finish loading
                    if(progress == 100)
                        setTitle(R.string.app_name);
                }
            });

            String articleURL =bundle.getString("articleURL");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(articleURL);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}
