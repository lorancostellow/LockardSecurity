package PiComAPI.Core;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by dylan on 21/04/16.
 * Source belongs to Lockard_PiComAPI
 */
public class Configuration {
    private Path configurationFile;
    private JSONObject settingsObject;
    private String json = "";

    public Configuration(Path configurationFile) throws IOException {
        this.configurationFile = configurationFile;

        if (Files.exists(configurationFile))
            json = new String(Files.readAllBytes(configurationFile));
        else Files.write(configurationFile, "".getBytes());
        settingsObject = json.isEmpty() ? new JSONObject() :
                new JSONObject(json);
    }

    public String getSetting(Settings setting) {
        if (settingsObject.has(setting.name()))
            return settingsObject.getString(setting.name());
        return "";
    }

    public void setSetting(Settings setting, String value) {
        settingsObject.put(setting.name(), value);
    }

    public boolean commit() {
        try {
            if (!json.equals(settingsObject.toString()))
                Files.write(configurationFile,
                        settingsObject.toString().getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Configuration\n" +
                "Using -->" + configurationFile +
                "\"\n" + settingsObject.toString(4);
    }
}
