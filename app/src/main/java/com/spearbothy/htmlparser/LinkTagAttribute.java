package com.spearbothy.htmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 自定义标签的属性
 * Created by mahao on 17-3-20.
 */
public class LinkTagAttribute implements Parcelable {

    private String href;

    private boolean isShowUnderline;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeByte(isShowUnderline ? (byte) 1 : (byte) 0);
    }

    protected LinkTagAttribute(Parcel in) {
        this.href = in.readString();
        this.isShowUnderline = in.readByte() != 0;
    }

    public static final Creator<LinkTagAttribute> CREATOR = new Creator<LinkTagAttribute>() {
        @Override
        public LinkTagAttribute createFromParcel(Parcel source) {
            return new LinkTagAttribute(source);
        }

        @Override
        public LinkTagAttribute[] newArray(int size) {
            return new LinkTagAttribute[0];
        }
    };

    public LinkTagAttribute() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isShowUnderline() {
        return isShowUnderline;
    }

    public void setShowUnderline(boolean showUnderline) {
        isShowUnderline = showUnderline;
    }
}
