package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int series;
    private int number;
    private Date dateIssue;
    private String whomIssued;
    private String registrationPlace;
    private int devisionCode;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "passport")
    private Worker worker;

    @Override
    public String toString() {
        return String.format("%d %d",number,series);
    }
}
