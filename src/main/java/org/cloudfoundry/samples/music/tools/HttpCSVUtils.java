package org.cloudfoundry.samples.music.tools;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by shijian on 23/02/2017.
 */
public class HttpCSVUtils {

    private String fileString;
    private String[] rows;

    public HttpCSVUtils(MultipartFile multipartcsv) {
        try {
            byte[] bytes = multipartcsv.getBytes();
            fileString = new String(bytes);
            rows = fileString.split("\n");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public String[] getRows() {
        return rows;
    }

    public String[] getLabels() {
        String[] rows = getRows();
        String[] columns = rows[0].trim().split(",");
        return columns;
    }

    /**
     * return -1 if there is no such label
     * @param label
     * @return
     */
    public int labelIndex(String label) {
        String[] labels = getLabels();
        for(int i=0; i<labels.length; i++) {
            if(label.trim().toLowerCase().equals(labels[i].trim().toLowerCase()))
                return i;
        }
        return -1;
    }

    public String[] getSingleRowData(Integer index){
        String[] rows = getRows();
        String[] res = rows[index].trim().split(",");
        return res;
    }

}
