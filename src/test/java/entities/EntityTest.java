package entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {

    @Test
    public void Taxi_TestToString() {
        Taxi taxi = new Taxi(1, "", 1, null);
        assertEquals(
                "Taxi[size=1, capacity=5, registrationNumber=, headcount=1, location=null, node=null]",
                taxi.toString()
        );
    }

    @Test
    public void Party_TestToString() {
        Party party = new Party(1, null, "d");
        assertEquals(
                "Party[headcount=1, location=null, node=null]",
                party.toString()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void Taxi_TestHeadcountLessThan1() {
        Taxi taxi = new Taxi(1, "",0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Party_TestHeadcountLessThan1() {
        Party taxi = new Party(0, null, "d");
    }

    @Test
    public void Taxi_TestHeadcountLessThanCapacity() {
        // Same as testing if capacity greater than headcount
        Taxi taxi = new Taxi(2, "", 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Taxi_TestCapacityLessThan1() throws IllegalArgumentException {
        Taxi taxi = new Taxi(0, "",1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Taxi_TestCapacityLessThanHeadcount() throws IllegalArgumentException {
        // Same as testing if headcount greater than capacity
        Taxi taxi = new Taxi(1, "",10, null);
    }

}