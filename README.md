### Publisher
Publisher is responsible for publishing messages to their respective queue.
Publisher doesn't interacts with Subscriber directly. Instead it will invoke pubsub service to inject messages in the queue.
Each queue can have multiple topics.

#### Constructor: Publisher(QueueName)

### Subscriber
Subscribers are subscribed to topics. Each subscriber can be subscribed to multiple topics.

#### Subscriber(Endpoint -> Webhook)
Above endpoint will be invoked whenever new message is received from PubSubService.

### PubSubService
PubSubService is responsible for enqueuing messages to the queue as well as responsible for delivering messages to the respective subscribers.
Service maintains Topic-Subscriber mapping as well as queue-publisher mapping.

### Main Class: MessageQueuingService
MessageQueuingService is a main class which starts the worker threads as well as input is hardcoded in the main class for testing.

### Testing Steps
1. mvn clean compile
2. mvn package
3. java -jar target/MessageQueuingSystem-1.0.jar  