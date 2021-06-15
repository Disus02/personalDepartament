package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String patronymic;
    @NonNull
    private String address;
    @NonNull
    private Date birthday;
    @NonNull
    private int inn;
    @NonNull
    private Date dateEmployment;
    @NonNull
    private String photoPath;
    @NonNull
    private int snils;
    @NonNull
    private int numberService;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "worker")
    private Set<TimesheetWorker> timesheetWorkers;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "worker")
    private Set<Medical> medicals;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "worker")
    private Set<Documents> documents;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "worker")
    private Set<Education> educations;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "division_id")
    @NonNull
    private Division division;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passport_id")
    @NonNull
    private Passport passport;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_book_id")
    @NonNull
    private WorkBook workBook;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post",joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns =@JoinColumn(name = "post_id"))
    private Set<PositionType> positionTypes;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "holiday",joinColumns = @JoinColumn(name = "worker_id"),
    inverseJoinColumns = @JoinColumn(name = "holiday_id"))
    private Set<Timetable> timetables;

    @Override
    public String toString() {
        return String.format("%s %s %s",firstName, lastName, patronymic);
    }
    public void addPost(PositionType positionType){
        positionTypes.add(positionType);
    }
    public void addHoliday(Timetable timetable){
        timetables.add(timetable);
    }
}
