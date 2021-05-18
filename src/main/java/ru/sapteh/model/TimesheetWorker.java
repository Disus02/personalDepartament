package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "timesheet_worker")
public class TimesheetWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private Worker worker;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timesheet_id")
    private Timesheet timesheet;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_code_id")
    private PaymentCode paymentCode;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "correspondent_account_id")
    private CorrespondentAccount correspondentAccount;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "timesheetWorker")
    private Set<Marks> marks;

    @Override
    public String toString() {
        return String.format("%s %s %s",timesheet,paymentCode,correspondentAccount);

    }
}
