package com.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ScrollingNewsActivity extends AppCompatActivity {
    static String URL_main = "https://vnexpress.net/so-hoa";
    ArrayList<SmallNews> smallNewsList = new ArrayList<>();
    ArrayList<String> subUrls = new ArrayList<>();
    private NewsAdapter adapter;
    RequestQueue requestQueue;
    Handler handler;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scrolling_news);

        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);

        requestQueue = Volley.newRequestQueue(this);

//        this.addNewsToListRequest(URL);
//
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        handler = new Handler(Looper.getMainLooper());

        Thread t = new Thread(() -> {
            this.addSubUrls(URL_main);
            if (!subUrls.isEmpty()) {
                Log.i("SubUrl", "SubUrl is not empty");
                for (String subUrl: subUrls) {
                    SmallNews n = getDataFromLink(subUrl);
                    smallNewsList.add(n);
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.e("Interruption", e.toString());
            }

            handler.post(() -> {
//                adapter = new NewsAdapter(smallNewsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            });
        });

        t.start();

    }

    private void refreshData() {
        List<SmallNews> data = getSmallNewsList();
        adapter.setNewsList(data);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<SmallNews> getSmallNewsList() {
        Log.i("News List", smallNewsList.isEmpty() ? "" : smallNewsList.toString());
        return smallNewsList;
    }

    private void addSubUrls(String url) {
        try {
            Document document = Jsoup.connect(url).get();

            Elements articles = document.select("a");
            for (Element article: articles) {
                String link = article.attr("href");
                if (link.startsWith("/")) {
                    link = URL_main + link;
                }
                if (link.contains("vnexpress") && link.endsWith(".html")) {
                    if (!subUrls.contains(link)) {
                        subUrls.add(link);
                    }
                }
            }
        } catch (NetworkOnMainThreadException exception) {
            Log.e("Connection failed", "Error:", exception);
        } catch (Exception e) {
            Log.e("Add failed", e.toString());
        }
    }

    private SmallNews getDataFromLink(String... url) {
        final SmallNews news = new SmallNews();
        final ArrayList<String> paragraphsTexts = new ArrayList<String>();
        final String imageUrl, videoUrl;
        Element img, date = null;

        try {
            Document document = Jsoup.connect(url[0]).get();
//            Element to get the article Id
            Element articleIdMetaTag = document.selectFirst("meta[name=tt_article_id]");

//            Element to get the content that contains title, date, description, contents, and the media URLs.
            Element content = document.selectFirst("div.sidebar-1");
            assert content != null;
//            Date element
            if (content.selectFirst("div.header-content") != null) {
                date = content.selectFirst("div.header-content").selectFirst("span.date");
            }
//            Title element
            Element title = content.selectFirst("h1.title-detail");
//            Description element
            Element description = content.selectFirst("p.description");
//            Paragraphs element
            Elements paragraphs = content.select("p.Normal");
            Element img_container = content.selectFirst("div.fig-picture");
            if (img_container == null) {
                img_container = content.selectFirst("div.box_img_video");
                if (img_container == null) {
                    img = null;
                } else {
                    img = img_container.selectFirst("img[src]");
                }
            } else {
                img = img_container.selectFirst("img[data-src]");
            }

            Log.i("img", img == null ? String.format("empty from %s", url[0]) : "exist");
            imageUrl = img != null ? (!img.attr("data-src").isEmpty() ? img.attr("data-src") : img.attr("src")) : "" ;

//            Get the video Url (if exists)
            Element vid_container = content.selectFirst("div.box_embed_video");
            if (vid_container != null) {
                Element vid = vid_container.selectFirst("video[src]");
                if (vid != null) {
                    videoUrl = vid.attr("src");
                } else {
                    videoUrl = "";
                }
            } else {
                videoUrl = "";
            }

            // Get the article id.
            assert articleIdMetaTag != null;
            int articleId = Integer.parseInt(articleIdMetaTag.attr("content"));

            // Convert the paragraphs elements to ArrayList contains the paragraphs' texts.
            for (Element paragraph: paragraphs) {
                paragraphsTexts.add(paragraph.text());
            }

            news.setId(articleId);
            news.setTitle(title != null ? title.text() : getString(R.string.default_title));
            news.setDate(date != null ? date.text() : getString(R.string.default_date));
            news.setDescription(description != null ? description.text() : getString(R.string.content_description));
            news.setContents(paragraphsTexts.toArray(new String[0]));
            news.setImageUrl(imageUrl);
        } catch (ClassCastException e) {
            Log.e("Convert Class Failed", e.toString());
        } catch (Exception e) {
            Log.e("Connection Failed", String.format("Error From Url %s", url[0]), e);
        }
        return news;
    }
}