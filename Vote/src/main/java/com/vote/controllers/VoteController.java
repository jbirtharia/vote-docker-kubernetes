package com.vote.controllers;

import com.vote.entities.Votes;
import com.vote.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class VoteController {

    public static final String DOG_VOTE = "dog-vote";
    public static final String CAT_VOTE = "cat-vote";

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public String vote(){
        return "index";
    }

    @PostMapping("/submit")
    public String submit(@RequestParam("vote") String vote){
        log.info("Vote --->> {}", vote);
        saveVote(vote);
        return "index";
    }

    private void saveVote(String vote) {
        if (vote.equalsIgnoreCase("cats")) {
            Votes catVotes = redisService.get(CAT_VOTE, Votes.class);
            if (catVotes == null) {
                Votes newCatVote = createNewVoteEntity(vote);
                redisService.set(CAT_VOTE, newCatVote);
            }else {
                catVotes.setCount(catVotes.getCount() + 1);
                catVotes.setAllProcessed(false);
                redisService.set(CAT_VOTE, catVotes);
            }
        }else if (vote.equalsIgnoreCase("dogs")) {
            Votes dogVotes = redisService.get(DOG_VOTE, Votes.class);
            if (dogVotes == null) {
                Votes newDogVote = createNewVoteEntity(vote);
                redisService.set(DOG_VOTE, newDogVote);
            }else {
                dogVotes.setCount(dogVotes.getCount() + 1);
                dogVotes.setAllProcessed(false);
                redisService.set(DOG_VOTE, dogVotes);
            }
        }else {
            log.error("Invalid vote");
        }
    }

    private static Votes createNewVoteEntity(String vote) {
        Votes newCatVote = new Votes();
        newCatVote.setVote(vote);
        newCatVote.setId(1L);
        newCatVote.setCount(1L);
        newCatVote.setAllProcessed(false);
        return newCatVote;
    }
}
