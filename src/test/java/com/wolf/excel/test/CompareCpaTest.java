package com.wolf.excel.test;

import com.wolf.bean.Student;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sam
 * Date: 13-5-26
 * Time: 下午10:54
 * To change this template use File | Settings | File Templates.
 */
public class CompareCpaTest {

    private static String cpaPath1 = "/Users/sam/Desktop/锦江旅行家mac list 20130524-20130526.xlsx";
    private static String cpaPath2 = "/Users/sam/Desktop/2013-05-24--26副本.txt";

    private Map<String,CpaCompareInfo> compareCpaInfoResult(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        Map<String,CpaCompareInfo> cpaCompareInfoList = null;
        try{
         fileReader = new FileReader(new File(cpaPath2));
          bufferedReader = new BufferedReader(fileReader);
            String lineStr = null;
            cpaCompareInfoList = new HashMap<String, CpaCompareInfo>();
            int i = 0;
            while ((lineStr = bufferedReader.readLine()) != null){
                String[] cpastrs = lineStr.split("\\|\\|");
                CpaCompareInfo cpaCompareInfo = new CpaCompareInfo();
                if(cpastrs.length > 0){
                        i++;
                        cpaCompareInfo.setMacAddress(cpastrs[0]);
                        if(cpastrs.length >1)
                        cpaCompareInfo.setVersion(cpastrs[1]);
                        if(cpastrs.length > 2){
                            cpaCompareInfo.setDateStr(cpastrs[2]);
                        }
                        if("2".equals(cpaCompareInfo.getVersion().substring(0,1)) && !"null".equals(cpaCompareInfo.getMacAddress())){
                            cpaCompareInfoList.put(cpaCompareInfo.getMacAddress(), cpaCompareInfo);
                        }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                bufferedReader.close();
                fileReader.close();
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }
        return cpaCompareInfoList;

    }


    private List<CpaCompareInfo> compareCpaInfoResultList(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        List<CpaCompareInfo> cpaCompareInfoList = null;
        try{
            fileReader = new FileReader(new File(cpaPath2));
            bufferedReader = new BufferedReader(fileReader);
            String lineStr = null;
            cpaCompareInfoList = new ArrayList<CpaCompareInfo>();
            int i = 0;
            while ((lineStr = bufferedReader.readLine()) != null){
                String[] cpastrs = lineStr.split("\\|\\|");

                CpaCompareInfo cpaCompareInfo = new CpaCompareInfo();
                if(cpastrs.length > 0){
                    i++;
                    cpaCompareInfo.setMacAddress(cpastrs[0]);
                    if(cpastrs.length >1)
                        cpaCompareInfo.setVersion(cpastrs[1]);
                    if(cpastrs.length > 2){
                        cpaCompareInfo.setDateStr(cpastrs[2]);
                    }
//                    if("2".equals(cpaCompareInfo.getVersion().substring(0,1))){
//                        cpaCompareInfoList.put(cpaCompareInfo.getMacAddress(),cpaCompareInfo);
//                    }
                    if(!"null".equals(cpaCompareInfo.getMacAddress()) && cpaCompareInfo.getMacAddress() !=null){
                        cpaCompareInfoList.add(cpaCompareInfo);
                    }
                }
            }
            System.out.println("---"+ i);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                bufferedReader.close();
                fileReader.close();
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }
        return cpaCompareInfoList;

    }
    @Test
    public void readXLSX2007Test() {
        Map<String,CpaCompareInfo> cpaCompareInfos1 = readFromXLSX2007(cpaPath1);
        Map<String,CpaCompareInfo> cpaCompareInfos2 = compareCpaInfoResult();
        List<CpaCompareInfo> listCpas = new ArrayList<CpaCompareInfo>();

        for(CpaCompareInfo cpaCompareInfo : cpaCompareInfos1.values()){
            if(cpaCompareInfos2.containsKey(cpaCompareInfo.getMacAddress())){
                CpaCompareInfo  cpaCompareInfo1 = cpaCompareInfos2.get(cpaCompareInfo.getMacAddress());
                cpaCompareInfo1.setDesc("存在");
                listCpas.add(cpaCompareInfo1);
                continue;
            }else {
                cpaCompareInfo.setDesc("不存在");
            }
            listCpas.add(cpaCompareInfo);
        }

       for(CpaCompareInfo cpaCompareInfo :listCpas){
           System.out.println("  "+cpaCompareInfo.getMacAddress() +"  "+cpaCompareInfo.getDesc() +" "+cpaCompareInfo.getVersion() +"  " + cpaCompareInfo.getDateStr());
       }

    }



    private Map<String,CpaCompareInfo> readFromXLSX2007(String filePath) {
        File excelFile = null;// Excel文件对象
        InputStream is = null;// 输入流对象
        Map<String,CpaCompareInfo> compareInfos = new HashMap<String, CpaCompareInfo>();// 返回封装数据的List
        try {
            excelFile = new File(filePath);
            is = new FileInputStream(excelFile);// 获取文件输入流
            XSSFWorkbook workbook2007 = new XSSFWorkbook(is);// 创建Excel2003文件对象
            int sheets =workbook2007.getNumberOfSheets();
            for(int j=0;j<sheets;j++){
                XSSFSheet sheet = workbook2007.getSheetAt(j);// 取出第一个工作表，索引是0
                // 开始循环遍历行，表头不处理，从1开始
                System.out.println("lastRowNum:"+sheet.getLastRowNum());
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    CpaCompareInfo cpaCompareInfo = new CpaCompareInfo();
                    XSSFRow row = sheet.getRow(i);// 获取行对象
                    if (row == null) {// 如果为空，不处理
                        continue;
                    }
                    XSSFCell cell = row.getCell(0);
                    String macAddress = cell.getStringCellValue().replaceAll(":", "");
                    cpaCompareInfo.setMacAddress(macAddress);
                    compareInfos.put(macAddress,cpaCompareInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return compareInfos;
    }
}
