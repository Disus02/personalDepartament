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
@Table(name = "timesheet")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numberDoc;
    private Date dateCompilation;
    private Date dateStart;
    private Date dateEnd;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "timesheet")
    private Set<TimesheetWorker> timesheetWorkers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "division_id")
    private Division division;

    @Override
    public String toString() {
        return dateStart.toString().substring(0,10) + "-" + dateEnd.toString().substring(0,10);
    }
}
