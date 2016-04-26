package ie.lockard.security.Repository;

import ie.lockard.security.Domain.LockardAdminsDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */

@Repository
public interface AdminRepository extends CrudRepository<LockardAdminsDAO, Long> {
}
