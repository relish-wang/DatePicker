package com.facebook.react.bridge;

/**
 * @author Relish Wang
 * @since 2017/12/21
 */
public interface ReadableArray {
    int size();

    ReadableMap getMap(int i);
}
