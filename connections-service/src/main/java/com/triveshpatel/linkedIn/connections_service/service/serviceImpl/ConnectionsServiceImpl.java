package com.triveshpatel.linkedIn.connections_service.service.serviceImpl;

import com.triveshpatel.linkedIn.connections_service.entity.Person;
import com.triveshpatel.linkedIn.connections_service.repository.PersonRepository;
import com.triveshpatel.linkedIn.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsServiceImpl implements ConnectionsService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> getFirstDegreeConnections(Long userID) {
        log.info("Getting first degree connections of the person");

        return personRepository.getFirstDegreeConnections(userID);
    }
}
