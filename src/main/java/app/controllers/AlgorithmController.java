package app.controllers;

import app.SseTesting.Notification;
import app.SseTesting.NotificationJobService;
import app.gerry.Sse.AlgorithmMoveService;
import app.gerry.Sse.SseResultData;
import app.gerry.Sse.TestAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@CrossOrigin
public class AlgorithmController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Autowired
    NotificationJobService notificationJobService;

    @Autowired
    AlgorithmMoveService algorithmMoveService;

//    @GetMapping("new_notification")
//    public SseEmitter startAlgorithm(@RequestParam String compactness) {
//        System.out.println("WOW I GOT THE COMPACTNESS");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String user = auth.getPrincipal().toString();
//        SseEmitter emitter = new AlgorithmSseEmitter(sseEngine.getTIMEOUT());
//        sseEngine.getEmitters().put(user, emitter);
//
//        //push data to user view
//        sseService.push(user, null);
//        return emitter;
//    }

//    @GetMapping("/new_notification")
//    public SseEmitter getNewNotification() throws InterruptedException {
//        SseEmitter emitter = new SseEmitter();
//        this.emitters.add(emitter);
//
//        emitter.onCompletion(() -> this.emitters.remove(emitter));
//        emitter.onTimeout(() -> {
//            emitter.complete();
//            this.emitters.remove(emitter);
//        });
//        notificationJobService.publishJobNotifications();
//        System.out.println("returned emitter");
//        return emitter;
//    }
//
//    @EventListener
//    public void onNotification(Notification notification) {
//        List<SseEmitter> deadEmitters = new ArrayList<>();
//        this.emitters.forEach(emitter -> {
//            try {
//                emitter.send(notification);
//            } catch (Exception e) {
//                deadEmitters.add(emitter);
//            }
//        });
//        this.emitters.remove(deadEmitters);
//    }

    @GetMapping("/running/data")
    public SseEmitter getData() {
        SseEmitter emitter = new SseEmitter();
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            this.emitters.remove(emitter);
        });
        algorithmMoveService.runAlgorithm(new TestAlgorithm());
        return emitter;
    }
    
    @GetMapping("/running/moves")
    public SseEmitter getMoves() {
        SseEmitter emitter = new SseEmitter();
        this.emitters.add(emitter);

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            this.emitters.remove(emitter);
        });
        algorithmMoveService.runAlgorithm(new TestAlgorithm());
        return emitter;
    }

    @EventListener
    public void onAlgorithmMove(SseResultData resultData) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                emitter.send(resultData);
                if(resultData.isLastOne()) {
                    deadEmitters.add(emitter);
                }
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });
        this.emitters.remove(deadEmitters);
    }
}
