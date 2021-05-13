package org.whitehat.airports;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/airports")
public class WebController {
    public List<String> airports = new ArrayList<>();
    public Map<String, String> users = Map.of("test user", "password");

    public WebController() {
        airports.add("LHR");
        airports.add("NRT");
        airports.add("LAX");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("cabbage");

        System.out.println(hashedPassword);

        boolean isMatch = passwordEncoder.matches(hashedPassword, "$2a$10$MFDKo5y8czJsu4Gapjnk6uwCwKt1yblvtyu2rjMAE2QKh/H/UVAu6");
        boolean isMatchTwo = passwordEncoder.matches("won't match", "$2a$10$MFDKo5y8czJsu4Gapjnk6uwCwKt1yblvtyu2rjMAE2QKh/H/UVAu6");
        System.out.println(String.format("This should be true: %s. This should be false: %s", isMatch, isMatchTwo));

    }

    @PostMapping("/test")
    public String makeUser(@RequestBody String user) throws IOException {
        System.out.println(user);
        return user;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public List<String> getAirports() {
        return this.airports;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/airport")
    public String getAirport(HttpServletRequest request) {
        if (airports.contains(request.getQueryString())) {
            return request.getQueryString();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Doesn't exist"
            );
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public String addAirport(HttpServletRequest request) {
        System.out.println(request.getQueryString());
        airports.add(request.getQueryString());
        return("Succesfully added airport " + request.getQueryString());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete")
    public String deleteOne(HttpServletRequest request) {
        airports.remove(request.getQueryString().toUpperCase());
        return("Removed airport " + request.getQueryString());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/")
    public String deleteAll() {
        airports.removeAll(airports);
        System.out.println(airports);
        return("Removed all airports");
    }
}
