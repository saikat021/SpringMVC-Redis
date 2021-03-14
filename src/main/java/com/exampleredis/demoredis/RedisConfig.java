package com.exampleredis.demoredis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    //Connection factory --> lettuce connection /jedis connection
    //Redis  template
    @Bean
    LettuceConnectionFactory getFactory(){
        LettuceConnectionFactory connectionFactory=new LettuceConnectionFactory(
                new RedisStandaloneConfiguration("localhost",6379)
        );
        return connectionFactory;

        //For connecting with redis labs
        //Redis on cloud


//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration
//                ("redis-15420.c245.us-east-1-3.ec2.cloud.redislabs.com", 15420);
//This url must be your own
        //Same with password
//        redisStandaloneConfiguration.setPassword("OiqNvnffAhnfP6s6jLCrS4nRK5p2DksN");
//
//        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
//
//        return connectionFactory;



    }
    //If given only string in V then  list and hashes will give error
    @Bean
    RedisTemplate<String ,Object> getTemplate(){
        RedisTemplate<String ,Object> template=new RedisTemplate<>();
        RedisSerializer<String> stringRedisSerializer =new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer=new JdkSerializationRedisSerializer();
        template.setValueSerializer(jdkSerializationRedisSerializer);
        template.setHashValueSerializer(jdkSerializationRedisSerializer);
        template.setConnectionFactory(getFactory());
        return template;

    }
}
