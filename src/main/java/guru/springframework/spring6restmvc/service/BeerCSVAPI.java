package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface BeerCSVAPI {
    List<BeerCSVRecord> convertCSV(File file) throws FileNotFoundException;
}
