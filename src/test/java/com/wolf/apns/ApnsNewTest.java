package com.wolf.apns;

import com.wolf.bean.Student;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.*;
import javapns.notification.transmission.PushQueue;
import org.json.JSONException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ApnsNewTest {

    private static final String APNS_DEVICE_TOKEN_KEY = "iPhone";

    private static final Logger logger = LoggerFactory
            .getLogger(ApnsNewTest.class);

    private Student student;

//    private static String certificatePath = "/Users/huguiqi/Public/workspace/github/WolfSpringMVC/src/main/resources/com/wolf/apns/Certificates-starHotel-dev.p12";
//    private static String certificatePath = "com/wolf/apns/Certificates-starHotel-dev.p12";
    private static String certificatePath = "com/wolf/apns/Certificates-new-dev.p12";


    private List<Device> buildDeviceAndValidToken() {

        List<Device> deviceList = new ArrayList<Device>();
        for (int i = 0; i < 10; i++) {
            Device device = null;
            boolean isCorrect = true;
            try {
                if (i == 3) {
                    device = new BasicDevice("79c1e433bcfaad97866a26327e5151d8acdc29e29729590f7bdb37dd2e3965ef", true);
                } else {
                    device = new BasicDevice("35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d" + i, true);
                }
            } catch (InvalidDeviceTokenFormatException ie) {
                logger.error("deviceToken format error!! deviceToken", ie);
                isCorrect = false;
            }

            if (isCorrect) {
                deviceList.add(device);
            }

        }
        return deviceList;
    }

//    private List<String> buildDeviceAndValidToken() {
//
//        List<String> deviceList = new ArrayList<String>();
//        for (int i = 0; i < 10; i++) {
//            boolean isCorrect = true;
//            String token = "35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2b" + i;
//            try {
//                if (i == 3) {
//                    token = "android:aaaaa" + i;
//                    BasicDevice.validateTokenFormat(token);
//                } else {
//                    BasicDevice.validateTokenFormat(token);
//                }
//            } catch (InvalidDeviceTokenFormatException ie) {
//                logger.error("deviceToken format error!! deviceToken", ie);
//                isCorrect = false;
//            }
//
//            if (isCorrect) {
//                deviceList.add(token);
//            }
//
//        }
//        return deviceList;
//    }


    private void sendMsgToDevice(List<Device> deviceList, PushNotificationManager pushManager) throws Exception {
        PushNotificationPayload payLoad = new PushNotificationPayload();
        payLoad.addAlert("这只是个测试！！！");
        payLoad.addBadge(1);
        payLoad.addSound("default");
        payLoad.addCustomDictionary("msgType", "msg");
//        PushedNotifications notifications = pushManager.sendNotifications(payLoad, deviceList);
        Device device = new BasicDevice("79c1e433bcfaad97866a26327e5151d8acdc29e29729590f7bdb37dd2e3965ef");
        PushedNotification notification =  pushManager.sendNotification(device,payLoad,true);

//        List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
//        List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
//        int failed = failedNotifications.size();
//        int successful = successfulNotifications.size();
    }

//    @Test
//    public void testMsg() throws CommunicationException, KeystoreException {
//        PushNotificationManager pushManager = null;
//        List<Device> deviceList = buildDeviceAndValidToken();
//        try {
//            pushManager = new PushNotificationManager();
//            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, "123456", true));
//
//            sendMsgToDevice(deviceList, pushManager);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            pushManager.stopConnection();
//        }
//    }

//    @Test
//    public void testSend() {
//        try {
//            send(certificatePath, "123456", false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void send(Object keystore, String password, boolean production) throws InvalidDeviceTokenFormatException, KeystoreException {
//
//        /* Prepare a simple payload to push */
//        PushNotificationPayload payload = PushNotificationPayload.alert("Hello World!");
//
//        /* Decide how many threads you want your queue to use */
//        int threads = 5;
//
//        /* Create the queue */
//        PushQueue queue = Push.queue(keystore, password, production, threads);
//
//        /* Start the queue (all threads and connections and initiated) */
//        queue.start();
//
//        /* Add a notification for the queue to push */
//        List<Device> deviceList = buildDeviceAndValidToken();
//        for (Device device1 : deviceList) {
//            queue.add(payload, device1.getToken());
//        }
//        System.out.println(queue.getPushedNotifications().getFailedNotifications().size());
//    }


    @Test
    public void pushTest() throws Exception {
//        35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d4
//        Push.alert("Hello World!This is test!!", this.getClass().getClassLoader().getResourceAsStream(certificatePath), "123456", false, "35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d4");
        PushNotificationPayload payLoad = new PushNotificationPayload();
        payLoad.addAlert("这只是个测试！！！");
        payLoad.addBadge(1);
        payLoad.addSound("default");
        payLoad.addCustomDictionary("msgType", "msg");
//        Push.payload(payLoad,certificatePath, "123456", false, this.buildDeviceAndValidToken());


        PushedNotifications pushedNotifications = Push.payload(payLoad,this.getClass().getClassLoader().getResourceAsStream(certificatePath), "123456", false, 5,this.buildDeviceAndValidToken());
        System.out.println("success:" + pushedNotifications.getSuccessfulNotifications().getSuccessfulNotifications().size());
        System.out.println("fail:" + pushedNotifications.getFailedNotifications().getFailedNotifications().size());
    }

}
