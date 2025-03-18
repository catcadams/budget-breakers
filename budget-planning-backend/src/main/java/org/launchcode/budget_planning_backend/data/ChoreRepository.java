package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.Chore;
import org.springframework.data.repository.CrudRepository;

public interface ChoreRepository extends CrudRepository<Chore, Integer> {
}
