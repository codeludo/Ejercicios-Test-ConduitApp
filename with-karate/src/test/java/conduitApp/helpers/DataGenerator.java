package conduitApp.helpers;

import com.github.javafaker.Faker;

import net.minidev.json.JSONObject;

public class DataGenerator {

    /**
     * genera unos datos falsos para crear un articulo
     * title, description, body
     * @return json object
     * 
     */
    public  static JSONObject articleBodyGenerator(){

        Faker faker = new Faker();
        String title = faker.gameOfThrones().character();
        String description = faker.gameOfThrones().city();
        String body = faker.gameOfThrones().quote();
        JSONObject data = new JSONObject();
        data.put("title", title);
        data.put("description", description);
        data.put("body", body);
        return data;
    }
    
    /**
     * datos falsos para registro de usuarios nuevos aleatorios
     * @return json con usuario aleatorio
     */
    public static JSONObject randomSignUpData(){
        Faker faker = new Faker();
        JSONObject data = new JSONObject();
        String user = faker.rickAndMorty().character();
        String userEmail = faker.random().nextInt(0, 100).toString()+
                                user+
                                faker.random().nextInt(0,55)+
                                "@gmail.com";
        String password = faker.esports().game();
        data.put("username", user);
        data.put("email", userEmail);
        data.put("password", password);
        return data;
    }
    
    public static JSONObject randomComment(){
        Faker faker = new Faker();
        JSONObject data = new JSONObject();
        String comment = faker.hobbit().quote();
        data.put("body", comment);
        return data;
    }
}
