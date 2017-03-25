package com.spearbothy.htmlparser;

import android.text.Editable;
import android.text.Html;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.util.ArrayDeque;

/**
 * /**
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:truetag:htmloutput:
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:truetag:bodyoutput:
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:truetag:app_aoutput:我已阅读并同意
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:falsetag:app_aoutput:我已阅读并同意《支付服务协议》
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:truetag:app_aoutput:我已阅读并同意《支付服务协议》、
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:falsetag:app_aoutput:我已阅读并同意《支付服务协议》、《委托扣款协议》
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:falsetag:bodyoutput:我已阅读并同意《支付服务协议》、《委托扣款协议》
 * 03-20 14:24:26.392 9727-9727/com.spearbothy.htmlparser I/info: open:falsetag:htmloutput:我已阅读并同意《支付服务协议》、《委托扣款协议》
 * <p>
 * Created by mahao on 17-3-20.
 */

public class HtmlParser implements Html.TagHandler, ContentHandler {

    private final TagHandler mHandler;

    private ContentHandler mWrapperContentHandler;

    private Editable mOutput;

    private ArrayDeque<Boolean> mTagStatus = new ArrayDeque<>();


    public interface TagHandler {
        boolean handleTag(boolean opening, String tag, Editable output, Attributes attributes);
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (mWrapperContentHandler == null) {
            mOutput = output;
            mWrapperContentHandler = xmlReader.getContentHandler();
            xmlReader.setContentHandler(this);
            mTagStatus.addLast(Boolean.FALSE);
        }
    }

    public HtmlParser(TagHandler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        Log.i("info", "start:--" + "uri:" + uri + " localName:" + localName + " qName:" + qName);
        boolean isHandled = mHandler.handleTag(true, localName, mOutput, atts);
        mTagStatus.addLast(isHandled);
        if (!isHandled)
            mWrapperContentHandler.startElement(uri, localName, qName, atts);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Log.i("info", "end:--" + "uri:" + uri + " localName:" + localName + " qName:" + qName);
        if (!mTagStatus.removeLast()) {
            mWrapperContentHandler.endElement(uri, localName, qName);
        } else {
            mHandler.handleTag(false, localName, mOutput, null);
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        mWrapperContentHandler.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        mWrapperContentHandler.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        mWrapperContentHandler.endDocument();
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        mWrapperContentHandler.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        mWrapperContentHandler.endPrefixMapping(prefix);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        mWrapperContentHandler.characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        mWrapperContentHandler.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        mWrapperContentHandler.processingInstruction(target, data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        mWrapperContentHandler.skippedEntity(name);
    }
}
