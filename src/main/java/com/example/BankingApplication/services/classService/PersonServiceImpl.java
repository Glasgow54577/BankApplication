package com.example.BankingApplication.services.classService;

import com.example.BankingApplication.models.Person;
import com.example.BankingApplication.repositories.PersonRepository;
import com.example.BankingApplication.services.interfaceServices.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true )
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findByPersonLogin(String login){
        Optional<Person> foundPerson = personRepository.findByLogin(login);
        return foundPerson.orElse(null);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }
}
