package br.com.lorrandev.screen_match.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonAlias - basically it reads the json and gives a alias - Deserialize
// @JsonProperty - Deserialize e serialize

// ignore everythings that not been passed
@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer totalSeasons,
                         @JsonAlias("imdbRating")String assessment) {
}
