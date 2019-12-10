package com.trilogyed.controller;

import com.trilogyed.model.LevelUp;
import com.trilogyed.messages.LevelUpEntry;
import com.trilogyed.repository.LevelUpRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class LevelUpController {
    public static final String TOPIC_EXHCANGE_NAME = "level-up-exchange";
    public static final String ROUTING_KEY = "levelUp.controller";

    private LevelUpRepo repo;
    private RabbitTemplate rabbitTemplate;


    @Autowired
    public LevelUpController(LevelUpRepo repo, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping(value = "/levelUps")
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {
        return repo.findAll();
    }

    @GetMapping(value = "/levelUp/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpById(@PathVariable int id) {
        if (repo.findById(id).isPresent()) {
            return repo.getOne(id);
        }
        return null;
    }

    @GetMapping(value = "/levelUp/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomerId(@PathVariable int id) {
        return repo.findByCustomerId(id);
    }

    @PostMapping(value = "/levelUp")
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp addLevelUp(@RequestBody LevelUp levelUp) {
        System.out.println("System processing message...");

        LevelUpEntry msg = new LevelUpEntry();
        msg.setLevelUp(levelUp.toString());

        rabbitTemplate.convertAndSend(TOPIC_EXHCANGE_NAME, ROUTING_KEY, msg);
        System.out.println("LevelUp membership created.");

        return repo.save(levelUp);
    }

    @PutMapping(value = "/levelUp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@RequestBody LevelUp levelUp) {

        System.out.println("System processing message...");

        LevelUpEntry msg = new LevelUpEntry();
        msg.setLevelUp(levelUp.toString());

        rabbitTemplate.convertAndSend(TOPIC_EXHCANGE_NAME, ROUTING_KEY, msg);

        repo.save(levelUp);
        System.out.println("LevelUp membership updated.");
    }

    @DeleteMapping(value = "/levelUp/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUpById(@PathVariable int id) {
        repo.deleteById(id);
    }

    @DeleteMapping(value = "/batchDel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        repo.deleteAll();
    }
}