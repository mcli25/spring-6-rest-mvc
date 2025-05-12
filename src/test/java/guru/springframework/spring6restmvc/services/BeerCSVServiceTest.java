package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import guru.springframework.spring6restmvc.service.BeerCSVService;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class BeerCSVServiceTest {
    BeerCSVService beerCSVService = new BeerCSVService();

    @Test
    void convertCSV() throws IOException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> records = beerCSVService.convertCSV(file);

        assertThat(records).isNotEmpty();
        assertThat(records.size()).isGreaterThan(0);
    }
}
