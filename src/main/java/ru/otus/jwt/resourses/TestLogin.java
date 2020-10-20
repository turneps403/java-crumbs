package ru.otus.jwt.resourses;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestLogin {
    @RequestMapping("/lookup")
    public Map testtest(@RequestParam String email, @RequestParam String password) {
        int uid = ThreadLocalRandom.current().nextInt();
        return Collections.singletonMap("user", String.valueOf(uid));
    }
}
