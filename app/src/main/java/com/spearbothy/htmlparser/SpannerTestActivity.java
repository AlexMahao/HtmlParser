package com.spearbothy.htmlparser;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by mahao on 17-3-20.
 */

public class SpannerTestActivity extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanner);
        mText = ((TextView) findViewById(R.id.text));
        mText.setText(testSpanned());
    }

    /**
     * 测试Spanned的样式
     *      关键方法setSpan(mark )，传入的第一个参数表示对字符串的处理
     *      CharacterStyle类是所有处理类的父类，其定义了一系列处理的Span子类
     *
     *
     *
     *
     * @return
     */
    public Spanned testSpanned() {
        SpannableString spanned = new SpannableString("测试文字字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站X轴综合");
        // setSpan 会将start 到 end 之间的文本设置成创建的span格式
        /**
         *  SPAN_EXCLUSIVE_EXCLUSIVE : 标识当加入新的字体到这个标记范围内，是否应用
         *      - Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 前后都不包括
         *      - Spanned.SPAN_INCLUSIVE_EXCLUSIVE 前面包括，后面不包括
         *      - Spanned.SPAN_EXCLUSIVE_INCLUSIVE
         *      - Spanned.SPAN_INCLUSIVE_INCLUSIVE (前后都包括)
         */
        // 设置字体(default,default-bold,monospace,serif,sans-serif)
        spanned.setSpan(new TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置字体大小，第二个参数表示是否是dp值，默认是px
        spanned.setSpan(new AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new AbsoluteSizeSpan(20, true), 6, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 相对大小 相对于默认字体的倍数
        spanned.setSpan(new RelativeSizeSpan(0.5f), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new RelativeSizeSpan(2f), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置字体的前景色 Magenta紫红
        spanned.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置字体的背景色
        spanned.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置字体的样式
        spanned.setSpan(new StyleSpan(Typeface.NORMAL), 18, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new StyleSpan(Typeface.BOLD), 20, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new StyleSpan(Typeface.ITALIC), 22, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 24, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置下划线
        spanned.setSpan(new UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置删除线
        spanned.setSpan(new StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置下标
        spanned.setSpan(new SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置上标
        spanned.setSpan(new SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置链接
        spanned.setSpan(new URLSpan("tel:1234556"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new URLSpan("mailto:zziamhao@163.com"), 39, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanned.setSpan(new URLSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置宽度的缩放，高度不变
        spanned.setSpan(new ScaleXSpan(2.0f), 43, 45, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanned;
    }
}
