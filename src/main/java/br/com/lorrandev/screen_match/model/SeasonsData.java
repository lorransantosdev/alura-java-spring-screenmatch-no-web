package br.com.lorrandev.screen_match.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonsData(@JsonAlias("Season") Integer number,
                          @JsonAlias("Episodes") List<EpisodeData> episodes) {
}
