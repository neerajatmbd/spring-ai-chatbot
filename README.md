# spring-ai-chatbot

A simple chatbot REST API built with [Spring AI](https://docs.spring.io/spring-ai/reference/) and
[Ollama](https://ollama.com/) as the model provider.

## Prerequisites

- Java 17 or later
- Maven 3.9+ (or use the included `mvnw` wrapper if present)
- [Ollama](https://ollama.com/) installed and running locally
- A model pulled in Ollama, e.g.:

  ```bash
  ollama pull llama3.2
  ```

## Build

```bash
mvn clean package
```

## Run

```bash
mvn spring-boot:run
```

The application starts on `http://localhost:8080` by default.

## Usage

### POST /api/chat

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Hello, who are you?"}'
```

Response:

```json
{"reply": "..."}
```

### GET /api/chat

For quick testing via browser or curl:

```bash
curl "http://localhost:8080/api/chat?message=Hello,%20who%20are%20you?"
```

A minimal HTML test page is also available at `http://localhost:8080/` after starting the app.

### Error handling

If Ollama is unreachable or fails to respond, the API returns an HTTP `503 Service Unavailable`
with a JSON error body instead of an unhandled exception, e.g.:

```json
{
  "error": "Chat service unavailable",
  "message": "Could not reach the Ollama model provider. Ensure Ollama is running and the configured model is pulled.",
  "timestamp": "2024-01-01T00:00:00Z"
}
```

## Configuration

Configuration lives in `src/main/resources/application.yml`:

```yaml
spring:
  ai:
    ollama:
      base-url: ${OLLAMA_BASE_URL:http://localhost:11434}
      chat:
        options:
          model: ${OLLAMA_MODEL:llama3.2}
```

You can override these via environment variables without touching the file:

```bash
export OLLAMA_BASE_URL=http://localhost:11434
export OLLAMA_MODEL=llama3.2
mvn spring-boot:run
```

Or pass them as JVM/command-line properties:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.ai.ollama.chat.options.model=mistral
```

## Project structure

- `ChatController` — thin REST controller exposing `POST /api/chat` and `GET /api/chat`.
- `ChatService` — wraps Spring AI's `ChatClient` to talk to the configured Ollama model.
- `ChatExceptionHandler` — translates chat/model failures into a `503` JSON error response.

