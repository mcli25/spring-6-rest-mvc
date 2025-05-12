package guru.springframework.spring6restmvc.entities;

import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "beer")
public class Beer {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @Version
    @PositiveOrZero
    private Integer version;

    @NotBlank(message = "Beer name cannot be blank")
    @Size(min = 3, max = 50, message = "Beer name must be between 3 and 50 characters")
    @Column(name = "beer_name", length = 50)
    private String beerName;

    @NotNull(message = "Beer style cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BeerStyle beerStyle;

    @NotBlank(message = "UPC cannot be blank")
    @Size(min = 6, max = 25, message = "UPC must be between 6 and 25 characters")
    @Column(length = 25, unique = true)
    private String upc;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    @Digits(integer = 5, fraction = 2, message = "Price must have up to 5 integer and 2 fraction digits")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantityOnHand;

    @CreationTimestamp
    @PastOrPresent
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @PastOrPresent
    private LocalDateTime updateDate;
}