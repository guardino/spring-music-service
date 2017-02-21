package org.cloudfoundry.samples.music.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cloudfoundry.samples.music.domain.Album;
import org.cloudfoundry.samples.music.domain.RandomIdGenerator;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by shijian on 07/02/2017.
 */
public class ConvertingTools {

    /**
     * [Object1, Object2, Object3] => ["stringInObj1", "stringInObj2","stringInObj3"]
     * @param objects
     * @return
     */
    public static String[] ObjectArraryToStringArray(Object[] objects){
        int size = objects.length;
        String [] strings = new String[size];

        for (int i = 0; i < size; i++)
            strings[i] = objects[i].toString();
        return strings;
    }

    /**
     * [a, b, c] => "a, b, c"
     * @param strings
     * @return
     */
    public static String StringArrayToString(String[] strings) {
        StringBuffer sb = new StringBuffer();
        for(String string : strings) {
            sb.append("," + string);
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * parsing json string to hashMap
     * @param jsonString
     * @return
     */
    public static HashMap<String, String> JSONStringToHashMap(String jsonString) {

        HashMap<String, String> res = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        try {
            map = mapper.readValue(jsonString, Map.class);
        } catch (Exception e) { e.printStackTrace(); }
        System.out.println(map);
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey().trim();
            Object value = entry.getValue();
            res.put(key, value == null ? "" : value.toString());
        }
        return res;
    }

    public static Album HashMapToAlbum(HashMap<String, String> map) {

        Album album = new Album();
        RandomIdGenerator gen = new RandomIdGenerator();
        album.setAlbumId(map.get("albumId").trim());
        album.setArtist(map.get("artist").trim());
        album.setGenre(map.get("genre").trim());
        album.setTitle(map.get("title").trim());
        album.setReleaseYear(map.get("releaseYear").trim());
        if(map.get("id") == null) {
            album.setId(gen.generateId());
        } else {
            album.setId(map.get("id").trim());
        }
        return album;

    }

}
