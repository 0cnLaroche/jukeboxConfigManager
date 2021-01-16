package com.github.jukeboxConfigManager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.service.JukeboxService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JukeboxControllerTest {

    @Mock
    JukeboxService jukeboxService;

    @InjectMocks
    JukeboxController jukeboxController;

    @Before
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Jukebox> mockedJukeboxes = objectMapper.readValue(
                new File("src/test/resources/mockedJukeboxes.json"),
                new TypeReference<List<Jukebox>>() {});
        assert !mockedJukeboxes.isEmpty();

        Mockito.when(jukeboxService.findJukeboxesBySettingIdAndModel(
                ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(mockedJukeboxes);
    }

    @Test
    public void getJukeboxesBySettingAndModel() {
        String settingId = "12345";
        String expectedJukeboxId = "5ca94a8aafb9d8c4e4fddf02";
        int offset = 2;
        int limit = 5;
        List<Jukebox> results = jukeboxController.getJukeboxesBySettingAndModel(
                settingId, null, offset, limit);

        assertEquals(5, results.size());
        assertEquals(expectedJukeboxId, results.get(0).getId());
    }
}