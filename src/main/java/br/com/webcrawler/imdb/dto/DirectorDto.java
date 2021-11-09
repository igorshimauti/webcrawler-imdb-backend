package br.com.webcrawler.imdb.dto;

public class DirectorDto {

    private Integer id;
    private String name;

    public DirectorDto() {

    }

    public DirectorDto(String name) {
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