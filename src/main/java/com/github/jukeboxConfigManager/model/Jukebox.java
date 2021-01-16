package com.github.jukeboxConfigManager.model;

import lombok.Data;

import java.util.List;

/**
 * Jukebox and information about it.
 */
@Data
public class Jukebox {
    /** Jukebox ID */
    private String id;
    /** Model */
    private String model;
    /** Jukebox components */
    private List<Component> components;
}
