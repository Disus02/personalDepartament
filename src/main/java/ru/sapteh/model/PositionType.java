package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "position_type")
public class PositionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String salary;
    @ManyToMany(mappedBy = "positionTypes")
    private Set<Worker> workers;

    @Override
    public String toString() {
        return String.format("%s %s",title, salary);
    }
}
