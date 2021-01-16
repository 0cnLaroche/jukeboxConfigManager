package com.github.jukeboxConfigManager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jukeboxConfigManager.exception.NotFoundException;
import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.model.Settings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JukeboxServiceTest {

    @Mock
    private APIService mockApiService;

    @InjectMocks
    private JukeboxService jukeboxService;

    Settings mockedSettings;
    List<Jukebox> mockedJukeboxes;

    @Before
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockedSettings = objectMapper.readValue(
                new File("src/test/resources/mockedSettings.json"), Settings.class);
        mockedJukeboxes = objectMapper.readValue(
                new File("src/test/resources/mockedJukeboxes.json"),
                new TypeReference<List<Jukebox>>() {});

        Mockito.when(mockApiService.getJukeboxes())
                .thenReturn(mockedJukeboxes);
        Mockito.when(mockApiService.getSettings())
                .thenReturn(mockedSettings);

        assert !mockApiService.getJukeboxes().isEmpty();
        assert !mockApiService.getSettings().getSettings().isEmpty();
    }

    @Test
    public void testNoComponentRequired() throws Exception {

        int expectedSize = mockApiService.getJukeboxes().size();
        String settingId = "18beae74-a24f-425d-b9d2-8d9d69be89fa";

        List<Jukebox> results = jukeboxService.findJukeboxesBySettingIdAndModel(settingId, null);

        assertEquals(expectedSize, results.size());
    }

    @Test
    public void testNoComponentRequiredWithFilter() throws Exception {

        int expectedSize = 10;
        String modelName = "fusion";
        String settingId = "18beae74-a24f-425d-b9d2-8d9d69be89fa";

        List<Jukebox> results = jukeboxService.findJukeboxesBySettingIdAndModel(settingId, modelName);

        assertEquals(expectedSize, results.size());

    }

    @Test
    public void testComponentRequired() throws Exception {

        int expectedSize = 2;
        String settingId = "2321763c-8e06-4a31-873d-0b5dac2436da";

        List<Jukebox> results = jukeboxService.findJukeboxesBySettingIdAndModel(settingId, null);

        assertEquals(expectedSize, results.size());
    }

    @Test
    public void testComponentRequiredWithFilter() throws Exception {

        int expectedSize = 3;
        String settingId = "67ab1ec7-59b8-42f9-b96c-b261cc2a2ed9";
        String modelName = "virtuo";

        List<Jukebox> results = jukeboxService.findJukeboxesBySettingIdAndModel(settingId, modelName);

        assertEquals(expectedSize, results.size());
    }

    @Test(expected = NotFoundException.class)
    public void testSettingNotFound() throws Exception {
        jukeboxService.findJukeboxesBySettingIdAndModel("INVALID_ID", null);
    }
}