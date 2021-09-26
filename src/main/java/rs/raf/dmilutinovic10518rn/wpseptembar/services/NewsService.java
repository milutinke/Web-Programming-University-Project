package rs.raf.dmilutinovic10518rn.wpseptembar.services;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.News;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.PaginatedRecord;
import rs.raf.dmilutinovic10518rn.wpseptembar.repositories.INewsRepository;

import javax.inject.Inject;
import javax.lang.model.type.NoType;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class NewsService implements INewsService {
    @Inject
    private INewsRepository newsRepository;

    @Override
    public PaginatedRecord getNews(int pageNumber) {
        return newsRepository.getNews(pageNumber);
    }

    @Override
    public PaginatedRecord searchNews(int pageNumber, String query) {
        return newsRepository.searchNews(pageNumber, query);
    }

    @Override
    public List<News> getHomePageNews() {
        return newsRepository.getHomePageNews();
    }

    @Override
    public List<News> getMostReadNews() {
        return newsRepository.getMostReadNews();
    }

    @Override
    public News getSingle(String visitor, int id) {
        News news = newsRepository.getSingle(visitor, id);

        if (news == null)
            throw new ValidationException("This article does not exists!");

        return news;
    }

    @Override
    public News postNews(News news) {
        News resultNews = newsRepository.postNews(news);

        if (resultNews == null)
            throw new ValidationException("The article with this title already exists!");

        return resultNews;
    }

    @Override
    public boolean editNews(News news) {
        return newsRepository.editNews(news);
    }

    @Override
    public boolean deleteNews(int id) {
        return newsRepository.deleteNews(id);
    }
}
