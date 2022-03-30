package mb.seeme.model.services;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mb.seeme.model.BaseEntity;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotBlank
    @NotEmpty
    @Column(name = "service_name")
    private String serviceName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Set<Term> terms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

}
