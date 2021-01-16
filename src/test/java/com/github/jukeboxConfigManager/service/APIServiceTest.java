package com.github.jukeboxConfigManager.service;

import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.model.Settings;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class APIServiceTest {

    private APIService apiService;

    @Before
    public void setUp() throws Exception {
        this.apiService = new APIService(
                "http://my-json-server.typicode.com/touchtunes/tech-assignment",
                "/jukes",
                "/settings");
    }

    @Test
    public void getJukeboxes() {
        List<Jukebox> res = apiService.getJukeboxes();

        assertNotNull(res);
        assert res.size() > 0;
    }

    @Test
    public void getSettings() {
        Settings res = apiService.getSettings();
        assertNotNull(res);
        assert res.getSettings().size() > 0;
    }

}