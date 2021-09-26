package rs.raf.dmilutinovic10518rn.wpseptembar.repositories;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.News;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.PaginatedRecord;

import java.util.List;

public interface INewsRepository {
    PaginatedRecord getNews(int pageNumber);

    PaginatedRecord searchNews(int pageNumber, String query);

    List<News> getHomePageNews();

    List<News> getMostReadNews();

    News getSingle(String visitor, int id);

    News postNews(News news);

    boolean editNews(News news);

    boolean deleteNews(int id);
}
