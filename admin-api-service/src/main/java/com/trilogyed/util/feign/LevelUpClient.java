package com.trilogyed.util.feign;

import com.trilogyed.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @GetMapping(value = "/levelUps")
    public List<LevelUp> getAllLevelUps();

    @GetMapping(value = "/levelUp/{id}")
    public LevelUp getLevelUpById(@PathVariable int id);

    @GetMapping(value = "/levelUp/customer/{id}")
    public LevelUp getLevelUpByCustomerId(@PathVariable int id);

    @PostMapping(value = "/levelUp")
    public LevelUp addLevelUp(@RequestBody LevelUp levelUp);

    @PutMapping(value = "/levelUp")
    public void updateLevelUp(@RequestBody LevelUp levelUp);

}
