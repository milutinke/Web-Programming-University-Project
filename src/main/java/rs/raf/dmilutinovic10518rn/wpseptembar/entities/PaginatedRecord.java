package rs.raf.dmilutinovic10518rn.wpseptembar.entities;

import java.util.ArrayList;

public class PaginatedRecord {
    private ArrayList<Object> data;
    private int total;
    private int page;
    private int perPage;
    private int pages;

    public PaginatedRecord(ArrayList<Object> data, int total, int page, int perPage, int pages) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.perPage = perPage;
        this.pages = pages;
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
