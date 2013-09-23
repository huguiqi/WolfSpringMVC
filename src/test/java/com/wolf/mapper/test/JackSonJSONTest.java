package com.wolf.mapper.test;

import com.wolf.model.Account;
import com.wolf.model.GuestDto;
import com.wolf.model.SystemEntity;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JackSonJSONTest {

    @Test
    public void  testUserListToJson(){
        SystemEntity systemEntity = new SystemEntity();

        systemEntity.setId(100L);
        systemEntity.setName("小黑");

        List<Account> accountList = new ArrayList<Account>();
        Account account = new Account();
        account.setId(100L);
        account.setUserName("老黑子");
        account.setPassword("xxxx");
        accountList.add(account);
        accountList.add(account);
        systemEntity.setAccountList(accountList);
        List<GuestDto> guestDtoList = new ArrayList<GuestDto>();
        GuestDto guestDto = new GuestDto();
        guestDto.setId(101L);
        guestDto.setName("xiaohei");
        guestDtoList.add(guestDto);
        guestDtoList.add(guestDto);
        ObjectMapper om = new ObjectMapper();
        try {
            om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String guests = om.writeValueAsString(guestDtoList);
            systemEntity.setGuests(guests);
            String jsonStr = om.writeValueAsString(systemEntity);
            System.out.println(jsonStr);
            System.out.println(systemEntity.getGuests());
            SystemEntity se = om.readValue(jsonStr,SystemEntity.class);
            System.out.println("==="+se.getGuestArray().size()+"===");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
