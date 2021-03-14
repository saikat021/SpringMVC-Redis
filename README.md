## SpringMVC-Redis
An elaborate example of Spring MVC architecture service using Redis Cache as a storage unit 
# Some of the curl requests associated with this service:
curl -XPOST "127.0.0.1:8080/setValue?key=key1&value=value1" -v
curl -XPOST "127.0.0.1:8080/setValue?key=key2&value=value2" -v
curl -XGET "127.0.0.1:8080/getValue?key=key2" -v
curl -XPOST "127.0.0.1:8080/setValue?key=key2&value=value2&expiry=1000" -v
curl -XPOST "127.0.0.1:8080/lpush?key=users" -H "Content-type: application/json" -d '{"id":1,"name":"Saikat","country":"USA","age":30}'
curl -XPOST "127.0.0.1:8080/lpush?key=users" -H "Content-type: application/json" -d '{"id":1,"name":"Saikat","country":"USA","age":30}'
curl -XGET "127.0.0.1:8080/lrange?key=users" -v
curl -XPOST "127.0.0.1:8080/rpush?key=users" -H "Content-type: application/json" -d '{"id":1,"name":"Shampa","country":"USA","age":50}'
curl -XPOST "127.0.0.1:8080/rpush?key=users" -H "Content-type: application/json" -d '{"id":1,"name":"Sumit","country":"India","age":62}'
curl -XPOST "127.0.0.1:8080/rpush?key=users" -H "Content-type: application/json" -d '{"id":1,"name":"Debmalya","country":"India","age":22}' -v
curl -XGET "127.0.0.1:8080/lrange?key=users" -v
curl -XGET "127.0.0.1:8080/lpop" -v
curl -XGET "127.0.0.1:8080/lpop?key=users" -v
curl -XGET "127.0.0.1:8080/lrange?key=users" -v
curl -XGET "127.0.0.1:8080/rpop?key=users" -v
curl -XGET "127.0.0.1:8080/rpop?Key=users" -v
curl -XGET "127.0.0.1:8080/lrange?key=users" -v
curl -XPOST "127.0.0.1:8080/hmset?key=user_set" -H "Content-type: application/json" -d '{"id":1,"name":"John","country":"USA","age":22}' -v
curl -XGET "127.0.0.1:8080/hgetall?key=user_set" -v
curl -XPOST "127.0.0.1:8080/hmset?key=user_set" -H "Content-type: application/json" -d '{"id":1,"name":"Jim","country":"India","age":22}' -v
curl -XGET "127.0.0.1:8080/hgetall?key=user_set" -v
curl -XPOST "127.0.0.1:8080/hmset?key=user_set" -H "Content-type: application/json" -d '{"id":1,"name":"Jim","country":"India","age":22}' -v
curl -XGET "127.0.0.1:8080/hgetall?key=user_set" -v
curl -XGET "127.0.0.1:8080/hgetall?key=user_set" -v

# Some of the methods by which the cache is used:
key is of String type
key-->value(<String>) pair 
key-->List<Object> pair 
key-->Map<String,Object> pair

# Config class written so that proper RedisTemplate can be created 
RedisTemplate takes in the following objects:
1. Redis Connection 
2. StringSerializer for key <String> serialization 
3. JdkSerializer for value <Object> serialization 
A bean of this template created and autowired in the controller class 


# Concept of Serialization and Deserialization
Importance of Serialization and User object implementing Serialization interface 
Redis does not understand Java User class objects so it is the responsibility of java to Serialize and send the User object in such a format that it can be stored in redis, So User object implements Serializable interface. Every primitive datatype and non-primitive previously defined datatypes alreday implement Serializable interface example : int, String, List<Object>, HashMap<Object> etc....  