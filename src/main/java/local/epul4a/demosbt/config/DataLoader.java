package local.epul4a.demosbt.config;

import local.epul4a.demosbt.model.Person;
import local.epul4a.demosbt.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PersonRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Person("Bill", "Gates", "bill@microsoft.com"));
            repository.save(new Person("Steve", "Jobs", "steve@apple.com"));
        }
    }
}