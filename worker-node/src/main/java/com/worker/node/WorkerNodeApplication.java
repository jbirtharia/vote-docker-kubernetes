package com.worker.node;

import com.worker.node.dto.Votes;
import com.worker.node.service.RedisService;
import com.worker.node.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class WorkerNodeApplication implements CommandLineRunner {

	public static final String DOG_VOTE = "dog-vote";
	public static final String CAT_VOTE = "cat-vote";

	@Autowired
	private RedisService redisService;

	@Autowired
	private VoteService voteService;

	public static void main(String[] args) {
		SpringApplication.run(WorkerNodeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		while (true) {
			Votes catVotes = redisService.get(CAT_VOTE, Votes.class);
			Votes dogVotes = redisService.get(DOG_VOTE, Votes.class);
			if (catVotes != null && !catVotes.isAllProcessed()) {
				voteService.saveVote(CAT_VOTE, catVotes);
				log.info("All Cat votes has been processed");
			} else if (dogVotes != null && !dogVotes.isAllProcessed()) {
				voteService.saveVote(DOG_VOTE, dogVotes);
				log.info("All Dog votes has been processed");
			} else {
				log.info("No votes to process");
			}
			Thread.sleep(10000);
		}
	}
}
