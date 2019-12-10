package com.trilogyed.util.feign;

import com.trilogyed.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @GetMapping(value = "/levelUp/customer/{id}")
    LevelUp retrieveLevelUpByCustomerId(@PathVariable int id);

    @PostMapping(value = "/levelUp")
    LevelUp addLevelUp(@RequestBody LevelUp levelUp);

    @PutMapping(value = "/levelUp")
    void updateLevelUp(@RequestBody LevelUp levelUp);

    @DeleteMapping(value = "/batchDel")
    void deleteAll();
}