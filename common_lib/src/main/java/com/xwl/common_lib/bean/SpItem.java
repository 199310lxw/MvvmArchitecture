package com.xwl.common_lib.bean;

/**
 * author: Administrator
 * time: 2022/10/26
 * desc:
 */
public class SpItem {
    //值类型（String：0，int：1，long：2，float：3，boolean：4）
    public static final int VALUE_TYPE_STRING = 0;
    public static final int VALUE_TYPE_INT = 1;
    public static final int VALUE_TYPE_LONG = 2;
    public static final int VALUE_TYPE_FLOAT = 3;
    public static final int VALUE_TYPE_BOOLEAN = 4;

    private String key;
    private Object value;
    private int valueType;

    public SpItem(String key, Object value) {
        this.key = key;
        this.value = value;
        this.valueType = initValueType(value);
    }

    private int initValueType(Object t) {
        int typ = -1;
        if (t instanceof String) {
            typ = VALUE_TYPE_STRING;
        } else if (t instanceof Integer) {
            typ = VALUE_TYPE_INT;
        } else if (t instanceof Long) {
            typ = VALUE_TYPE_LONG;
        } else if (t instanceof Float) {
            typ = VALUE_TYPE_FLOAT;
        } else if (t instanceof Boolean) {
            typ = VALUE_TYPE_BOOLEAN;
        }
        return typ;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String mKey) {
        this.key = mKey;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object mValue) {
        this.value = mValue;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int mValueType) {
        this.valueType = mValueType;
    }
}
