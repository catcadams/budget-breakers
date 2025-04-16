package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Integer> {
    UserGroup findByName(String name);
}
