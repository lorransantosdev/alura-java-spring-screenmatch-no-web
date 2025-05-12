package br.com.lorrandev.screen_match;

import br.com.lorrandev.screen_match.model.EpisodeData;
import br.com.lorrandev.screen_match.model.SeasonsData;
import br.com.lorrandev.screen_match.model.SeriesData;
import br.com.lorrandev.screen_match.service.ApiConsumer;
import br.com.lorrandev.screen_match.service.ConvertDataImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApiConsumer apiConsumer = new ApiConsumer();
		ConvertDataImpl convertData = new ConvertDataImpl();
		String json = apiConsumer.takeData("https://www.omdbapi.com/?t=gilmore+girls&apikey=1aad3715"); //"http://www.omdbapi.com/?i=tt3896198&apikey=1aad3715"
		System.out.println(json);

		SeriesData seriesData = convertData.getData(json, SeriesData.class);
		System.out.println(seriesData);


		json = apiConsumer.takeData("http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=1aad3715");
		EpisodeData episodeData = convertData.getData(json, EpisodeData.class);
		System.out.println(episodeData);

		List<SeasonsData> seasonsDataList = new ArrayList<>();

		for (int i = 1; i <=seriesData.totalSeasons(); i++) {
			json = apiConsumer.takeData("http://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=1aad3715");
			SeasonsData seasonsData = convertData.getData(json, SeasonsData.class);
			seasonsDataList.add(seasonsData);
		}

		seasonsDataList.forEach(System.out::println);
	}
}
