package br.com.lorrandev.screen_match.main;

import br.com.lorrandev.screen_match.model.Episode;
import br.com.lorrandev.screen_match.model.EpisodeData;
import br.com.lorrandev.screen_match.model.SeasonsData;
import br.com.lorrandev.screen_match.model.SeriesData;
import br.com.lorrandev.screen_match.service.ApiConsumer;
import br.com.lorrandev.screen_match.service.ConvertDataImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner scanner = new Scanner(System.in);

    private ApiConsumer apiConsumer = new ApiConsumer();
    private ConvertDataImpl convertData = new ConvertDataImpl();

    private static final String ADDRESS = "https://www.omdbapi.com/?t=";
    private static final String APIKEY = "&apikey=1aad3715";

    public void showMenu() {
        System.out.println("Wrute the serie name");
        String serieName = scanner.nextLine().replace(" ", "+");

        String json = apiConsumer.takeData(ADDRESS + serieName + APIKEY);
        SeriesData seriesData = convertData.getData(json, SeriesData.class);
        System.out.println(seriesData);

        List<SeasonsData> seasonsDataList = new ArrayList<>();

        for (int i = 1; i <=seriesData.totalSeasons(); i++) {
            json = apiConsumer.takeData(ADDRESS + serieName + "&season=" + i + APIKEY);
            SeasonsData seasonsData = convertData.getData(json, SeasonsData.class);
            seasonsDataList.add(seasonsData);
        }

        seasonsDataList.forEach(System.out::println);

        seasonsDataList.stream()
                .map(SeasonsData::episodes)
                .flatMap(List::stream)
                .forEach(episodeData -> {
                    System.out.println(episodeData.title());
                });


        List<EpisodeData> episodeDataList = seasonsDataList.stream()
                .flatMap(t -> t.episodes().stream())
                .toList();

        System.out.println("\nTop 5 Episodes");
        episodeDataList.stream()
                .filter(episodeData -> episodeData.assessment().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::assessment).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodesList = seasonsDataList.stream()
                .flatMap(seasonsData -> seasonsData.episodes().stream()
                        .map(episode -> new Episode(episode.number(), episode)))
                .toList();

        episodesList.forEach(System.out::println);


        System.out.println("Escreva um episode");
        String titlePart = scanner.nextLine();

        Optional<Episode> episodeFind = episodesList.stream()
                .filter(episode -> episode.getTitle().toUpperCase().contains(titlePart.toUpperCase()))
                .findFirst();

        if (episodeFind.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Season: " + episodeFind.get().getSeason());
        } else {
            System.out.println("Episodio nao encontrado");
        }

        System.out.println("A partir de qual ano vc quer ver os episodios?");

        int year = scanner.nextInt();
        scanner.nextLine();

        LocalDate dateSource = LocalDate.of(year, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodesList.stream()
                .filter(episode -> episode != null && episode.getReleaseDate().isAfter(dateSource))
                .forEach(episode -> System.out.println(
                        "Season: " + episode.getSeason() +
                                " - Episode: " + episode.getTitle() +
                                " - Release Date: " + episode.getReleaseDate().format(formatter)
                ));


        Map<Integer, Double> assessmentForSeason = episodesList.stream()
                .filter(episode -> episode.getAssessment() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getAssessment)));

        System.out.println(assessmentForSeason);


        DoubleSummaryStatistics est = episodesList.stream()
                .filter(episode -> episode.getAssessment() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getAssessment));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episodio: " + est.getMax());
        System.out.println("Pior episodio: " + est.getMin());
        System.out.println("Quantidade de episodios: " + est.getCount());
    }
}
