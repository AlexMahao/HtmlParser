package com.spearbothy.htmlparser;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;

import org.xml.sax.Attributes;

/**
 * Created by mahao on 17-3-20.
 */

public class LinkHandler implements HtmlParser.TagHandler {

    @Override
    public boolean handleTag(boolean opening, String tag, Editable output, Attributes attributes) {
        if (tag.equalsIgnoreCase("app_a")) {
            if (opening) {
                // 获取属性，打上标记
                String href = attributes.getValue("href");
                String showUnderline = attributes.getValue("show_underline");
                if (TextUtils.isEmpty(showUnderline)) {
                    showUnderline = "true";
                }
                LinkTagAttribute entity = new LinkTagAttribute();
                entity.setHref(href);
                entity.setShowUnderline(Boolean.parseBoolean(showUnderline));
                output.setSpan(entity, output.length(), output.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                LinkTagAttribute entity = getLast(output, LinkTagAttribute.class);
                if (entity != null) {
                    int start = output.getSpanStart(entity);// 开始位置
                    output.removeSpan(entity);
                    int end = output.length();
                    if (start != end){
                        output.setSpan(new AppUrlSpan(entity),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
            return true;
        }

        return false;
    }


    /**
     * 获取最后一个标记
     *
     * @return
     */
    private static <T> T getLast(Spanned text, Class<T> kind) {

        T[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            return objs[objs.length - 1];
        }
    }
}
