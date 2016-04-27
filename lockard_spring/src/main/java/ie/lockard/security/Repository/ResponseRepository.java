package ie.lockard.security.Repository;

import ie.lockard.security.Domain.LockardResponseDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */
@Repository
public interface ResponseRepository extends CrudRepository<LockardResponseDAO, Long> {
}
