package com.hramyko.finalapp.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hramyko.finalapp.entity.*;

public class JsonParser {

    public static String getInfoFromJson(String jsonString, String target) {
        String[] strings = jsonString.split(",|}");
        for (String s:
                strings) {
            if (s.contains(target))
            {
                jsonString = s;
            }
        }
        strings = jsonString.split(":");
        jsonString = strings[1].replaceAll(" ", "").replaceAll("\"", "");
        if (jsonString.contains("\n")) {
            jsonString = jsonString.substring(0, jsonString.indexOf("\n") - 1);
        }
        return jsonString;
    }

    public static Object getObjectFromJson(String jsonString, String className) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object object = getObject(className);
        if (object == null) return null;
        try {
            object = objectMapper.readValue(jsonString, object.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static Object getObject(String name) {
        switch(name) {
            case "com.hramyko.finalapp.entity.Post":
                return new Post();
            case "com.hramyko.finalapp.entity.User":
                return new User();
            case "com.hramyko.finalapp.entity.GameObject":
                return new GameObject();
            case "com.hramyko.finalapp.entity.Comment":
                return new Comment();
            case "com.hramyko.finalapp.entity.Game":
                return new Game();
            default:
                return null;
        }
    }
}
