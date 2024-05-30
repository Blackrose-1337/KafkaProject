package blackrose.producer.controller;

import blackrose.producer.dto.AdUserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class WebhookController {

    private final static Logger LOG = Logger.getLogger(WebhookController.class);
    private final KafkaTemplate<String, AdUserDto> kafkaTemplate;

    @Value("${git.url}")
    private String GITHUB_API_URL;

    @Value("${git.token}")
    private String TOKEN;

    @Value("${git.repo.name}")
    private String REPO_NAME;

    @Value("${git.repo.owner}")
    private String REPO_OWNER;

    @Value("${company.kafka.topic}")
    private String TOPIC;

    public WebhookController(KafkaTemplate<String, AdUserDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebhookController.class, args);
    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            final String commitId = (String) payload.get("after");
            final String previousCommitId = (String) payload.get("before");

            @SuppressWarnings("unchecked")
            Map<String, Object> headCommit = (Map<String, Object>) payload.get("head_commit");
            final String commitMessage = (String) headCommit.get("message");

            LOG.info("-----------------------------------------------------");
            LOG.info("Owner: " + REPO_OWNER);
            LOG.info("Commit: " + commitId);
            LOG.info("Commit Message: " + commitMessage);
            LOG.debugf("Webhook Payload: %s", payload);


            List<String> addPath = getStringList(headCommit.get("added"));
            List<String> removed = getStringList(headCommit.get("removed"));
            List<String> modified = getStringList(headCommit.get("modified"));

            Result result = getPaths(addPath, removed, modified);

            String firstFile = getFileContent(previousCommitId, result.previousPath());
            String secondFile = getFileContent(commitId, result.currentPath());

            compareAndProcessJson(firstFile, secondFile);
        } catch (Exception e) {
            LOG.error("Error parsing payload", e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getStringList(Object obj) {
        if (obj instanceof List<?> list) {
            if (list.isEmpty() || list.getFirst() instanceof String) {
                return (List<String>) list;
            }
        }
        return new ArrayList<>();
    }

    private static Result getPaths(List<String> addPath, List<String> removed, List<String> modified) {
        String currentPath = "";
        String previousPath = "";
        if (!addPath.isEmpty()) {
            LOG.infof("added: %s", addPath);
            currentPath = addPath.getFirst();
            if (!removed.isEmpty()) {
                LOG.infof("removed: %s", removed);
                previousPath = removed.getFirst();
            }
        } else if (!modified.isEmpty()) {
            LOG.infof("modified: %s", modified);
            previousPath = modified.getFirst();
            currentPath = previousPath;
        }
        return new Result(previousPath, currentPath);
    }

    private record Result(String previousPath, String currentPath) {
    }


    private String getFileContent(String commitId, String path) {
        try {
            HttpURLConnection conn = getHttpURLConnection(commitId, path);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> jsonResponse = objectMapper.readValue(response.toString(), new TypeReference<>() {
                    });
                    String encodedContent = (String) jsonResponse.get("content");
                    encodedContent = encodedContent.replaceAll("\\n", "");
                    byte[] decodedContent = Base64.getDecoder().decode(encodedContent);
                    return new String(decodedContent);
                }
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error retrieving file content", e);
            return null;
        }
    }


    private HttpURLConnection getHttpURLConnection(String commitId, String path) throws IOException {
        String apiUrl = GITHUB_API_URL + "/repos/" + REPO_OWNER + "/" + REPO_NAME + "/contents/" + path + "?ref=" + commitId;
        URL url = URI.create(apiUrl).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String encodedToken = Base64.getEncoder().encodeToString((TOKEN + ":").getBytes());
        conn.setRequestProperty("Authorization", "Basic " + encodedToken);
        return conn;
    }

    private void compareAndProcessJson(String before, String after) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            List<AdUserDto> usersBefore = objectMapper.readValue(before, objectMapper.getTypeFactory().constructCollectionType(List.class, AdUserDto.class));
            List<AdUserDto> usersAfter = objectMapper.readValue(after, objectMapper.getTypeFactory().constructCollectionType(List.class, AdUserDto.class));


            for (AdUserDto userAfter : usersAfter) {
                if (!usersBefore.contains(userAfter)) {
                    LOG.info("Added user: " + userAfter);
                    kafkaTemplate.send(TOPIC, userAfter);
                }
            }

            for (AdUserDto userBefore : usersBefore) {
                if (!usersAfter.contains(userBefore)) {
                    LOG.info("Removed user: " + userBefore);
                }
            }
        } catch (Exception e) {
            LOG.error("Error comparing JSON files", e);
        }
    }
}