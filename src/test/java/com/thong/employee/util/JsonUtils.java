package com.thong.employee.util;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static Map<Object, Object> toMap(String jsonString) {
        return new Gson().fromJson(jsonString, Map.class);
    }

}
