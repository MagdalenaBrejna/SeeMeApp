package mb.seeme.model.terms;

import lombok.*;
import mb.seeme.model.BaseEntity;
import mb.seeme.model.services.AvailableService;
import mb.seeme.model.users.Client;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "terms")
public class Term extends BaseEntity {

    @Builder
    public Term(Long id, LocalDate date, LocalTime time, String termDescription, AvailableService service, Client client) {
        super(id);
        this.date = date;
        this.time = time;
        this.termDescription = termDescription;
        this.service = service;
        this.client = client;
    }

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Lob
    @Column(name = "term_description")
    private String termDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private AvailableService service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
