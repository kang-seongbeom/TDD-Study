package com.example.tddstudy.learning;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learning/api/v1")
public class LearningController {

    @GetMapping("/get/pathvariable/{id}/{name}")
    public ResponseEntity<LearningDto> getPathVariable(@PathVariable final long id,
                                               @PathVariable final String name){
        LearningDto dto = LearningDto.builder().id(id).name(name).build();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(dto);
    }

    @GetMapping("/get/requestparam")
    public ResponseEntity<LearningDto> getRequestParam(@RequestParam final long id,
                                                       @RequestParam final String name){
        LearningDto dto = LearningDto.builder().id(id).name(name).build();

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/get/requestbody")
    public ResponseEntity<LearningDto> getRequestBody(@RequestBody LearningDto requestDto){
        LearningDto dto = LearningDto.builder().id(requestDto.id).name(requestDto.name).build();

        return ResponseEntity.ok().body(dto);
    }

}
