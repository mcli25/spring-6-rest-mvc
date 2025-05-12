package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repo.BeerRepo;
import guru.springframework.spring6restmvc.repo.CustomerRepo;
import guru.springframework.spring6restmvc.service.BeerCSVAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class BootstrapData implements CommandLineRunner {
    private final BeerRepo beerRepo;
    private final CustomerRepo customerRepo;
    private final BeerCSVAPI beerCSVAPI;

    public BootstrapData(BeerRepo beerRepo, CustomerRepo customerRepo, BeerCSVAPI beerCSVAPI) {
        this.beerRepo = beerRepo;
        this.customerRepo = customerRepo;
        this.beerCSVAPI = beerCSVAPI;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
        loadCsvData();  // Add this call to load CSV data as well
    }

    private void loadBeerData() {
        if (beerRepo.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")  // Fixed: Minimum 6 characters
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.IPA)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("123457")  // Fixed: Minimum 6 characters and made unique
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepo.saveAll(Arrays.asList(beer1, beer2, beer3));
            System.out.println("Loaded " + beerRepo.count() + " beers");  // Add logging
        }
    }

    private void loadCustomerData() {
        if (customerRepo.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("John Doe")
                    .email("john@example.com")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Jane Smith")
                    .email("jane@example.com")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Bob Johnson")
                    .email("bob@example.com")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepo.saveAll(Arrays.asList(customer1, customer2, customer3));
            System.out.println("Loaded " + customerRepo.count() + " customers");  // Add logging
        }
    }
    private void loadCsvData() throws FileNotFoundException {
        if (beerRepo.count() < 10) {
            try {
                File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
                List<BeerCSVRecord> records = beerCSVAPI.convertCSV(file);

                System.out.println("Found " + records.size() + " CSV records");

                records.forEach(record -> {
                    BeerStyle beerStyle = switch (record.getStyle()) {
                        case "American Pale Lager" -> BeerStyle.LAGER;
                        case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                                BeerStyle.ALE;
                        case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                        case "American Porter" -> BeerStyle.PORTER;
                        case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                        case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                        case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                        case "English Pale Ale" -> BeerStyle.PALE_ALE;
                        default -> BeerStyle.PILSNER;
                    };

                    // Get the beer name from the CSV and ensure it's valid
                    String beerName = record.getBeer();

                    // If beer name is null or too short, provide a default
                    if (beerName == null || beerName.trim().length() < 3) {
                        beerName = "Beer " + record.getRow();  // Create a valid default name
                    }

                    // Truncate if too long (should already be handled by StringUtils.abbreviate, but as a safeguard)
                    if (beerName.length() > 50) {
                        beerName = StringUtils.abbreviate(beerName, 50);
                    }

                    beerRepo.save(Beer.builder()
                            .beerName(beerName)  // Use the validated beer name
                            .beerStyle(beerStyle)
                            .price(BigDecimal.TEN)
                            .upc(String.format("%06d", record.getRow()))
                            .quantityOnHand(record.getCount())
                            .build());
                });
                System.out.println("CSV data loaded. Total beers: " + beerRepo.count());
            } catch (Exception e) {
                System.err.println("Error loading CSV data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}