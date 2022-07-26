package com.samsonmarikwa.photoappusers.repositories;

import com.samsonmarikwa.photoappusers.data.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
}
