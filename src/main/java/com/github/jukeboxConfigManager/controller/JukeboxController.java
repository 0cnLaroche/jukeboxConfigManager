package com.github.jukeboxConfigManager.controller;

import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.service.JukeboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/jukebox")
public class JukeboxController {

    @Autowired JukeboxService jukeboxService;

    @GetMapping("/{settingId}")
    public List<Jukebox> getJukeboxesBySettingAndModel(
            @PathVariable String settingId,
            @RequestParam(required = false) String model) {
        return jukeboxService.findJukeboxesBySettingIdAndModel(settingId, model);
    }
}
