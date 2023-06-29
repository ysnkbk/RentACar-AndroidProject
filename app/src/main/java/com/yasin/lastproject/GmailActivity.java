package com.yasin.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yasin.lastproject.databinding.ActivityGmailBinding;

public class GmailActivity extends AppCompatActivity {

    WebView webView;
    private ActivityGmailBinding binding;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.optionlogin,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.loginPage) {
            Intent intentToUpload = new Intent(GmailActivity.this, SignIn.class);
            startActivity(intentToUpload);
        } else if (item.getItemId() == R.id.registerPage) {
            Intent intentToMain = new Intent(GmailActivity.this, SingUp.class);
            startActivity(intentToMain);
            finish();
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGmailBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        Intent intent=getIntent();
        String choose=intent.getStringExtra("ChooseKey");


        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(false);


        if(choose.equals("email")){
            binding.webView.loadUrl("https://www.enterprise.com.tr/");
        }else if(choose.equals("twitter")){
            binding.webView.loadUrl("https://twitter.com/enterprisetr");
        }else if(choose.equals("facebook")){
            binding.webView.loadUrl("hhttps://www.facebook.com/enterpriseturkiye");
        }else{
            Toast.makeText(this, "Basariz", Toast.LENGTH_SHORT).show();
        }
        binding.webView.setWebViewClient(new WebViewClient());

    }


}