package com.u1.gocashm.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * Created by speakJ on 15/7/27.
 */
public class GsonPhHelper {
    public static Gson getGson(){
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL,
                Modifier.TRANSIENT, Modifier.STATIC).create();
    }
}
