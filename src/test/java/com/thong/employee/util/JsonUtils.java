package com.thong.employee.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static Map<Object, Object> toMap(String jsonString) {
        return new Gson().fromJson(jsonString, Map.class);
    }

    public static String readJsonString(String path) throws IOException {
        String data = IOUtils.toString(new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8);
        return new ObjectMapper().readTree(data).toString();
    }

}
