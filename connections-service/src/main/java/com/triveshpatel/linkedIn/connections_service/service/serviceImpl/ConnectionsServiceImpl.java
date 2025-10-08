package com.triveshpatel.linkedIn.connections_service.service.serviceImpl;

import com.triveshpatel.linkedIn.connections_service.auth.UserContextHolder;
import com.triveshpatel.linkedIn.connections_service.entity.Person;
import com.triveshpatel.linkedIn.connections_service.event.AcceptConnectionRequestEvent;
import com.triveshpatel.linkedIn.connections_service.event.SendConnectionRequestEvent;
import com.triveshpatel.linkedIn.connections_service.repository.PersonRepository;
import com.triveshpatel.linkedIn.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsServiceImpl implements ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;

    @Override
    public List<Person> getFirstDegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting first degree connections for the user with Id: {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

    @Override
    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Trying to send connection request, sender: {}, receiver: {}", senderId, receiverId);

        if(senderId == receiverId){
            throw new RuntimeException("Both sender and receiver are same");
        }

        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if(alreadySentRequest){
            throw new RuntimeException("Connection request already exists, cannot send again");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if(alreadyConnected){
            throw new RuntimeException("Already connected users, cannot add connection request");
        }

        log.info("Successfully sent the connection request");
        personRepository.addConnectionRequest(senderId, receiverId);

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        sendRequestKafkaTemplate.send("send-connection-request-topic", sendConnectionRequestEvent);
        return true;
    }

    @Override
    public Boolean acceptConnectionRequest(Long senderId) {
         Long receiverId = UserContextHolder.getCurrentUserId();

         boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if(!connectionRequestExists){
            throw new RuntimeException("No connection request exist to accept");
        }

        log.info("Successfully accepted the connection request, sender: {}, receiver: {}", senderId, receiverId);
        personRepository.acceptConnectionRequest(senderId,receiverId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent
                .builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        acceptRequestKafkaTemplate.send("accept-connection-request-topic", acceptConnectionRequestEvent);
        return true;
    }

    @Override
    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();

        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if(!connectionRequestExists){
            throw new RuntimeException("No connection request exist, cannot delete");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;
    }
}
