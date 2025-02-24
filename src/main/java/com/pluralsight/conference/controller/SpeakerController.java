package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.service.SpeakerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/speaker")
public class SpeakerController {

    private SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public List<Speaker> getSpeakers() {
        return speakerService.findAll();
    }

    @GetMapping("/{id}")
    public Speaker getSpeaker(@PathVariable("id") int id) {
        return speakerService.get(id);
    }
    @PostMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        System.out.println("Name: " + speaker.getName());
        return speakerService.create(speaker);
    }
}
