package com.safetynet.alerts.IT.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataExchangerJsonToArrayList;

@DisplayName("IT - Reading JsonFile To ArrayList")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataExchangerJsonToArrayListIT {

    @Autowired
    private DataExchangerJsonToArrayList dataExchangerJsonToArrayList;

    @DisplayName("ReadingJsonInputFile for PERSON"
    		+ " - Given input Json File to read"
    		+ "When get Person List"
    		+ "then return expected number of entries in the list")
    @Test
    public void whenGetPersonList_thenReturnExpectedPersonList() {
        List<Person> listOfPersons = dataExchangerJsonToArrayList.getPersonList();

        assertNotNull(listOfPersons);
        assertEquals(23, listOfPersons.size());
    }

}
