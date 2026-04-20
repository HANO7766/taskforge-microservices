package com.taskforge.project.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Implementación del Patrón Observer (Publisher)
@Component
public class TaskEventPublisher {

    public static final String EXCHANGE = "taskforge.exchange";
    public static final String ROUTING_KEY = "notification.routingkey";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishTaskEvent(TaskEvent event) {
        System.out.println("Publicando evento en RabbitMQ: " + event.getEventType() + " para la tarea: " + event.getTaskTitle());
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
    }
}
