package mb.seeme.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {

    @Query(nativeQuery = true, value = "select * from security_details where username = ?1")
    ApplicationUser selectApplicationUserByUsername(String user);
}
