package lld.bookmyshow.domain;

import java.util.Objects;

public class Movie {
    private final String id;
    private final String title;
    private final String language;
    private final int durationMin;

    public Movie(String id, String title, String language, int durationMin) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.language = Objects.requireNonNull(language);
        this.durationMin = durationMin;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public int getDurationMin() { return durationMin; }
}