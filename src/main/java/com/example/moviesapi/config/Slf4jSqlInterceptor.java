package com.example.moviesapi.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Slf4jSqlInterceptor implements StatementInspector {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jSqlInterceptor.class);

    @Override
    public String inspect(String sql) {
        logger.debug("SQL: {}", sql);
        return sql;
    }
}