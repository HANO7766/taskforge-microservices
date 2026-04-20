package com.taskforge.notification.listener;

import com.taskforge.notification.dto.TaskEvent;
import com.taskforge.notification.model.Notification;
import com.taskforge.notification.model.NotificationType;
import com.taskforge.notification.repository.NotificationRepository;
import com.taskforge.notification.service.EmailService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailService emailService;

    // Escucha eventos en RabbitMQ
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "notification.queue", durable = "true"),
            exchange = @Exchange(value = "taskforge.exchange", type = "topic"),
            key = "notification.routingkey"
    ))
    public void consumeTaskEvent(TaskEvent event) {
        System.out.println("Recibido evento RabbitMQ: Tarea " + event.getTaskTitle() + " - " + event.getEventType());

        // Si la tarea tiene un asignado, guardar la notificación
        if (event.getAssigneeId() != null) {
            Notification notification = Notification.builder()
                    .userId(event.getAssigneeId())
                    .title("Novedad en Tarea: " + event.getEventType())
                    .message("La tarea '" + event.getTaskTitle() + "' ha sido actualizada.")
                    .type(NotificationType.TASK_ASSIGNED)
                    .build();

            notificationRepository.save(notification);

            // Disparar envío de correo asíncrono
            emailService.sendEmail(
                    event.getAssigneeId(),
                    "Novedad en tu tarea",
                    notification.getMessage()
            );
        }
    }
}
