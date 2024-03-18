# Action plan

1) Input data will be provided as a CSV file whose columns are:
    - user_id: string. The identifier of the user
    - message: string. The message written by the user

Get the file using Path, map to class with Opencsv:

```java
class UserMessageInput {
    String userId;
    String message;
}
```

Which will be a list:

```java
List<UserMessageInput> userMessages;
```

3) Since the scoring service will only work with English text, there will be another REST API for a Translation service
   that receives the message string as an input and returns the same message translated to English, or the same message
   if the message was already in English.

Loop through the list, make a call with each message, respond with the translated message (in this case mock).

```java
String getTranslatedMessage(String message);

// Create a mapper from UserMessageInput -> UserMessage
List<TranslatedMessage> getTranslatedMessages(List<UserMessage> userMessage);
```

2) There will be a Scoring service, a REST API developed by another team, that will receive a string as an input and
   will return a float value between 0 and 1, assigning an offensiveness score to the message.

Loop from the translated message, return (mock) the score. For the output csv we will need:

```java
String userId;
int totalMessages; // this can be a counter with the requests
float avgScore; // for this, we will need the know the total score i.e. totalMessages / totalScore (for each user id) 
```

Which we should be able to get with:

```java
Map<String, MessageScore> messageScore;

class MessageScore {
    int totalMessages;
    float totalScore;
}
```

4) The output data will be a CSV file with these columns:
    - user_id: string
    - total_messages: integer. The total number of messages written by the user.
    - avg_score: float. The average offensiveness score for all the messages of the same user.

Will need to see how to output, but I assume it will need to be list:

```java
Map<String, MessageScore> ->List<UserMessagesOut>

class UserMessagesOut {
    String userId;
    int totalMessages;
    float avgScore;
}
```

## Thoughts:

- Where to read and write the file?
    - Resource folder?
    - Running the application: CommandLineRunner?
    - Exit the application after the csv is written

---

Additional requirements and considerations

a) The Translation and Scoring services will have some network latencies when interacting with them. These are expected
to be between 50ms and 200ms

- Mocking this, Thread.sleep (or something similar)

b) Your application needs to perform well even for large input data files with millions of entries.

- I am not sure how OpenCSV will handle this. May need to look into Spring batch?

c) The Translation and Scoring services are idempotent. Consider the scenario of bots spamming the social network with
the same message multiple times.

- I can add caching for this, but may also need to filter out duplicates

d) You can write your application in plain Java or use frameworks like Spring/SpringBoot

e) You may spend as much time as you like, but a few hours should be sufficient.

f) Please submit the source code with a README file with instructions on how to build and run it

g) We will evaluate the exercise considering the correctness, implementation, style, readability and test coverage.

---

### My considerations / during / after thoughts:

It could be worth translating the message and then making the call to the scoring service with that response.
This would reduce the need to map back to a list.

TODO:

- Map from csv input to UserMessage
    - and fix those tests
- Integration tests
    - Unit and integration tests using the same file...? or producing the same output file?
    - Actually - I can set that in the constructor for the unit test
- Mock the latency
- Filter out duplicates?
- Logging
- Comments
- Readme

