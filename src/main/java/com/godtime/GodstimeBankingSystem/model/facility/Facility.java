package com.godtime.GodstimeBankingSystem.model.facility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godtime.GodstimeBankingSystem.model.customer.Customer;
import com.godtime.GodstimeBankingSystem.model.loan.Loan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Facility")
@Table(name = "facility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facility_sequence")
    @SequenceGenerator(name = "facility_sequence", sequenceName = "facility_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "facility_name")
    private String facilityName;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "customer_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "facility_customer_id_fk"))
    private Customer customer;

    @JsonIgnore
    @OneToOne(mappedBy = "facility")
    private Loan loan;

    public Facility(String facilityName, Customer customer) {
        this.facilityName = facilityName;
        this.customer = customer;
    }

}
