package com.jarboot.example.stock.server.cmd;

import com.mz.jarboot.api.cmd.annotation.Name;
import com.mz.jarboot.api.cmd.session.CommandSession;
import com.mz.jarboot.api.cmd.spi.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.instrument.Instrumentation;

/**
 * 测试jdk SPI
 * @author majianzheng
 */
@Name("spring.cmd")
@Component
public class DemoCommandProcessor implements CommandProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DemoCommandProcessor.class);
    @Override
    public void postConstruct(Instrumentation instrumentation, String server) {
        logger.info("postConstruct {}", server);
    }

    @Override
    public String process(CommandSession session, String[] args) {
        return "Hello";
    }
}
