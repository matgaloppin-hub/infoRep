package local.epul4a.demosbt.repository;

import local.epul4a.demosbt.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}