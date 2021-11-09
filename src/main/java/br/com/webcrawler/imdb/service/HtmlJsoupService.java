package br.com.webcrawler.imdb.service;


import br.com.webcrawler.imdb.dto.ActorDto;
import br.com.webcrawler.imdb.dto.CommentDto;
import br.com.webcrawler.imdb.dto.DirectorDto;
import br.com.webcrawler.imdb.dto.MovieDto;
import br.com.webcrawler.imdb.model.Actor;
import br.com.webcrawler.imdb.model.Comment;
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

    public void convertHtmlToData(String html, String idImdb) {
        try {
            Document doc = Jsoup.parse(html);

            Elements elementDivOriginalTitle = doc.select("div[data-testid=hero-title-block__original-title] , h1[data-testid=hero-title-block__title]");
            Elements elementSpanRating = doc.select("div[data-testid=hero-rating-bar__aggregate-rating__score].AggregateRatingButton__Rating-sc-1ll29m0-2.bmbYRW > span.AggregateRatingButton__RatingScore-sc-1ll29m0-1.iTLWoV");
            Elements elementUlDirectorsAndMainCast = doc.select("ul[role=presentation].ipc-metadata-list.ipc-metadata-list--dividers-all.title-pc-list.ipc-metadata-list--baseAlt");
            Elements elementDivReviews = doc.select("div.review-container");

            if (elementDivOriginalTitle.size() > 0) {
                MovieDto movieDto = new MovieDto();
                movieDto.setIdImdb(idImdb);

                if (elementDivOriginalTitle.size() > 1) {
                    movieDto.setName(elementDivOriginalTitle.get(1).text());
                } else {
                    movieDto.setName(elementDivOriginalTitle.get(0).text());
                }

                if (elementSpanRating.size() > 0) {
                    movieDto.setRate(Double.parseDouble(elementSpanRating.get(0).text()));
                }

                if (elementUlDirectorsAndMainCast.size() > 0) {
                    if (elementUlDirectorsAndMainCast.get(0).child(0) != null) {
                       List<DirectorDto> directorsDto = new ArrayList<DirectorDto>();

                       for (Element e : elementUlDirectorsAndMainCast.get(0).child(0).child(1).child(0).children()) {
                           directorsDto.add(new DirectorDto(e.child(0).text()));
                       }

                        movieDto.setDirectors(directorsDto);
                    }

                    List<ActorDto> actorsDto = new ArrayList<ActorDto>();
                    switch (elementUlDirectorsAndMainCast.get(0).children().size()) {
                        case 3:
                            for (Element e : elementUlDirectorsAndMainCast.get(0).child(2).child(1).child(0).children()) {
                                actorsDto.add(new ActorDto(e.child(0).text()));
                            }
                            break;
                        case 2:
                            for (Element e : elementUlDirectorsAndMainCast.get(0).child(1).child(1).child(0).children()) {
                                actorsDto.add(new ActorDto(e.child(0).text()));
                            }
                            break;
                    }

                    movieDto.setActors(actorsDto);
                }

                if (movieDto.getName() != null && movieDto.getRate() != null) {
                    Gson gson = new Gson();
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    HttpPost post = new HttpPost("http://localhost:8080/imdb/movie");
                    StringEntity postingString = new StringEntity(gson.toJson(movieDto));
                    post.setEntity(postingString);
                    post.setHeader("Content-type", "application/json");
                    httpClient.execute(post);
                }
            }

            if (elementDivReviews.size() > 0) {
                List<CommentDto> commentsDto = new ArrayList<CommentDto>();

                Double rate = 0.0;
                String title = "";
                String description = "";

                for (int i = 0; i < elementDivReviews.size(); i++) {
                    Element e = elementDivReviews.get(i);

                    if (e.children().size() > 0) {

                        if (e.child(0).children().size() > 0) {

                            if (e.child(0).child(0).children().size() > 0) {

                                if (e.child(0).child(0).child(0).children().size() > 0) {
                                    rate = Double.parseDouble(e.child(0).child(0).child(0).child(1).text());
                                }

                                if (e.child(0).child(3).children().size() > 0) {
                                    description = e.child(0).child(3).child(0).text();
                                } else if (e.child(0).child(2).children().size() > 0) {
                                    description = e.child(0).child(2).child(0).text();
                                } else if (e.child(0).child(1).children().size() > 0) {
                                    description = e.child(0).child(1).child(0).text();
                                }
                            }

                            title = e.child(0).child(1).text();
                        }
                    }

                    if (!title.equals("") && !description.equals("") && rate != 0.0) {
                        commentsDto.add(new CommentDto(rate, title, description));
                    }

                    rate = 0.0;
                    title = "";
                    description = "";
                }

                Gson gson = new Gson();
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("http://localhost:8080/imdb/movie/" + idImdb + "/comments");
                StringEntity postingString = new StringEntity(gson.toJson(commentsDto));
                post.setEntity(postingString);
                post.setHeader("Content-type", "application/json");
                httpClient.execute(post);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}