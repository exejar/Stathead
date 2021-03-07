package me.exejar.stathead.champstats.statapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.exejar.stathead.champstats.config.ModConfig;
import me.exejar.stathead.champstats.statapi.exception.ApiRequestException;
import me.exejar.stathead.champstats.statapi.exception.InvalidKeyException;
import me.exejar.stathead.champstats.statapi.exception.PlayerNullException;
import me.exejar.stathead.utils.References;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HypixelAPI {

    private final String key = ModConfig.getInstance().getApiKey();
    public JsonObject achievementObj;

    /**
     * @param uuid Target Player's UUID
     * @param game Game Stats to retrieve
     * @return JsonObject of the specified gameType's Stats
     * @throws InvalidKeyException If Hypixel API Key is Invalid
     * @throws PlayerNullException If Target Player UUID is returned Null from the Hypixel API
     * @throws ApiRequestException If any other exception is thrown during the request
     */
    public JsonObject getGameData(String uuid, HypixelGames game) throws InvalidKeyException, PlayerNullException, ApiRequestException {
        JsonObject obj = new JsonObject();
        if (key == null) {
            throw new InvalidKeyException();
        } else {
            String requestURL = String.format("https://api.hypixel.net/player?key=%s&uuid=%s", key, uuid.replace("-", ""));
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(requestURL);
                JsonParser parser = new JsonParser();

                obj = parser.parse(new InputStreamReader(client.execute(request).getEntity().getContent(), StandardCharsets.UTF_8)).getAsJsonObject();
                System.out.println(References.MODNAME + " Stat Checking: " + uuid);

                if (obj.get("player") == null) {
                    if (obj.get("cause").getAsString().equalsIgnoreCase("Invalid API key")) throw new InvalidKeyException();
                    throw new PlayerNullException();
                }
                else if (obj.get("player").toString().equalsIgnoreCase("null"))
                    throw new PlayerNullException();
                else if (obj.get("success").getAsString().equals("false"))
                    throw new ApiRequestException();
            } catch (IOException ex) {
                System.err.println(References.MODNAME + " setGameData: " + ex.getStackTrace().toString());
            }
        }

        JsonObject player = obj.get("player").getAsJsonObject();

        this.achievementObj = player.get("achievements").getAsJsonObject();

        JsonObject stats = player.get("stats").getAsJsonObject();
        obj = stats.get(game.getApiName()).getAsJsonObject();

        return obj;
    }

    public JsonObject getPlayerData(String uuid) throws InvalidKeyException, PlayerNullException, ApiRequestException {
        JsonObject obj = new JsonObject();
        if (key == null) {
            throw new InvalidKeyException();
        } else {
            String requestURL = String.format("https://api.hypixel.net/player?key=%s&uuid=%s", key, uuid.replace("-", ""));
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(requestURL);
                JsonParser parser = new JsonParser();

                obj = parser.parse(new InputStreamReader(client.execute(request).getEntity().getContent(), StandardCharsets.UTF_8)).getAsJsonObject();
                System.out.println(References.MODNAME + " Stat Checking: " + uuid);

                if (obj.get("player") == null) {
                    if (obj.get("cause").getAsString().equalsIgnoreCase("Invalid API key")) throw new InvalidKeyException();
                    throw new PlayerNullException();
                }
                else if (obj.get("player").toString().equalsIgnoreCase("null"))
                    throw new PlayerNullException();
                else if (obj.get("success").getAsString().equals("false"))
                    throw new ApiRequestException();
            } catch (IOException ex) {
                System.err.println(References.MODNAME + " setGameData: " + ex.getStackTrace().toString());
            }
        }

        JsonObject player = obj.get("player").getAsJsonObject();
        this.achievementObj = player.get("achievements").getAsJsonObject();

        return obj;
    }

    @Deprecated
    /* Removed as per request from sk1er. Although, I do believe this to be a better request method than the one provided in Levelhead.
    *  This project is not associated with Sk1er.
    */
    public JsonObject setSk1erData(String uuid, String data) throws PlayerNullException {
        JsonObject obj = new JsonObject();
        String requestUrl = String.format("https://api.sk1er.club/levelheadv5/%s/%s", uuid.replace("-", ""), data);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(requestUrl);
            JsonParser parser = new JsonParser();

            obj = parser.parse(new InputStreamReader(client.execute(request).getEntity().getContent(), StandardCharsets.UTF_8)).getAsJsonObject();
            System.out.println(References.MODNAME + " Stat Checking: " + uuid);

            if (obj.get("success").getAsString().equalsIgnoreCase("false")) {
                if (obj.get("reason").getAsString().contains("Type") && obj.get("reason").getAsString().contains("not found")) {
                    System.err.println("Type not found?? EXTREME ERROR PLEASE REPORT TO MAX#9876");
                }
            } else if (obj.get("bot") != null) {
                if (obj.get("bot").getAsString().equalsIgnoreCase("true")) {
                    throw new PlayerNullException();
                }
            }
        } catch (IOException ex) {
            System.err.println(References.MODNAME + " setGameData: " + ex.getStackTrace().toString());
        }

        return obj;
    }

    /**
     * @param name Target's Minecraft Name
     * @return UUID of Target using Mojang API
     */
    public static String getUUID(String name) {
        String uuid = "";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(String.format("https://api.mojang.com/users/profiles/minecraft/%s", name));
            try (InputStream is = client.execute(request).getEntity().getContent()) {
                JsonParser jsonParser = new JsonParser();
                JsonObject object = jsonParser.parse(new InputStreamReader(is, StandardCharsets.UTF_8)).getAsJsonObject();
                uuid = object.get("id").getAsString();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return uuid;
    }

}
