package com.fr.miage.Controller;

import com.fr.miage.Dice;
import com.fr.miage.Modele.DiceRollLog;
import com.fr.miage.Repository.DiceRollLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DiceController {

    @Autowired
    private Dice dice;

    @Autowired
    private DiceRollLogRepository diceRollLogRepository;

    @GetMapping("/rollDice")
    public int rollDice() {
        int result = dice.roll();
        logDiceRoll(1, List.of(result));
        return result;
    }

    @GetMapping("/rollDices/{count}")
    public List<Integer> rollDices(@PathVariable int count) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(dice.roll());
        }
        logDiceRoll(count, results);
        return results;
    }

    private void logDiceRoll(int diceCount, List<Integer> results) {
        DiceRollLog log = new DiceRollLog();
        log.setDiceCount(diceCount);
        log.setResults(results);
        log.setTimestamp(LocalDateTime.now());
        diceRollLogRepository.save(log);
    }
}