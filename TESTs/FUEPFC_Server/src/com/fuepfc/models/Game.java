package com.fuepfc.models;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: matteoranzi
 * Date: 18/03/19
 * Time: 15.54
 */
public class Game {
    private UUID versionUUID;
    private UUID gameUUID;
    private String name;
    private String version;

    public Game(UUID versionUUID, UUID gameUUID, String name, String version){
        this.versionUUID = versionUUID;
        this.gameUUID = gameUUID;
        this.name = name;
        this.version = version;
    }

    public UUID getVersionUUID() {
        return versionUUID;
    }

    public UUID getGameUUID() {
        return gameUUID;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}