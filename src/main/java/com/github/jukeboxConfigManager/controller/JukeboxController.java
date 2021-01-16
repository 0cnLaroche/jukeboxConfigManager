package com.github.jukeboxConfigManager.controller;

import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.service.JukeboxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jukebox")
public class JukeboxController {

    @Autowired JukeboxService jukeboxService;

    private Logger logger = LoggerFactory.getLogger(JukeboxController.class);

    @Operation(summary = "Finds jukeboxes supporting the given setting. Jukebox must have all required " +
            "components for the setting.")
    @GetMapping("/{settingId}")
    public List<Jukebox> getJukeboxesBySettingAndModel(
            @PathVariable String settingId,
            @Parameter(description = "filter by model name")
            @RequestParam(required = false) String model,
            @Parameter(description = "specifies at what index start the page")
            @RequestParam(required = false) Integer offset,
            @Parameter(description = "specifies the page size ")
            @RequestParam(required = false) Integer limit) {

        logger.debug("Processing jukeboxes request");

        PagedListHolder pagedListHolder = new PagedListHolder(
                jukeboxService.findJukeboxesBySettingIdAndModel(settingId, model));

        if (limit != null && limit > 0) {
            // Default is 10
            pagedListHolder.setPageSize(limit);
        }
        if(offset != null && offset >= 0) {
            pagedListHolder.setPage(offset);
            return pagedListHolder.getPageList();
        }
        // If offset is not specified, whole list is returned.
        return pagedListHolder.getSource();
    }
}
