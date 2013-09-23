//package com.wolf.apns;
//
//import com.wolf.bean.Student;
//import javapns.back.PushNotificationManager;
//import javapns.back.SSLConnectionHelper;
//import javapns.data.Device;
//import javapns.data.PayLoad;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.naming.CommunicationException;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ApnsTest {
//
//    private static final String APNS_DEVICE_TOKEN_KEY = "iPhone";
//
//    private static final Logger logger = LoggerFactory
//            .getLogger(ApnsTest.class);
//
//    private Student student;
//
//    private static String certificatePath = "/Users/huguiqi/Public/workspace/github/WolfSpringMVC/src/main/resources/com/wolf/apns/Certificates-starHotel-dev.p12";
//
//
////    private List<Device> buildDeviceAndValidToken(){
////
////        List<Device> deviceList = new ArrayList<Device>();
////        for (int i = 0; i < 10; i++) {
////            Device device = null;
////            boolean isCorrect = true;
////            try{
////                device = new BasicDevice("35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d" + i,true);
////            }catch (InvalidDeviceTokenFormatException ie){
////                logger.error("deviceToken format error!! deviceToken:"+device.getToken(),ie);
////                isCorrect = false;
////                continue;
////            }finally {
////                if(isCorrect){
////                    deviceList.add(device);
////                }
////            }
////        }
////        return deviceList;
////    }
//
//    private List<String> buildDeviceAndValidToken(){
//
//        List<String> deviceList = new ArrayList<String>();
//        deviceList.add("an:xxxddddeeree");
//        for (int i = 0; i < 10; i++) {
//            String deviceToken = "35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d"+i;
//            deviceList.add(deviceToken);
//        }
////        String deviceToken = "35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d4";
////        deviceList.add(deviceToken);
//        return deviceList;
//    }
//    @Test
//    public void testSendMsg() throws IOException, CommunicationException {
//
//        PushNotificationManager pushManager = null;
//        List<String> deviceList = buildDeviceAndValidToken();
//
//        int failCount = 0;
//        try {
//            pushManager = PushNotificationManager.getInstance();
//            pushManager.initializeConnection("gateway.sandbox.push.apple.com", Integer.parseInt("2195"),
//                    certificatePath, "123456",
//                    SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//            for (int i = 0; i < deviceList.size(); i++) {
//                try {
//                    sendMsgToDevice(deviceList.get(i), pushManager, i);
//                    if(i==5){
//                        logger.info("aaaaaaaaa");
//                    }
//                } catch (Exception e) {
//                    logger.error(String.format("foreach push message to device: %s fail", deviceList.get(i)),e);
//                    pushManager.initializeConnection("gateway.sandbox.push.apple.com", Integer.parseInt("2195"),
//                            certificatePath, "123456",
//                            SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//                    failCount++;
//                } finally {
//                    pushManager.removeDevice(APNS_DEVICE_TOKEN_KEY + i);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("" +
//                    "sendMessage({})----push message invoke fail", e);
//        } finally {
//            if (pushManager != null) {
//                pushManager.stopConnection();
//            }
//        }
//    }
//
////    private void sendMsgToDevice(String deviceToken, PushNotificationManager pushManager, int i) throws Exception {
////        PayLoad payLoad = new PayLoad();
////        payLoad.addAlert("这只是个测试！！！");
////        payLoad.addBadge(1);
////        payLoad.addSound("default");
////        payLoad.addCustomDictionary("msgType", "msg");
////
////        pushManager.addDevice(APNS_DEVICE_TOKEN_KEY + i, deviceToken);
////
////        Device client = pushManager.getDevice(APNS_DEVICE_TOKEN_KEY + i);
////        pushManager.sendNotification(client, payLoad);
////
////    }
//
////    private void sendMsgToDevice(List<Device> deviceList, PushNotificationManager pushManager) throws Exception {
////        PayLoad payLoad = new Payload();
////        payLoad.addAlert("这只是个测试！！！");
////        payLoad.addBadge(1);
////        payLoad.addSound("default");
////        payLoad.addCustomDictionary("msgType", "msg");
//////        pushManager.sendNotifications(payLoad,deviceList);
////        Device device = new BasicDevice("35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d4",true);
////        pushManager.sendNotification(device,payLoad);
////    }
//
//    private void sendMsgToDevice(String deviceToken,
//                                 PushNotificationManager pushManager, int i) throws Exception {
//
//        PayLoad payLoad = new PayLoad();
//        payLoad.addAlert("小黑开车2");
//        payLoad.addBadge(1);
//        payLoad.addSound("default");
//        payLoad.addCustomDictionary("msgType", "msg");
////        if (StringUtils.equals(ApnsMsgType.URL.name(), dto.getMsgType())) {
////            payLoad.addCustomDictionary("url", dto.getUrl());
////        }
//        pushManager.addDevice(APNS_DEVICE_TOKEN_KEY + i, deviceToken);
//
//        Device client = pushManager.getDevice(APNS_DEVICE_TOKEN_KEY + i);
//        pushManager.sendNotification(client, payLoad);
//
//    }
//
//
//    private InputStream getResourcePath(String path) throws FileNotFoundException {
//        return new FileInputStream(new File(path));
//    }
//
//    private Student getStudentInstance(){
//        if(this.student == null){
//            this.student = new Student();
//        }
//        return this.student;
//
//    }
//
////    @Test
////    public void pushTest() throws CommunicationException, KeystoreException {
////        Push.alert("Hello World!This is test!!", certificatePath, "123456", false, "35c81f1c9de63aa5c55d50022542e737bc54e68a9ded01264f46d2e4dc9db2d4");
////    }
//
//
//    /**
//     * 向单个iphone手机推送消息.
//     * @param deviceToken iphone手机获取的token
//     * @param p12File .p12格式的文件路径
//     * @param p12Pass .p12格式的文件密码
//     * @param customDictionarys CustomDictionary字典组
//     * @param content 推送内容
//     */
//    public static void push2One(String p12File, String p12Pass, String deviceToken, String content) throws IOException {
//
//        PushNotificationManager pushManager = PushNotificationManager.getInstance();
//        try {
//            PayLoad payLoad = new PayLoad();
//            payLoad.addAlert(content);//push的内容
//            payLoad.addBadge(1);//应用图标上小红圈上的数值
//            payLoad.addSound("default");//铃音
//
//            //添加字典
//            payLoad.addCustomDictionary("url", "www.baidu.com");
//            pushManager.addDevice("iphone", deviceToken);
//
//            //链接到APNs
//            pushManager.initializeConnection("gateway.sandbox.push.apple.com", Integer.parseInt("2195"), p12File, p12Pass, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
//
//            //开始推送
//            Device client = pushManager.getDevice("iphone");
//            pushManager.sendNotification(client, payLoad);
//
//            pushManager.removeDevice("iphone");
//            logger.info("iphone 推送消息成功");
//        } catch (Exception e) {
//            //   System.out.println("iphone 推送消息异常：" + e.getMessage());
//            logger.error("iphone 推送消息异常：" + e.getMessage());
//        }finally {
//            //断开链接
//            pushManager.stopConnection();
//        }
//    }
//
//    @Test
//    public void testMsg(){
//        for(String deviceToken:buildDeviceAndValidToken()){
//            try {
//                push2One(certificatePath,"123456",deviceToken,"收到没");
//            } catch (IOException e) {
//                logger.error("-----error!!---",e);
//            }
//        }
//
//    }
//}
