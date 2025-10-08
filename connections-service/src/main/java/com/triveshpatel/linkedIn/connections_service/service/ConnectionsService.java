package com.triveshpatel.linkedIn.connections_service.service;

import com.triveshpatel.linkedIn.connections_service.entity.Person;

import java.util.List;

public interface ConnectionsService {

    List<Person> getFirstDegreeConnections();

    Boolean sendConnectionRequest(Long userId);

    Boolean acceptConnectionRequest(Long userId);

    Boolean rejectConnectionRequest(Long userId);
}
