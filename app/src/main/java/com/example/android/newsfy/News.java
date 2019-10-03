package com.example.android.newsfy;

public class News {
    private String newsImageURL;
    private String newsUrl;
    private String newsTitle;
    private String newsTime;
    private String newsAuthor;
    private String newsContent;
    private String newsSource;
    private String newsDescription;

    public News(String newsImageURL, String newsUrl, String newsTitle, String newsTime, String newsAuthor, String newsContent, String newsSource, String newsDescription) {
        this.newsImageURL = newsImageURL;
        this.newsUrl = newsUrl;
        this.newsTitle = newsTitle;
        this.newsTime = newsTime;
        this.newsAuthor = newsAuthor;
        this.newsContent = newsContent;
        this.newsSource = newsSource;
        this.newsDescription = newsDescription;
    }

    public News() {
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public String getNewsDescription() {
        return newsDescription;
    }
}
