package com.example.accountapiwithhateoas.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "account_tbl")
public class Account  {
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    @Setter(AccessLevel.NONE)
    private Long id;
    private String username;
    private String accountNumber;
    private Double balance;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
}
