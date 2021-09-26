package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.News;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.PaginatedRecord;

import java.util.List;

public interface INewsService {
    PaginatedRecord getNews(int pageNumber);
    PaginatedRecord searchNews(int pageNumber, String query);
    List<News> getHomePageNews();
    List<News> getMostReadNews();
    News getSingle(String visitor, int id);
    News postNews(News news);
    boolean editNews(News news);
    boolean deleteNews(int id);
}
