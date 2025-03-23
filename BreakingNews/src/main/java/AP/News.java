package AP;

public class News
{
    private String title;
    private String description;
    private String sourceName;
    private String author;
    private String url;
    private String publishedAt;

    // Constructor: Initializes news article details
    public News(String title, String description, String sourceName, String author, String url, String publishedAt)
    {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.author = author;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    // Getters for accessing private fields
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSourceName() { return sourceName; }
    public String getAuthor() { return author; }
    public String getUrl() { return url; }
    public String getPublishedAt() { return publishedAt; }

    // Display full details of the news article
    public void displayNews()
    {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Source: " + sourceName);
        System.out.println("Author: " + author);
        System.out.println("URL: " + url);
        System.out.println("Published At: " + publishedAt);
    }

}
