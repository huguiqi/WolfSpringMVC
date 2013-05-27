package com.wolf.model;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
public class SystemEntity implements Serializable{
    private  Long id;
    private String name;
    private List<Account> accountList;
    private String guests;
    private List<GuestDto> guestArray;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public List<GuestDto> getGuestArray() throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GuestDto[] guestDtos = om.readValue(getGuests(), GuestDto[].class);
        return Arrays.asList(guestDtos);
    }

    public void setGuestArray(List<GuestDto> guestArray) {
        this.guestArray = guestArray;
    }
}
