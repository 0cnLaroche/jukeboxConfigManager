package com.github.jukeboxConfigManager.service;

import com.github.jukeboxConfigManager.exception.ApiResourceMissingException;
import com.github.jukeboxConfigManager.exception.NotFoundException;
import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.model.Setting;
import com.github.jukeboxConfigManager.util.ListComparator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JukeboxService {

    private final APIService apiService;
    private final ListComparator<String> listComparator;

    public JukeboxService(APIService apiService) {
        this.apiService = apiService;
        this.listComparator = new ListComparator<>();
    }

    public List<Jukebox> findJukeboxesBySettingIdAndModel(
            final String settingId, String model) throws Exception {

        List<Jukebox> jukeboxes = apiService.getJukeboxes();

        if (jukeboxes.isEmpty()) {
            throw new ApiResourceMissingException("Jukeboxes list could not be loaded"); // respond 500
        }

        Map<String, Setting> settings = apiService.getSettings().getSettings()
                .stream()
                .collect(Collectors.toMap(Setting::getId, Function.identity()));

        if (settings.isEmpty()) {
            throw new ApiResourceMissingException("Settings could not be loaded"); // respond 500
        }

        Setting setting = settings.get(settingId);

        if (setting == null) {
            throw new NotFoundException(Setting.class, settingId);
        }

        // First filter out other models since parts lookup is costly
        if (model != null) {
            jukeboxes.removeIf(juke -> !juke.getModel().equals(model));
        }

        return jukeboxes
                .stream()
                .filter(
                        juke -> listComparator.isIncludedIn(
                                setting.getRequires(), juke.getComponents()
                                        .stream()
                                        .flatMap(
                                                comp -> Stream.of(comp.getName()))
                                        .collect(Collectors.toList())))
                .collect(Collectors.toList());

    }
}
