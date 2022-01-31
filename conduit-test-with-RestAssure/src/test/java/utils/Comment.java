package utils;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    private static Map<String, Object> comment = new HashMap<>();
    static Faker faker = new Faker();

    public static String getCommentBody(){
        return faker.lorem().paragraph();
    }

    public static Map<String, Object> getComment(){
        comment.clear();
        comment.put("body", getCommentBody());

        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("comment", comment);
        return jsonObject;
    }

}
