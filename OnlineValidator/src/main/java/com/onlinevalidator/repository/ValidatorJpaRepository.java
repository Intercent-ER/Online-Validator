package com.onlinevalidator.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.onlinevalidator.model.ValidatorEntity2;

@Repository
@Transactional
public class ValidatorJpaRepository {
	
	@Autowired 
	ValidatorJpaRepositoryInterface repo;
    
    public void save(ValidatorEntity2 entity) {
        save(entity);
    }
     
    public List<ValidatorEntity2> listAll() {
        return (List<ValidatorEntity2>) repo.findAll();
    }
     
    public ValidatorEntity2 get(long id) {
        return repo.findById(id).get();
    }
     
    public void delete(int id) {
        //deleteById(id);
    }

}
