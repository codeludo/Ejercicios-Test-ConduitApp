package utils;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomArticle {


    private static List<String> tags;
    private static String title;
    private static String description;
    private static String body;
    private static Map<String, Object>  article = new HashMap<>();

    static Faker faker = new Faker();


    public static List<String> getTags() {
        return tags;
    }

    private static void setTags() {
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(faker.lorem().word());
        RandomArticle.tags = tags;
    }

    public static String getTitle() {
        return title;
    }


    private static void setTitle() {
        RandomArticle.title = faker.lorem().sentence(1, 2);
    }

    public static String getDescription() {
        return description;
    }

    private static void setDescription() {
        RandomArticle.description = faker.lorem().sentence(3);;
    }

    public static String getBody() {
        return body;
    }

    private static void setBody() {
        RandomArticle.body = faker.lorem().paragraph(5);
    }

    public static void setArticle() {
        setTags();
        setTitle();
        setDescription();
        setBody();

        article.put("tagList", getTags());
        article.put("title", getTitle());
        article.put("description", getDescription());
        article.put("body", getBody());


    }

    public static Map<String, Object> getArticle() {
        Map<String, Object>  jsonObject = new HashMap<>();
        jsonObject.put("article", article);
        return jsonObject;
    }

    public static void setUpdateArticle(){
        article.clear();
        setTags();
        setTitle();
        setDescription();

        article.put("tagList", getTags());
        article.put("title", getTitle());
        article.put("description", getDescription());
    }

    public static Map<String, Object> getUpdateArticle(){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("article", article);
        return jsonObject;
    }
}
