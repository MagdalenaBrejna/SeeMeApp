package mb.seeme.model.users;

import lombok.*;
import mb.seeme.model.terms.Term;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends Person {

    @Builder
    public Client(Long id, String name, String telephone, String email, String password, UserRole userRole) {
        super(id, name, telephone, email, password, userRole);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Term> terms = new HashSet<>();
}
