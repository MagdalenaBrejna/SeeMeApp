package mb.seeme.model.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mb.seeme.model.services.AvailableService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "providers")
public class ServiceProvider extends Person {

    @Builder
    public ServiceProvider(Long id, String name, String telephone, String email, String password, SimpleGrantedAuthority userRole, String address, String city, String providerDescription, String providerField, byte[] providerImage) {
        super(id, name, telephone, email, password, userRole);
        this.address = address;
        this.city = city;
        this.providerDescription = providerDescription;
        this.providerField = providerField;
        this.providerImage = providerImage;
    }

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Lob
    @Column(name = "description")
    private String providerDescription;

    @Column(name = "field")
    private String providerField;

    @Lob
    @Column(name = "image")
    private byte[] providerImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceProvider")
    private Set<AvailableService> services = new HashSet<>();

}
