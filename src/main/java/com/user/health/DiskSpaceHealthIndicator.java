package com.user.health;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConditionalOnProperty(value = "DiskSpace-on.health-check", havingValue = "true")
public class DiskSpaceHealthIndicator extends AbstractHealthIndicator {

    private static final Log logger = LogFactory.getLog(DiskSpaceHealthIndicator.class);


    private final String pathString= ".";
    private final long threshold = 500;


    @Override
    protected void doHealthCheck(Health.Builder builder) {
        File path = new File(pathString);
        long diskFreeInBytes = path.getUsableSpace();
        System.out.println("This is disFreeInBytes");
        System.out.println(diskFreeInBytes);
        if (diskFreeInBytes >= threshold) {
            builder.up();
        } else {
            logger.warn(LogMessage.format(
                    "Free disk space at path '%s' below threshold. Available: %d bytes (threshold: %s)",
                    path.getAbsolutePath(), diskFreeInBytes, threshold));
            builder.down();
        }
        builder.withDetail("total", path.getTotalSpace()).withDetail("free", diskFreeInBytes)
                .withDetail("threshold", threshold).withDetail("path", path.getAbsolutePath())
                .withDetail("exists", path.exists());
    }

}