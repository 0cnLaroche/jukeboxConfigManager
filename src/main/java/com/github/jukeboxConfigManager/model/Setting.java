package com.github.jukeboxConfigManager.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Contains a list of required components which a jukebox should have in order to support the setting
 */
@Data
public class Setting {
    private List<String> requires;
    private String id;
}
