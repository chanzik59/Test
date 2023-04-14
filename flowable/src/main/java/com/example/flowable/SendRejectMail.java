package com.example.flowable;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author chenzhiqin
 * @date 13/4/2023 11:53
 * @info XX
 */
@Slf4j
public class SendRejectMail implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("发送拒绝邮件");

    }
}
