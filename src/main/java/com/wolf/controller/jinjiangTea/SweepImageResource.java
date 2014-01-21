package com.wolf.controller.jinjiangTea;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/sweep")
public class SweepImageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SweepImageResource.class);

    private static String IMAGE_DIR = "/imgdir/imgdir/download/jjtea";

    private static String JINJIANG__TEA_PATH = "http://192.168.2.51:8888";

    private static int maxLineSize = 4;

    @RequestMapping(value = "/images", method = {RequestMethod.GET, RequestMethod.POST})
    public String getImages(Model model) {
        try {
            Collection<File> fileList = null;
            fileList = FileUtils.listFiles(new File(IMAGE_DIR), new String[]{"JPG", "PNG", "jpg"}, true);
            List<JinJiangTeaImage> listTeaImages = new ArrayList<JinJiangTeaImage>();
            List<String> listPath = new ArrayList<String>();
            for (File file : fileList) {
                listPath = filterGroupImages(listPath, maxLineSize, JINJIANG__TEA_PATH + "/" + file.getName());
                if (listPath.size() >= maxLineSize) {
                    JinJiangTeaImage tempTeaImage = new JinJiangTeaImage();
                    tempTeaImage.setGroupListPath(listPath);
                    listTeaImages.add(tempTeaImage);
                }
            }
            if (listTeaImages.size() < fileList.size()) {
                List<String> diffListPath = new ArrayList<String>();
                JinJiangTeaImage tempTeaImage = new JinJiangTeaImage();
                List<File> tempFiles = Lists.newArrayList(fileList);
                for (int i = (listTeaImages.size() * maxLineSize); i < tempFiles.size(); i++) {
                    diffListPath.add(JINJIANG__TEA_PATH + "/" + tempFiles.get(i).getName());
                }
                tempTeaImage.setGroupListPath(diffListPath);
                listTeaImages.add(tempTeaImage);
            }
            model.addAttribute("listTeaImages", listTeaImages);
            LOGGER.info("-----listGroupSize", listTeaImages.size());
        } catch (Exception e) {
            LOGGER.error("---读取图片出错:{}",JINJIANG__TEA_PATH, e);
        }

        return "jinjiangTea/sweepImages";
    }

    private List<String> filterGroupImages(List<String> imageList, int maxSize, String fullPath) {

        List<String> resultList = new ArrayList<String>();
        if (imageList.size() < maxSize) {
            imageList.add(fullPath);
            resultList = imageList;
        } else {
            resultList.add(fullPath);
        }

        return resultList;
    }

}
