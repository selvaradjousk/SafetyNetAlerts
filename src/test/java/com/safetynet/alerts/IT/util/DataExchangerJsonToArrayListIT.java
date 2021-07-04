package com.safetynet.alerts.IT.util;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataExchangerJsonToArrayList;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataExchangerJsonToArrayListIT {

    @Autowired
    private DataExchangerJsonToArrayList dataExchangerJsonToArrayList;

    @Test
    public void whenGetPersonList_thenReturnExpectedPersonList() {
        List<Person> listOfPersons = dataExchangerJsonToArrayList.getPersonList();

        assertNotNull(listOfPersons);
        assertEquals(23, listOfPersons.size());
    }

}
