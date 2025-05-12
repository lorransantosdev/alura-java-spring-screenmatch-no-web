package br.com.lorrandev.screen_match;

import br.com.lorrandev.screen_match.model.SeriesData;
import br.com.lorrandev.screen_match.service.ApiConsumer;
import br.com.lorrandev.screen_match.service.ConvertDataImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApiConsumer apiConsumer = new ApiConsumer();
		ConvertDataImpl convertData = new ConvertDataImpl();
		String json = apiConsumer.takeData("http://www.omdbapi.com/?i=tt3896198&apikey=1aad3715");
		System.out.println(json);

		SeriesData seriesData = convertData.getData(json, SeriesData.class);
		System.out.println(seriesData);
	}
}
