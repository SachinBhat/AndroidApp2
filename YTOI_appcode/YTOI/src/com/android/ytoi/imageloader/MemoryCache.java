package com.android.ytoi.imageloader;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

public class MemoryCache {

    private static final String TAG = "MemoryCache";
    private Map<String, Bitmap> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, Bitmap>(10,1.5f,true));//Last argument true for LRU ordering
    private long limit = 0;//max memory in bytes

    public MemoryCache() {
        setLimit(0);
    }
    
    public void setLimit(long new_limit) {
        limit = new_limit;
        Log.i(TAG, "MemoryCache will use up to "+limit/1024./1024.+"MB");
    }

    

    public void clear() {
        try {
            //NullPointerException sometimes happen here http://code.google.com/p/osmdroid/issues/detail?id=78 
            cache.clear();
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}