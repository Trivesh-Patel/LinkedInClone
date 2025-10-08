package com.triveshpatel.linkedIn.notification_service.repository;


import com.triveshpatel.linkedIn.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
