
/* Created by sunil.nataraj */


import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* Limitation of Tool :
    All values in default dimens must be in dp
    create folders manually : values-sw768dp , values-sw1200dp ,values-sw900dp etc..
    recreates dimens file automatically
 */
public class DimenConverterTool {

    /**
     * You can change your factors here. The current factors are just for the demo
     */
    //# MARKER 1
    private static final Double val_0_64 = new Double(0.64);
    private static final Double val_0_75 = new Double(0.75);
    private static final Double val_0_34 = new Double(0.34);
    private static final Double val_100 = new Double(1.0);


    private static Map<Double, String> fileNameMap = new HashMap();

    private static Map<Double, List<String>> map = new HashMap();

    static String folderPath = "../app/src/main/res/";
    static String filePath = "../app/src/main/res/values-sw1200dp/dimens.xml";

    static String deafultDimen = "../app/src/main/res/values/dimens.xml";

    public static void main(String[] args) throws IOException {

        //# MARKER 2
        ArrayList<String> list_0_64 = new ArrayList();
        ArrayList<String> list_0_75 = new ArrayList();
        ArrayList<String> list_0_34 = new ArrayList();


        //# MARKER 3
       list_0_64.add("<resources>");
       list_0_75.add("<resources>");
       list_0_34.add("<resources>");

        //# MARKER 4
        map.put(val_0_64, list_0_64);
        map.put(val_0_75, list_0_75);
        map.put(val_0_34, list_0_34);




        //# MARKER 5
        fileNameMap.put(val_0_64, "../app/src/main/res/values-sw768dp/dimens.xml");
        fileNameMap.put(val_0_75, "../app/src/main/res/values-sw900dp/dimens.xml");
        fileNameMap.put(val_0_34, "../app/src/main/res/values-sw411dp/dimens.xml");


        createFolderAndFiles();

        Iterator<Entry<Double, List<String>>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<Double, List<String>> entry = entries.next();
            try {
                convertToNewDimens(entry.getKey(), entry.getValue());
            } catch (Exception e) {

                System.out.println(" cannot covert file , the base file does not exist or invalid file names");
                 e.printStackTrace();
            }

        }




    }

    private static void createFolderAndFiles() {
        Iterator<Entry<Double, String>> entries = fileNameMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry<Double, String> entry = entries.next();
            System.out.println(entry.getValue());
            File file = new File(entry.getValue());
            try {
                if(file.getParent()==null) {

                }
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("cannot crate folders in the metioned path , may be permission issue ");
              e.printStackTrace();
            }

        }

    }

    private static void convertToNewDimens(Double factor, List<String> resultList) throws IOException {
        System.out.println("Heloo" + filePath);
        /**
         * In case there is some I/O exception with the file, you can directly copy-paste the full path and assign to varaibale "fullPath" to the file here:
         */
        FileInputStream fstream = null;
        BufferedReader br = null;
        try {
            fstream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(fstream));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strLine;
        while ((strLine = br.readLine()) != null) {
            strLine.trim();

            modifyLine(strLine, factor, resultList);

        }
        br.close();
       resultList.add("</resources>");

        for (String str : resultList) {
            System.out.println(factor + str);
        }
        if (factor.doubleValue() == 0.75) {
            save(fileNameMap.get(val_0_75), resultList);
        } else if (factor.doubleValue() == 0.34) {
            save(fileNameMap.get(val_0_34), resultList);
            //# DEFAULT MARKER
            save(deafultDimen, resultList);
        } else if (factor.doubleValue() == 0.64) {
            save(fileNameMap.get(val_0_64), resultList);
        }
        //# MARKER 6
    }

    public static void save(String fileName, List<String> resultList) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        for (String str : resultList)
            pw.println(str);
        pw.close();
    }

    public static void save(String fileName, String tag) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        pw.println(tag);
        pw.close();
    }

    private static void modifyLine(String line, double factor, List<String> resultList) {
        /**
         * Well, this is how I'm detecting if the line has some dimension value or not.
         */
        if (line.contains("p</")) {
            int endIndex = line.indexOf("p</");
            //since indexOf returns the first instance of the occurring string. And, the actual dimension would follow after the first ">" in the screen
            int begIndex = line.indexOf(">");
            String prefix = line.substring(0, begIndex + 1);
            String root = line.substring(begIndex + 1, endIndex - 1);
            String suffix = line.substring(endIndex - 1, line.length());
            /**
             * Now, we have the root. We can use it to create different dimensions. Root is simply the dimension number.
             */
            double dimens = Double.parseDouble(root);
            dimens = dimens * factor * 1000;
            dimens = (double) ((int) dimens);
            dimens = dimens / 1000;
            root = dimens + "";
            String convertedDimes = prefix + "" + root + "" + suffix;
            // System.out.println(convertedDimes);
            resultList.add(convertedDimes);
            return;

        }

    }
}
