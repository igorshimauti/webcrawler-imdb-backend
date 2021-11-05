package br.com.webcrawler.imdb.service;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

public class CrawlerService extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean result = !FILTERS.matcher(href).matches() &&
                href.startsWith("https://www.imdb.com/title/") &&
                !href.contains("/trivia") &&
                !href.contains("/goofs") &&
                !href.contains("/quofes") &&
                !href.contains("/alternateversions") &&
                !href.contains("/movieconnections") &&
                !href.contains("/soundtrack") &&
                !href.contains("/keywords") &&
                !href.contains("/taglines") &&
                !href.contains("/parentalguide") &&
                !href.contains("/review/") &&
                !href.contains("/faq") &&
                !href.contains("/company") &&
                !href.contains("/releaseinfo") &&
                !href.contains("/news") &&
                !href.contains("/mediaindex");

        return result;
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            HtmlJsoupService htmlJsoupService = new HtmlJsoupService();
            htmlJsoupService.convertHtmlToData(html);
        }
    }
}