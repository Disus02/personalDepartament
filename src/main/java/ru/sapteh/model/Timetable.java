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
@Table(name = "timetable")
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date dateStart;
    private Date dateEnd;
    private int numberOrder;
    @ManyToMany(mappedBy = "timetables",fetch = FetchType.EAGER)
    private Set<Worker> workers;

    @Override
    public String toString() {
        return String.format("%s по %s", dateStart,dateEnd);
    }

}
