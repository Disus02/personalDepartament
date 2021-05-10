package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "division")
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Organization_id")
    private Organization organization;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "division")
    private Set<Worker> workers;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "division")
    private Set<Timesheet> timesheet;

    @Override
    public String toString() {
        return name;
    }
}
