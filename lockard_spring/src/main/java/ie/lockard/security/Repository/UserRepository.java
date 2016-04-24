package ie.lockard.security.Repository;

import ie.lockard.security.Domain.LockardUsersDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Repository
public interface UserRepository extends CrudRepository<LockardUsersDAO, Long>{

}
