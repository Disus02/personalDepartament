package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documents")
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int documentNumber;
    private Date dateIssue;
    private String pathDocument;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private Worker worker;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;
}
