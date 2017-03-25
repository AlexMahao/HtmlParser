package com.spearbothy.htmlparser;

import android.graphics.Color;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mahao on 17-3-23.
 */

public class AppUrlSpan extends ClickableSpan implements ParcelableSpan {
    private static final int APP_URL_SPAN = 100000;
    private LinkTagAttribute entity;

    public AppUrlSpan(LinkTagAttribute entity) {
        this.entity = entity;
    }

    protected AppUrlSpan(Parcel in) {
        entity = in.readParcelable(LinkTagAttribute.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUrlSpan> CREATOR = new Creator<AppUrlSpan>() {
        @Override
        public AppUrlSpan createFromParcel(Parcel in) {
            return new AppUrlSpan(in);
        }

        @Override
        public AppUrlSpan[] newArray(int size) {
            return new AppUrlSpan[size];
        }
    };

    public LinkTagAttribute getEntity() {
        return entity;
    }

    public void setEntity(LinkTagAttribute entity) {
        this.entity = entity;
    }

    @Override
    public void onClick(View widget) {
        // 简单的弹出提示

        Log.i("info", "click");
        Toast.makeText(widget.getContext(), entity.getHref() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    //实现ParcelableSpan隐藏函数
    public int getSpanTypeIdInternal() {
        return APP_URL_SPAN;
    }

    //实现ParcelableSpan隐藏函数
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeParcelable(this.entity, flags);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.bgColor = Color.TRANSPARENT;
        ds.setUnderlineText(entity.isShowUnderline());
    }
}
