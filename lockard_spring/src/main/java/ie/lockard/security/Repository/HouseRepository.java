package ie.lockard.security.Repository;

import ie.lockard.security.Domain.LockardHousesDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
@Repository
public interface HouseRepository extends CrudRepository<LockardHousesDAO, Integer> {
}
