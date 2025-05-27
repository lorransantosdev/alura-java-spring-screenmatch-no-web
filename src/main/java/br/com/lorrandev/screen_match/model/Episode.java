package br.com.lorrandev.screen_match.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.util.Optional;


public class Episode {

    private Integer season;
    private String title;
    private Integer number;
    private Double assessment;
    private LocalDate releaseDate;

    public Episode(Integer season, EpisodeData episodeData) {
        this.season = season;
        this.title = episodeData.title();
        this.number = episodeData.number();
        this.assessment = Optional.ofNullable(episodeData.assessment())
                .filter(assessment -> !assessment.equalsIgnoreCase("N/A"))
                .map(Double::valueOf)
                .orElse(0.0);

        this.releaseDate = Optional.ofNullable(episodeData.releaseDate())
                .filter(releaseDate -> !releaseDate.equalsIgnoreCase("N/A"))
                .map(LocalDate::parse)
                .orElse(null);
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getAssessment() {
        return assessment;
    }

    public void setAssessment(Double assessment) {
        this.assessment = assessment;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "season=" + season +
                ", title='" + title + '\'' +
                ", number=" + number +
                ", assessment=" + assessment +
                ", releaseDate=" + releaseDate;
    }
}
