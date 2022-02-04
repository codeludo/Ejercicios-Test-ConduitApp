package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class Article {

    @Getter private String slug;
    @Getter private String title;
    @Getter private String description;
    @Getter private String body;
    @Getter private Date createdAt;
    @Getter private Date updatedAt;
    @Getter private List<String> tagList;
    @Getter private Author author;
    @Getter private Integer favoritesCount;
    @Getter private Boolean favorited;

}
