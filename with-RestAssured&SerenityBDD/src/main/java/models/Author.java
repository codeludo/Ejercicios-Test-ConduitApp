package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Author {

    @Getter private String username;
    @Getter private String bio;
    @Getter private String image;
    @Getter private Boolean following;

}
