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
    public Term(Long id, LocalDate termDate, LocalTime termTime, String termDescription, AvailableService service, Client client, Status termRealizedStatus) {
        super(id);
        this.termDate = termDate;
        this.termTime = termTime;
        this.termDescription = termDescription;
        this.service = service;
        this.client = client;
        this.termRealizedStatus = termRealizedStatus;
    }

    @Column(name = "term_date")
    private LocalDate termDate;

    @Column(name = "term_time")
    private LocalTime termTime;

    @Lob
    @Column(name = "term_description")
    private String termDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private AvailableService service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status termRealizedStatus;

    public int compareToPast(Term otherTerm) {
        if(this.getTermDate().isBefore(otherTerm.getTermDate()))
            return 1;
        else if(this.getTermDate().isAfter(otherTerm.getTermDate()))
            return -1;
        else if(this.getTermTime().isBefore(otherTerm.getTermTime()))
            return 1;
        else if(this.getTermTime().isAfter(otherTerm.getTermTime()))
            return -1;
        else
            return 0;
    }

    public int compareToFuture(Term otherTerm) {
        if(this.getTermDate().isBefore(otherTerm.getTermDate()))
            return -1;
        else if(this.getTermDate().isAfter(otherTerm.getTermDate()))
            return 1;
        else if(this.getTermTime().isBefore(otherTerm.getTermTime()))
            return 1;
        else if(this.getTermTime().isAfter(otherTerm.getTermTime()))
            return -1;
        else
            return 0;
    }
}
