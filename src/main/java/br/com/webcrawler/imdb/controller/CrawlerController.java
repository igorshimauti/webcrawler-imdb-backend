package br.com.webcrawler.imdb.controller;

import br.com.webcrawler.imdb.service.CrawlerService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "crawler")
public class CrawlerController {

    private static final String CRAWL_STORAGE_FOLDER = "/data/crawl/root/imdb";
    private static final int NUMBER_OF_CRAWLERS = 4;
    private static final int POLITENESS_DELAY = 50;
    private static final int MAX_DEPTH_OF_CRAWLING = 2;

    @PostMapping(value = "/run")
    public static void runCrawler() throws Exception {

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE_FOLDER);
        config.setPolitenessDelay(POLITENESS_DELAY);
        config.setMaxDepthOfCrawling(MAX_DEPTH_OF_CRAWLING);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("https://www.imdb.com/chart/bottom");

        CrawlController.WebCrawlerFactory<CrawlerService> factory = CrawlerService::new;

        controller.start(factory, NUMBER_OF_CRAWLERS);
    }
}