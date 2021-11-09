package br.com.webcrawler.imdb.dto;

public class ActorDto {

    private Integer id;
    private String name;

    public ActorDto() {

    }

    public ActorDto(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}