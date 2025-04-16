package org.launchcode.budget_planning_backend.data;

import org.launchcode.budget_planning_backend.models.Contributions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionsRepository extends CrudRepository<Contributions, Integer> {
}
