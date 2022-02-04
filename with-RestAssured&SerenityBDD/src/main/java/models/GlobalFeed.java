package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class GlobalFeed {
    @Getter
    private Integer articlesCount;
    @Getter
    private List<Article> articles;

}
