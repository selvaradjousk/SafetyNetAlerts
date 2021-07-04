package com.safetynet.alerts.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;

@Component
public class DataExchangerJsonToArrayList {

    private final List<Person> personList = new ArrayList<>();

    @Value("${dataSourceJsonFilePath}")
    private String dataFilePath;

    @PostConstruct
    public void readJsonDataToPOJO() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream fileInputStream = new FileInputStream(dataFilePath)) {
            JsonNode readJsonData = mapper.readTree(fileInputStream);

            // Transfers the all person details in the corresponding array list
            JsonNode persons = readJsonData.at("/persons");
            for (JsonNode jsonNode : persons) {
                Person person = new Person(jsonNode.get("firstName").asText(),
                        jsonNode.get("lastName").asText(),
                        jsonNode.get("address").asText(),
                        jsonNode.get("city").asText(),
                        jsonNode.get("zip").asInt(),
                        jsonNode.get("phone").asText(),
                        jsonNode.get("email").asText());

                personList.add(person);
            }
         }
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
