package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int inn;
    private int kpp;
    private long payment;
    private int bik;
    private String phone;
    private String nameDirector;
    private String address;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "organization")
    private Set<Division> divisions;
}
