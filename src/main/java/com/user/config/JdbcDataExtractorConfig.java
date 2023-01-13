package com.user.config;

import com.user.model.user.UserRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
public class JdbcDataExtractorConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public List<String> flowSpecificQueries(@Value("classpath:/flowQueries.sql") Resource flowQueries) throws IOException{

        List<String> statements = new ArrayList<>();

        EncodedResource queryResource = new EncodedResource(flowQueries);

        String script = ScriptUtils.readScript(
                new LineNumberReader(queryResource.getReader()),
                ScriptUtils.DEFAULT_STATEMENT_SEPARATOR,
                ScriptUtils.DEFAULT_COMMENT_PREFIX,
                ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER
                );
        ScriptUtils.splitSqlScript(script, ScriptUtils.DEFAULT_STATEMENT_SEPARATOR, statements);
        List<Map<String, Object>> userRegister = null;
        for(String sql: statements){
            userRegister=jdbcTemplate.queryForList(sql);
        }
        return statements;
    }

}
