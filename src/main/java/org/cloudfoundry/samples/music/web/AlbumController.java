package org.cloudfoundry.samples.music.web;

import org.cloudfoundry.samples.music.domain.Album;
import org.cloudfoundry.samples.music.tools.ConvertingTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class AlbumController {
    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);
    private CrudRepository<Album, String> repository;

    @Autowired
    public AlbumController(CrudRepository<Album, String> repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Album> albums() {
        return repository.findAll();
    }

    @RequestMapping(value = "/columns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album columns() {
        return new Album();
    }

    @RequestMapping(
            value = "/employee",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Album> getEmployee(@RequestParam("query") String query){

        List<Album> res = new ArrayList<>();
        Iterable<Album> all = repository.findAll();
        if(all != null && query != null) {
            for (Album album : all) {
                if (album.getArtist()!= null && album.getArtist().trim().toLowerCase().contains(query.toLowerCase().trim())
                        || album.getGenre()!= null &&  album.getGenre().trim().toLowerCase().contains(query.toLowerCase().trim())
                        || album.getReleaseYear()!= null &&  album.getReleaseYear().trim().toLowerCase().contains(query.toLowerCase().trim())
                        || album.getTitle()!= null &&  album.getTitle().trim().toLowerCase().contains(query.toLowerCase().trim())
                        )
                    res.add(album);
            }
        }

        return res;
    }

    @RequestMapping(value="/employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album add(@RequestBody String json) {
        HashMap<String, String> map= ConvertingTools.JSONStringToHashMap(json);
        Album album = ConvertingTools.HashMapToAlbum(map);
        logger.info("Adding album " + album.getId());
        return album;
    }

    @RequestMapping(value="/employee",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album update(@RequestBody String json) {
        HashMap<String, String> map= ConvertingTools.JSONStringToHashMap(json);
        Album album = ConvertingTools.HashMapToAlbum(map);
        repository.save(album);
        logger.info("Updating album " + album.getId());
        return album;
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Album getById(@PathVariable String id) {
        logger.info("Getting album " + id);
        return repository.findOne(id);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteById(@PathVariable String id) {
        logger.info("Deleting album " + id);
        return id;
    }
}