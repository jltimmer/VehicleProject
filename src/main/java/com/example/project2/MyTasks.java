package com.example.project2;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MyTasks {

    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 3000)
    public void addVehicle() {
        String url = "http://localhost:8080/addVehicle";
        Vehicle newVehicle = new Vehicle(RandomStringUtils.randomAlphabetic(10),
                RandomUtils.nextInt(1986, 2016), RandomUtils.nextInt(15000, 45000));
        restTemplate.postForObject(url, newVehicle, Vehicle.class);
    }

    @Scheduled(fixedRate = 3000)
    public void deleteVehicle() {
        int randID = RandomUtils.nextInt(1, 20);
        String url = "http://localhost:8080/deleteVehicle/" + randID;
        restTemplate.delete(url);
    }

    @Scheduled(fixedRate = 4000)
    public void updateVehicle() {
        String url = "http://localhost:8080/updateVehicle";
        int randID = RandomUtils.nextInt(1, 20);
        Vehicle newVehicle = new Vehicle(randID, RandomStringUtils.randomAlphabetic(10),
                RandomUtils.nextInt(1986, 2016), RandomUtils.nextInt(15000, 45000));
        restTemplate.put(url, newVehicle);

        /*
        String getUrl = "http://localhost:8080/getVehicle/" + randID;
        Vehicle v = restTemplate.getForObject(getUrl, Vehicle.class);
        System.out.println(v);*/
    }

    @Scheduled(cron="0 0 * * * *")
    public void getLatestVehicles() {
        String getUrl = "http://localhost:8080/getLatestVehicles";
        List<Vehicle> vehicles = restTemplate.getForObject(getUrl, List.class);
        for (int i = vehicles.size() - 1; i >= 0; i--) {
            System.out.println(vehicles.get(i));
        }
    }
}