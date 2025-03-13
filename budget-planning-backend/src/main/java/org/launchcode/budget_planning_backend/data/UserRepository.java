package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
