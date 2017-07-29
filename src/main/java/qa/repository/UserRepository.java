package qa.repository;

/**
 * Created by ozgur on 7/5/17.
 */

import org.springframework.data.repository.CrudRepository;
import qa.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmailAndPassword(String email, String password);
    User getByEmail(String email);

}
