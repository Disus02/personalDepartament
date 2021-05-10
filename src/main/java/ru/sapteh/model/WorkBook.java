package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_book")
public class WorkBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int series;
    private int number;
    private Date dateIssue;
    private String whomIssued;
    @OneToOne(fetch = FetchType.LAZY,mappedBy = "workBook")
    private Worker worker;

    @Override
    public String toString() {
        return String.format("%d %d",series, number);
    }
}
