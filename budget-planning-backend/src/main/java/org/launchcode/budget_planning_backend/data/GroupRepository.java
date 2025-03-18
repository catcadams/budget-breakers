package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}
