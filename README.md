## Producer.java

### To produce message as object
Add your message in ProducerRecord
```java
for (int i = 1; i <= 10; i++){
    User user=new User(i,"shubham",23,"MCA");
    kafkaProducer.send(new ProducerRecord("mytopic2",String.valueOf(user.getId()),user));
}
```
Here `mytopic2` is name of ***topic*** & `user` is object of **User** class

## Consumer.java
### To see consuming messages
Make sure you subscribed to the topic `mytopic2`
```java
List topics = new ArrayList();
topics.add("mytopic2");
kafkaConsumer.subscribe(topics);
```
### Writing Data into a file when consuming it
```java

while (true) {
        FileWriter fileWriter = new FileWriter("user-json-data.txt", true);
        ConsumerRecords<String, User> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, User> consumerRecord : consumerRecords) {
            System.out.printf(
                "Topic: %s, Partition: %d, Value: %s%n",
                consumerRecord.topic(),
                consumerRecord.partition(),
                consumerRecord.value().toString());
            fileWriter.write(consumerRecord.value().toString() + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
}
```
### Then
Just run the `Consumer.java` file. A terminal will open and there you can see your all messages from beginning produced by Producer.

### In user-json-data.txt
> {"id":1, "name":"shubham", "age":23, "course":"MCA"}
> 
> {"id":2, "name":"shubham", "age":23, "course":"MCA"}
> 
> {"id":3, "name":"shubham", "age":23, "course":"MCA"}
> 
> {"id":4, "name":"shubham", "age":23, "course":"MCA"}
> 
> {"id":5, "name":"shubham",  "age":23, "course":"MCA"}
> 

