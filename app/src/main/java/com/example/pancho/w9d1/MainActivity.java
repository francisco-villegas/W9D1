package com.example.pancho.w9d1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.pancho.w9d1.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    SQLHelper dataBaseHelper = new SQLHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WebViewClient webViewClient = new WebViewClient();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new Object()
        {
            @JavascriptInterface
            public void performClick(String strl)
            {
                try {
                    boolean isSaved;
                    User user = new User(strl);
                    isSaved =  dataBaseHelper.saveUser(user);
                    if(isSaved)
                        Toast.makeText(MainActivity.this, strl + "  Saved!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Error while saving..." , Toast.LENGTH_SHORT).show();
                }catch (Exception  e){
                    Toast.makeText(MainActivity.this, "Error while saving..." + e, Toast.LENGTH_SHORT).show();}

            }
        }, "ok");
        webView.setWebViewClient(webViewClient);
        String customHtml = "<div align=\"center\">\n" +
                "<form action=\"/action_page.php\">\n" +
                "  <textarea name=\"myTextBox\" width=\"100%\" rows=\"4\" style=\"border:3px solid #449D44;\" id=\"txtfname\">\n" +
                "\t</textarea><br><br>\n" +
                "   \t<script>\n" +
                "    function getValues() {\n" +
                "    document.getElementById(\"btnOK\").value = document.getElementById(\"txtfname\").value;}\n" +
                "    </script>\n" +
                "    <button type=\"button\" value=\"\" id=\"btnOK\" onclick=\"getValues();ok.performClick(this.value);\">Submit</button>\n" +
                "</form>\n" +
                "</div>\n";
        webView.loadData(customHtml, "text/html", "UTF-8");

    }
}