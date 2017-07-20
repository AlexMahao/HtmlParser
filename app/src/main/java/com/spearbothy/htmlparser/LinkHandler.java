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
                // 开始标签，获取对应值
                String href = attributes.getValue("href");
                String showUnderline = attributes.getValue("show_underline");
                if (TextUtils.isEmpty(showUnderline)) {
                    showUnderline = "true";
                }
                // 构造标签实体，用以保存数据
                LinkTagAttribute entity = new LinkTagAttribute();
                entity.setHref(href);
                entity.setShowUnderline(Boolean.parseBoolean(showUnderline));
                // 将解析的数据实体暂时保存到文本上 (打标记)
                output.setSpan(entity, output.length(), output.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                // 获取之前保存的标记
                LinkTagAttribute entity = getLast(output, LinkTagAttribute.class);
                if (entity != null) {
                    // 获取开始标签的位置索引
                    int start = output.getSpanStart(entity);
                    // 移除之前的标记
                    output.removeSpan(entity);
                    int end = output.length();
                    if (start != end){
                        // 设置自定义的Span
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
