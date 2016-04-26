package ie.lockard.security.Service;

import ie.lockard.security.Domain.LockardHousesDAO;
import ie.lockard.security.Repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */

@Service
public class HouseService {

    private HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public Iterable<LockardHousesDAO> findAll(){
        return houseRepository.findAll();
    }

    public LockardHousesDAO findHouse(int id){
        return houseRepository.findOne(id);
    }

}
