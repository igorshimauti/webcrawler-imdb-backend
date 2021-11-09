package br.com.webcrawler.imdb.dto;

public class CommentDto {

    private Integer id;
    private Double rate;
    private String title;
    private String description;

    public CommentDto() {

    }

    public CommentDto(Double rate, String title, String description) {
        this.rate = rate;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}