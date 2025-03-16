package AP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Infrastructure
{

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList;

    public Infrastructure(String APIKEY)
    {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=2025-02-20&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        if (JSONRESULT != null)
        {
            parseInformation(JSONRESULT);
        }
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    private String getInformation()
    {
        try
        {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + APIKEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
            {
                return response.body();
            }
            else
            {
                throw new IOException("HTTP error code: " + response.statusCode());
            }
        }
        catch (Exception e)
        {
            System.out.println("!!Exception : " + e.getMessage());
        }
        return null;
    }

    private void parseInformation(String JSONRESULT)
    {
        try
        {
            JsonObject jsonObject = JsonParser.parseString(JSONRESULT).getAsJsonObject(); // Convert response string to JSON object
            JsonArray articles = jsonObject.getAsJsonArray("articles"); //Extract articles array

            newsList = new ArrayList<>();

            for (int i = 0; i < (Math.min(20, articles.size())); i++)
            {
                JsonObject article = articles.get(i).getAsJsonObject();
                // Extract values with null checks
                String title = article.has("title") && !article.get("title").isJsonNull() ? article.get("title").getAsString() : "No Title";
                String description = article.has("description") && !article.get("description").isJsonNull() ? article.get("description").getAsString() : "No Description";
                String sourceName = article.has("source") && article.getAsJsonObject("source").has("name") ? article.getAsJsonObject("source").get("name").getAsString() : "Unknown Source";
                String author = article.has("author") && !article.get("author").isJsonNull() ? article.get("author").getAsString() : "Unknown Author";
                String url = article.has("url") && !article.get("url").isJsonNull() ? article.get("url").getAsString() : "No URL";
                String publishedAt = article.has("publishedAt") && !article.get("publishedAt").isJsonNull() ? article.get("publishedAt").getAsString() : "No Date";

                newsList.add(new News(title, description, sourceName, author, url, publishedAt));
            }
        }
        catch (Exception e)
        {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }

    public void displayNewsList()
    {
        if (newsList.isEmpty())
        {
            System.out.println("No news available."); // Handle empty news list
            return;
        }

        for (int i = 0; i < newsList.size(); i++)
        {
            System.out.println((i + 1) + ". " + newsList.get(i).getTitle());
        }
    }
}
