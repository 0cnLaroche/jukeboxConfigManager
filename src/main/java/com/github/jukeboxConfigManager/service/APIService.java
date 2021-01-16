package com.github.jukeboxConfigManager.service;

import com.github.jukeboxConfigManager.model.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.github.jukeboxConfigManager.model.Jukebox;

import java.util.List;

@Service
public class APIService {
   final private String host;
   final private WebClient client;
   final private String jukeboxesUri;
   final private String settingsUri;

   public APIService(
           @Value("api.touchtunes.url") String host,
           @Value("api.touchtunes.jukeboxes") String jukeboxesUri,
           @Value("api.touchtunes.settings") String settingsUri) {
       this.host = host;
       this.jukeboxesUri = jukeboxesUri;
       this.settingsUri = settingsUri;
       client = WebClient.create(host);
   }

   /**
    * GET jukeboxes from API
    * @return List of all jukeboxes
    */
   public List<Jukebox> getJukeboxes() {
       return client.get()
               .uri(jukeboxesUri)
               .retrieve()
               .bodyToMono(new ParameterizedTypeReference<List<Jukebox>>() {})
               .block();
   }

    /**
     * GET settings from API
     * @return Settings
     */
   public Settings getSettings() {
       return client.get()
               .uri(settingsUri)
               .retrieve()
               .bodyToMono(Settings.class)
               .block();
   }
}
