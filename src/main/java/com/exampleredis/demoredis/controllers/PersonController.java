package com.exampleredis.demoredis.controllers;

import com.exampleredis.demoredis.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    @Autowired
    RedisTemplate<String,Object> template;
//--------------------------Value operations ----------------------------


    @GetMapping("/getValue")
    public String getValue(@RequestParam("key") String key){
        return (String)template.opsForValue().get(key);
    }

    @PostMapping("/setValue")
    public void setValue(@RequestParam("key") String key,
                         @RequestParam("value") String value,
                         @RequestParam(value = "expiry",required =false) Integer expiry){
        if (expiry==null){
            template.opsForValue().set(key,value);
        }
        else {


//            Long timeUnit=TimeUnit.SECONDS.toSeconds(expiry);
//            template.opsForValue().set(key,value,timeUnit);

            template.opsForValue().set(key,value, Duration.ofSeconds(expiry));
        }
    }

//-------------------------------Value Operations-------------------------------------
//--------------------------------List Operations -------------------------------------

    //1. To retrieve from index l to index r
    //2. lpush
    //3. rpush
    //4. lpop
    //5. rpop
    @GetMapping("/lrange")
    public List<User> getUsersByList(@RequestParam("key") String key,
                                     @RequestParam(value = "start",required = false,defaultValue = "0") long start,
                                     @RequestParam(value = "end",required = false,defaultValue = "-1") long end){
        return template
                .opsForList()
                .range(key,start,end)
                .stream()
                .map(x->(User)x)
                .collect(Collectors.toList());
        // This actually returns you a List<V> which is List<Object>
        //type cast this to List<User> using stream
        //List<Object> works with every other data type

    }

    @PostMapping("/lpush")
    public Long Lpush(@RequestParam("key") String key,
                      @RequestBody User user ){
        return template.opsForList().leftPush(key,user);
    }

    @PostMapping("/rpush")
    public Long Rpush(@RequestParam("key") String key,
                      @RequestBody User user){
        return template.opsForList().rightPush(key,user);
    }
    @GetMapping("/lpop")
    public User lpop(@RequestParam("key") String key){
        return (User)template.opsForList().leftPop(key);
    }

    @GetMapping("/rpop")
    public User rpop(@RequestParam("Key")String key){
        return (User)template.opsForList().rightPop(key);
    }

//------------------------------List Operations------------------------------------
//------------------------------Map Operations-------------------------------------

    @GetMapping("/hgetall")
    public Map<Object ,Object> hgetall(@RequestParam("key") String key){
        Map<Object,Object> maptoReturn=new HashMap<>();
        List<Object> fields= Arrays.asList("id","name","country","age");
        List<Object> values = template.opsForHash().multiGet(key,fields);
        for (int i=0;i<fields.size();i++){
            maptoReturn.put(fields.get(i),values.get(i));
        }
        return maptoReturn;
    }

    @PostMapping("/hmset")
    public void hmset(@RequestParam("key") String Key,
                      @RequestBody User user ){
        Map<String, Object> fieldmap=new HashMap<>();
        fieldmap.put("age",user.getAge());
        fieldmap.put("country",user.getCountry());
        fieldmap.put("name",user.getName());
        fieldmap.put("id",user.getId());
        template.opsForHash().putAll(Key,fieldmap);
    }







}
