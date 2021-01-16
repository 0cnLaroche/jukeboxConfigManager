package com.github.jukeboxConfigManager.exception;

import lombok.Data;

public enum ErrorCode {

    EMPTY_SETTING_LIST(3, "No settings were cached after API call"),
    EMPTY_JUKEBOX_LIST(2, "No jukeboxes were cached after API call"),
    SETTING_NOT_FOUND(1, "SettingId could not be found");

    private final int code;
    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return Error code value.
     */
    public int getCode() {
        return this.code;
    }

    /**
     * @return Error description for logging and response.
     */
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "error: {" +
                "code: " + code +
                ", description: '" + description + '\'' +
                '}';
    }
}
