## Content Moderation System

The application takes a csv input, in the format:

| user_id |    message     |
|---------|:--------------:|
| 1       | user message 1 |

Where:

- user_id: string. The identifier of the user
- message: string. The message written by the user

Each message will be translated to English (if not already in English) and scored on the
offensiveness of the message from 0 - 1.

These values will then be returned in the following format:

| user_id | total_messages | avg_score             |
|---------|:--------------:|:----------------------|
| 1       | user message 1 | average message score |

Where:

- user_id: string
- total_messages: integer. The total number of messages written by the user.
- avg_score: float. The average offensiveness score for all the messages of the same user.

> ⚠️ The translation and scoring services are both currently mocked. The scoring service will
> only be responding with random number between 0 - 1

The input and output locations can be set in the `application.properties`

The current default location locations are:

```
csv.file.input=src/main/resources/input/input.csv
csv.file.output=src/main/resources/output/output.csv
```

### Running the application

### On Mac/Linux

```
./gradlew bootRun
```

### On Windows

```
gradlew.bat bootRun
```

