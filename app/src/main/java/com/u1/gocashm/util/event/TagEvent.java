package com.u1.gocashm.util.event;

/**
 * Created by ArcherMind on 05/05/2017.
 */

public class TagEvent {
    private String tag;
    private Object object;
    private Object arg1;
    private Object arg2;

    public TagEvent(String tag) {
        this(tag, null);
    }

    public TagEvent(String tag, Object object) {
        this(tag, object, null, null);
    }

    public TagEvent(String tag, Object object, Object arg1, Object arg2) {
        this.tag = tag;
        this.object = object;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public TagEvent(String tag, Object object, Object arg1) {
        this(tag, object, arg1, null);
    }

    public Object getArg1() {
        return arg1;
    }

    public void setArg1(Object arg1) {
        this.arg1 = arg1;
    }

    public Object getArg2() {
        return arg2;
    }

    public void setArg2(Object arg2) {
        this.arg2 = arg2;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
