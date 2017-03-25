package com.spearbothy.htmlparser;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = ((TextView) findViewById(R.id.text));
        mText.setMovementMethod(LinkMovementMethod.getInstance());
        mText.setHighlightColor(Color.TRANSPARENT);
        mText.setText(fromHtml(getString(R.string.bind_invest_card_protocol)));

    }

    public static Spanned fromHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT, null, new HtmlParser(new LinkHandler()));
        } else {
            return Html.fromHtml(html, null, new HtmlParser(new LinkHandler()));
        }
    }
}
