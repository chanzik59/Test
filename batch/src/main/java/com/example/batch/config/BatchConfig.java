package com.example.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenzhiqin
 * @date 14/4/2023 15:18
 * @info XX
 */
@Configuration
@Slf4j
public class BatchConfig {


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Tasklet tasklet() {
        return (stepContribution, chunkContext) -> {
            log.info("模拟执行step");
            return RepeatStatus.FINISHED;
        };

    }

    @Bean
    public Step step(Tasklet tasklet) {
        return stepBuilderFactory.get("start").tasklet(tasklet).build();
    }


    @Bean
    public Job job(Step step,BatchJobListener batchJobListener){
        return   jobBuilderFactory.get("firstJob1").start(step).listener(batchJobListener).build();
    }
}
