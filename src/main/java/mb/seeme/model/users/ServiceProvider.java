package mb.seeme.model.users;

import lombok.*;
import mb.seeme.model.services.AvailableService;

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
    public ServiceProvider(Long id, String name, String telephone, String email, String password, AppUserRole appUserRole, String address, String city, String description, String field) {
        super(id, name, telephone, email, password, appUserRole);
        this.address = address;
        this.city = city;
        this.description = description;
        this.field = field;
    }

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "field")
    private String field;

    @Lob
    @Column(name = "image")
    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceProvider")
    private Set<AvailableService> services = new HashSet<>();

}
