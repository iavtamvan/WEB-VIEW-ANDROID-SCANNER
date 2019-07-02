package com.iavariav.androidscanner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pub.devrel.easypermissions.EasyPermissions;

public class WebViewActivity extends AppCompatActivity {
    private WebView activityMainWebview;
    private String url = "https://ituteams.firebaseapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodRequiresTwoPermission(WebViewActivity.this);
            }
        });
        activityMainWebview = findViewById(R.id.activity_main_webview);

        // Enable Javascript
        WebSettings webSettings = activityMainWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        activityMainWebview.loadUrl(url);
        // TODO handle jumping to chrome
        activityMainWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });

//        edtWebviewUrl.setText(url);
//        edtWebviewUrl.setFocusable(true);
//        edtWebviewUrl.setEnabled(false);

    }
    private static final int RC_CAMERA_AND_LOCATION = 100;
    public static final void methodRequiresTwoPermission(Context context) {
        String[] perms = {
                Manifest.permission.INTERNET, Manifest.permission.CAMERA
        };
        if (EasyPermissions.hasPermissions(context, perms)) {
            Log.d("Perms", "methodRequiresTwoPermission: Succes Perms");
            context.startActivity(new Intent(context, AndroidScannerActivity.class));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

}
