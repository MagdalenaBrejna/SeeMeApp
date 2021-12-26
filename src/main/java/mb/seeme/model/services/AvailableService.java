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
@Entity
@Table(name = "services")
public class AvailableService extends BaseEntity {

    @Builder
    public AvailableService(Long id, String serviceName, ServiceProvider serviceProvider) {
        super(id);
        this.serviceName = serviceName;
        this.serviceProvider = serviceProvider;
    }

    @Column(name = "service_name")
    private String serviceName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Set<Term> terms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

}
