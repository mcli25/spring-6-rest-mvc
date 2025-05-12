package guru.springframework.spring6restmvc.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class BeerCSVService implements BeerCSVAPI{
    @Override
    public List<BeerCSVRecord> convertCSV(File file) throws FileNotFoundException {
        try (Reader reader = new FileReader(file)) {
            CsvToBean<BeerCSVRecord> csvToBean = new CsvToBeanBuilder<BeerCSVRecord>(reader)
                    .withType(BeerCSVRecord.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
