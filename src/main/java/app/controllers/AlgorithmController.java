package app.controllers;

import app.Sse.AlgorithmSseEmitter;
import app.Sse.SseEngine;
import app.model.Algorithm;
import app.service.AlgorithmFeedSseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Controller
@CrossOrigin
public class AlgorithmController {

    @Autowired
    SseEngine sseEngine;

    @Autowired
    AlgorithmFeedSseService sseService;

    @GetMapping("running")
    public SseEmitter startAlgorithm(@RequestParam String compactness) {
        System.out.println("WOW I GOT THE COMPACTNESS");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getPrincipal().toString();
        SseEmitter emitter = new AlgorithmSseEmitter(sseEngine.getTIMEOUT());
        sseEngine.getEmitters().put(user, emitter);

        //push data to user view
        sseService.push(user, null);
        return emitter;
    }
}
