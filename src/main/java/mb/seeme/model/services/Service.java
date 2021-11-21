package mb.seeme.model.services;

import lombok.*;
import mb.seeme.model.BaseEntity;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "services")
public class Service extends BaseEntity {

    @Builder
    public Service(Long id, String serviceName) {
        super(id);
        this.serviceName = serviceName;
    }

    @Column(name = "service_name")
    private String serviceName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Set<Term> terms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;
}
