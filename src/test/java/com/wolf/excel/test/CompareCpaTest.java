package com.wolf.excel.test;

import com.wolf.bean.Student;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
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

    private static String cpaPath1 = "/Users/huguiqi/Desktop/cpa_compare/2013-10-(12-13).xlsx";
    private static String cpaPath2 = "/Users/huguiqi/Desktop/cpa_compare/compare_2013_10_12.txt";
    private static String outFilePath = "/Users/huguiqi/Desktop/cpa_compare/compare_result.txt";

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
                        cpaCompareInfo.setDeviceToken(cpastrs[0]);

                        if(cpastrs.length >1)
                            cpaCompareInfo.setMacAddress(cpastrs[1]);
                        if(cpastrs.length > 2){
                            cpaCompareInfo.setVersion(cpastrs[2]);
                        }
                        if(cpastrs.length >3){
                            cpaCompareInfo.setDateStr(cpastrs[3]);
                        }
                        if("2.2.4".equals(cpaCompareInfo.getVersion())&& !"null".equals(cpaCompareInfo.getMacAddress())){
                            cpaCompareInfoList.put(cpaCompareInfo.getMacAddress(), cpaCompareInfo);
                        }
//                    String v = cpaCompareInfo.getVersion().substring(0,3);
//                    System.out.println("-----v:"+v);
//                    if("2.2".equals(v)){
//                        int v2 = Integer.valueOf(cpaCompareInfo.getVersion().substring(4)).intValue() ;
//                        if (v2>=2 && !"null".equals(cpaCompareInfo.getMacAddress())) {
//                            cpaCompareInfoList.put(cpaCompareInfo.getMacAddress(), cpaCompareInfo);
//                        }
//                    }

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
                    if(!"null".equals(cpaCompareInfo.getMacAddress()) && cpaCompareInfo.getMacAddress() !=null && "2".equals(cpaCompareInfo.getVersion().substring(0,1))){
                        cpaCompareInfoList.add(cpaCompareInfo);
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
    @Test
    public void readXLSX2007Test() {
        Map<String,CpaCompareInfo> cpaCompareInfos1 = readFromXLSX2007(cpaPath1);
        Map<String,CpaCompareInfo> cpaCompareInfos2 = compareCpaInfoResult();
//        List<CpaCompareInfo> cpaCompareInfos = compareCpaInfoResultList();
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

//       for(CpaCompareInfo cpaCompareInfo :listCpas){
//           System.out.println("  "+cpaCompareInfo.getDeviceToken()+"  "+cpaCompareInfo.getMacAddress() +"  "+cpaCompareInfo.getDesc() +" "+cpaCompareInfo.getVersion() +"  " + cpaCompareInfo.getDateStr());
//       }
//        System.out.println("aaa nums:"+cpaCompareInfos2.size());
//        for(CpaCompareInfo cpaCompareInfo:cpaCompareInfos2.values()){
//            System.out.println(" device cpa : "+cpaCompareInfo.getDeviceToken()+"  "+cpaCompareInfo.getMacAddress() +"  "+cpaCompareInfo.getDesc() +" "+cpaCompareInfo.getVersion() +"  " + cpaCompareInfo.getDateStr());
//        }

        createTxtForResult(listCpas);

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
                    String macAddress = "";
                    if(1 == cell.getCellType()){
                        macAddress = cell.getStringCellValue().replaceAll(":", "");
                    }else {
                        System.out.println("-----"+j+"------");
                        macAddress = String.valueOf(cell.getNumericCellValue());
                    }
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


    public void createTxtForResult(List<CpaCompareInfo> list){

        FileWriter fw = null;
        try{
            fw = new FileWriter(outFilePath);
            for (CpaCompareInfo cpaCompareInfo :list){
                fw.write("  "+cpaCompareInfo.getDeviceToken()+"  "+cpaCompareInfo.getMacAddress() +"  "+cpaCompareInfo.getDesc() +" "+cpaCompareInfo.getVersion() +"  " + cpaCompareInfo.getDateStr()+"\r\n");
            }
            fw.flush();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createExcelFor2007(List<CpaCompareInfo> list){

        // 创建Excel2007工作簿对象
        XSSFWorkbook workbook2007 = new XSSFWorkbook();
        // 创建工作表对象并命名
        XSSFSheet sheet = workbook2007.createSheet("cpa设备比较结果");
        // 设置行列的默认宽度和高度
        sheet.setColumnWidth(0, 32 * 80);// 对A列设置宽度为80像素
        sheet.setColumnWidth(1, 32 * 80);
        sheet.setColumnWidth(2, 32 * 80);
        sheet.setColumnWidth(3, 32 * 80);
        sheet.setColumnWidth(4, 32 * 80);


        // 创建样式
        XSSFFont font = workbook2007.createFont();
        XSSFCellStyle headerStyle = workbook2007.createCellStyle();
        // 设置垂直居中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置边框
//        headerStyle.setBorderTop(BorderStyle.THIN);
//        headerStyle.setBorderBottom(BorderStyle.THIN);
//        headerStyle.setBorderLeft(BorderStyle.THIN);
//        headerStyle.setBorderRight(BorderStyle.THIN);
        // 字体加粗
//        font.setBold(true);
        // 设置长文本自动换行
//        headerStyle.setWrapText(true)
        headerStyle.setFont(font);

// 创建表头
        XSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(25f);// 设置行高度
        XSSFCell nameHeader = headerRow.createCell(0);
        nameHeader.setCellValue("deviceToken");
        nameHeader.setCellStyle(headerStyle);
        XSSFCell genderHeader = headerRow.createCell(1);
        genderHeader.setCellValue("公网IP");
        genderHeader.setCellStyle(headerStyle);
        XSSFCell ageHeader = headerRow.createCell(2);
        ageHeader.setCellValue("是否存在");
        ageHeader.setCellStyle(headerStyle);
        XSSFCell classHeader = headerRow.createCell(3);
        classHeader.setCellValue("版本号");
        classHeader.setCellStyle(headerStyle);
        XSSFCell scoreHeader = headerRow.createCell(4);
        scoreHeader.setCellValue("时间");
        scoreHeader.setCellStyle(headerStyle);


        for (int i = 0; i < list.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            row.setHeightInPoints(20f);
            CpaCompareInfo compareInfo = list.get(i);
            XSSFCell nameCell = row.createCell(0);
            nameCell.setCellValue(compareInfo.getDeviceToken());
            CellStyle cellStyle = row.getRowStyle();
            nameCell.setCellStyle(cellStyle);
            XSSFCell genderCell = row.createCell(1);
            genderCell.setCellValue(compareInfo.getMacAddress());
            genderCell.setCellStyle(cellStyle);
            XSSFCell ageCell = row.createCell(2);
            ageCell.setCellValue(compareInfo.getDesc());
            ageCell.setCellStyle(cellStyle);
            XSSFCell classCell = row.createCell(3);
            classCell.setCellValue(compareInfo.getVersion());
            classCell.setCellStyle(cellStyle);
            XSSFCell scoreCell = row.createCell(4);
            scoreCell.setCellValue(compareInfo.getDateStr());
            scoreCell.setCellStyle(cellStyle);
        }


        // 生成文件
        File file = new File(outFilePath);
         OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            workbook2007.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }


    }
}
