package org.whitehat.airports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/airports")
public class WebController {
    public List<String> airports = new ArrayList<>();

    public WebController() {
        airports.add("LHR");
        airports.add("NRT");
        airports.add("LAX");
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
