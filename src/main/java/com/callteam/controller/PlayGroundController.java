package com.callteam.controller;

import com.callteam.service.PlayGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/playground")
public class PlayGroundController {

    @Autowired
    private PlayGroundService playGroundService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam(required = false,name = "file") MultipartFile multipartFile , @RequestParam(name = "playGround") String playGround){
        return playGroundService.save(multipartFile,playGround);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestParam(required = false,name = "file") MultipartFile multipartFile , @RequestParam(name = "playGround") String playGround){
        return playGroundService.update(multipartFile,playGround);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return playGroundService.delete(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllGrounds(){
        return playGroundService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return playGroundService.getById(id);
    }

    @GetMapping("/{index}")
    public ResponseEntity<?> getAllGroundsByIndex(@PathVariable int index){
        return playGroundService.getAllByPage(index);
    }

    @GetMapping("/getAllByCity/{city}")
    public ResponseEntity<?> getAllByCity(@PathVariable String city){
        return playGroundService.getAllByCity(city);
    }

    @GetMapping("/pageCount")
    public ResponseEntity<?> pagesCount(){
        return playGroundService.pagesCount();
    }

    @GetMapping("/getAllBySportId/{id}")
    public ResponseEntity<?> getAllBySportId(@PathVariable String id){
        return playGroundService.getAllBySportId(id);
    }

}
