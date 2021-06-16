package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_code")
public class PaymentCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int code;
    private String description;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "paymentCode")
    private Set<TimesheetWorker> timesheetWorkers;

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
