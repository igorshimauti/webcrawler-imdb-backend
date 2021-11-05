package br.com.webcrawler.imdb.service;


import br.com.webcrawler.imdb.model.Actor;
import br.com.webcrawler.imdb.model.Director;
import br.com.webcrawler.imdb.model.Movie;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlJsoupService {

    public HtmlJsoupService() {

    }

    public void convertHtmlToData(String html) {
        try {
            Document doc = Jsoup.parse(html);

            Elements elementDivOriginalTitle = doc.select("div[data-testid=hero-title-block__original-title] , h1[data-testid=hero-title-block__title]");
            Elements elementSpanRating = doc.select("div[data-testid=hero-rating-bar__aggregate-rating__score].AggregateRatingButton__Rating-sc-1ll29m0-2.bmbYRW > span.AggregateRatingButton__RatingScore-sc-1ll29m0-1.iTLWoV");
            Elements elementUlDirectorsAndMainCast = doc.select("ul[role=presentation].ipc-metadata-list.ipc-metadata-list--dividers-all.title-pc-list.ipc-metadata-list--baseAlt");

            if (elementDivOriginalTitle.size() > 0) {
                Movie movie = new Movie();

                if (elementDivOriginalTitle.size() > 1) {
                    movie.setName(elementDivOriginalTitle.get(1).text());
                } else {
                    movie.setName(elementDivOriginalTitle.get(0).text());
                }

                if (elementSpanRating.size() > 0) {
                    movie.setRate(Double.parseDouble(elementSpanRating.get(0).text()));
                }

                if (elementUlDirectorsAndMainCast.size() > 0) {
                    if (elementUlDirectorsAndMainCast.get(0).child(0) != null) {
                       List<Director> directors = new ArrayList<Director>();

                       for (Element e : elementUlDirectorsAndMainCast.get(0).child(0).child(1).child(0).children()) {
                           directors.add(new Director(e.child(0).text()));
                       }

                       movie.setDirectors(directors);
                    }

                    List<Actor> actors = new ArrayList<Actor>();
                    switch (elementUlDirectorsAndMainCast.get(0).children().size()) {
                        case 3:
                            for (Element e : elementUlDirectorsAndMainCast.get(0).child(2).child(1).child(0).children()) {
                                actors.add(new Actor(e.child(0).text()));
                            }
                            break;
                        case 2:
                            for (Element e : elementUlDirectorsAndMainCast.get(0).child(1).child(1).child(0).children()) {
                                actors.add(new Actor(e.child(0).text()));
                            }
                            break;
                    }
                    movie.setActors(actors);


                    /*if (elementUlDirectorsAndMainCast.get(0).child(2) != null) {
                        List<Actor> actors = new ArrayList<Actor>();

                        for (Element e : elementUlDirectorsAndMainCast.get(0).child(2).child(1).child(0).children()) {
                            actors.add(new Actor(e.child(0).text()));
                        }

                        movie.setActors(actors);
                    }*/
                }

                if (movie.getName() != null && movie.getRate() != null) {
                    Gson gson = new Gson();
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    HttpPost post = new HttpPost("http://localhost:8080/imdb/movie");
                    StringEntity postingString = new StringEntity(gson.toJson(movie));
                    post.setEntity(postingString);
                    post.setHeader("Content-type", "application/json");
                    httpClient.execute(post);
                }
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}