package me.dooger.betterlvlhead.champstats.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.dooger.betterlvlhead.utils.ChatUtils;
import me.dooger.betterlvlhead.champstats.utils.Handler;
import me.dooger.betterlvlhead.utils.References;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.HashMap;

import static me.dooger.betterlvlhead.champstats.config.ModConfigNames.APIKEY;
import static me.dooger.betterlvlhead.utils.References.VERSION;


public class ModConfig {

    private String apiKey;
    private static ModConfig instance;

    public static ModConfig getInstance() {
        if (instance == null) instance = new ModConfig();
        return instance;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String key) {
        this.apiKey = key;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void makeFile() {
        try {
            if (!getFile().exists()) {
                getFile().getParentFile().mkdir();
                getFile().createNewFile();
                try (FileWriter writer = new FileWriter(getFile())) {
                    writer.write("{}");
                    writer.flush();
                    save();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfigFromFile() {
        if (!getFile().exists()) makeFile();
        apiKey = getString(APIKEY);
    }

    public File getFile() {
        String here = Paths.get("").toAbsolutePath().toString();
        String file = here + File.separator + References.MODID + File.separator + "config.json";
        return new File(file);
    }

    public void init() {
        loadConfigFromFile();
    }

    public void save() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(APIKEY.toString(), getApiKey());
        try (Writer writer = new FileWriter(getFile())) {
            Handler.getGson().toJson(map, writer);
        } catch (Exception e) {
            System.out.println("Unable to write to Config File!");
            e.printStackTrace();
            ChatUtils.sendMessage("UNABLE TO SAVE CONFIG! Severe error with Mod version: " + VERSION + ", You should report this!");
        }
    }

    public String getString(ModConfigNames key) {
        JsonParser parser = new JsonParser();
        String s = "";
        try {
            JsonObject object = (JsonObject) parser.parse(new FileReader(getFile()));
            s = object.get(key.toString()).getAsString();
        } catch (NullPointerException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
