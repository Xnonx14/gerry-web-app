package app.SseTesting;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationJobService {

    public final ApplicationEventPublisher eventPublisher;

    public NotificationJobService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 4000, initialDelay = 2000)
    public void publishJobNotifications() throws InterruptedException {
        System.out.println("Scheduled called");
        Integer jobId = Notification.getNextJobId();
        Notification nStarted = new Notification("Job No. " + jobId + " started.", new Date());
        this.eventPublisher.publishEvent(nStarted);
        Thread.sleep(2000);
        Notification nFinished = new Notification("Job No. " + jobId + " finished.", new Date());
        this.eventPublisher.publishEvent(nFinished);
    }
}