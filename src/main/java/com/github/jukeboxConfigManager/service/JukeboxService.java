package com.github.jukeboxConfigManager.service;

import com.github.jukeboxConfigManager.exception.ApiResourceMissingException;
import com.github.jukeboxConfigManager.exception.ErrorCode;
import com.github.jukeboxConfigManager.exception.NotFoundException;
import com.github.jukeboxConfigManager.model.Jukebox;
import com.github.jukeboxConfigManager.model.Setting;
import com.github.jukeboxConfigManager.util.ListComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Applies filtering operations on jukeboxes resources.
 */
@Service
public class JukeboxService {

    private final APIService apiService;
    private final ListComparator<String> listComparator;
    private final static Logger LOGGER = LoggerFactory.getLogger(JukeboxService.class);

    public JukeboxService(APIService apiService) {
        this.apiService = apiService;
        this.listComparator = new ListComparator<>();
    }

    /**
     * Find jukeboxes supporting the given setting. Jukebox must have all required components
     * for the setting.
     *
     * @param settingId
     * @param model filter by model name. Null for no filtering
     * @return
     * @throws Exception
     */
    public List<Jukebox> findJukeboxesBySettingIdAndModel(final String settingId, String model) {

        /*
         * For optimal performance, jukeboxes and settings should be cached,
         * however we don't know on which frequency list is updated on the server,
         * therefore we can't implement a synchronisation mechanism.
         */

        LOGGER.debug("Finding jukeboxes for settingId: '{}'", settingId);

        List<Jukebox> jukeboxes = apiService.getJukeboxes();

        if (jukeboxes.isEmpty()) {
            LOGGER.error(ErrorCode.EMPTY_JUKEBOX_LIST.toString());
            throw new ApiResourceMissingException(ErrorCode.EMPTY_JUKEBOX_LIST);
        }

        Map<String, Setting> settings = apiService.getSettings().getSettings()
                .stream()
                .collect(Collectors.toMap(Setting::getId, Function.identity()));

        if (settings.isEmpty()) {
            LOGGER.error(ErrorCode.SETTING_NOT_FOUND.toString());
            throw new ApiResourceMissingException(ErrorCode.SETTING_NOT_FOUND);
        }

        Setting setting = settings.get(settingId);

        if (setting == null) {
            LOGGER.error("{} : Setting '{}' could not be found", ErrorCode.SETTING_NOT_FOUND, settingId);
            throw new NotFoundException(Setting.class, settingId, ErrorCode.SETTING_NOT_FOUND);
        }

        // First filter out other models since component lookup is costly.
        if (model != null) {
            LOGGER.debug("Filtering by model {}", model);
            jukeboxes.removeIf(juke -> !juke.getModel().equals(model));
        }

        /*
         * Iterate over jukeboxes list to retrieve elements that have all required components of the
         * given Setting.
         */
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
