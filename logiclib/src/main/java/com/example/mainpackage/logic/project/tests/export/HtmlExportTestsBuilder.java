package com.example.mainpackage.logic.project.tests.export;

import com.example.mainpackage.logic.project.tests.Combination;
import com.example.mainpackage.logic.project.tests.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HtmlExportTestsBuilder extends ExportTestBuilder{

    public boolean export() {
        try {
            if (this.filePath != null && this.project != null) {
                List<String> content = new ArrayList();
                content.add("<!DOCTYPE html><html>");
                content.add("<head>");
                content.add("<title>" + this.project.getName() + " </title>");
                content.add("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css'>\n  <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>\n  <script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js'></script>");
                content.add("</head>");
                content.add("<body>");
                content.add("<div class='container'>");
                content.add("<h3>Running Test...</h3>");
                content.add("<div class='panel panel-default'>");
                content.add("<div class='panel-heading'>Project Name: " + this.project.getName() + "</div>");
                content.add("<div class='panel-body'>");
                Iterator var2 = this.project.getTests().iterator();

                while(var2.hasNext()) {
                    Test test = (Test)var2.next();
                    content.add("<b>Input Signal:</b> " + printStringCombinationHtml(test.getSignalInput().getCombinations()) + "</br>");
                    content.add("<b>Expected Signal:</b> " + printStringCombinationHtml(test.getSignalExpected().getCombinations()) + " </br>");
                    content.add("<b>Result:</b> " + this.project.runTest(test) + "</br>");
                }

                content.add("</div>");
                content.add("</div>");
                content.add("</div>");
                content.add("</body></html>");
                String strContent = "";

                String str;
                for(Iterator var8 = content.iterator(); var8.hasNext(); strContent = strContent + str + "\n") {
                    str = (String)var8.next();
                }

                String path = this.filePath + "/" + this.project.getName() + ".html";
                File file = new File(path);
                file.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(strContent.getBytes());
                fos.close();
                return true;
            } else {
                return false;
            }
        } catch (IOException var6) {
            Logger.getLogger(com.example.mainpackage.logic.project.filemanagement.File.class.getName()).log(Level.SEVERE, (String)null, var6);
            return false;
        }
    }

    private static String printStringCombinationHtml(List<Combination> combinations) {
        String str = "";
        Iterator var2 = combinations.iterator();

        while(var2.hasNext()) {
            Combination combination = (Combination)var2.next();
            str = str + "<br>Combination:<br>";

            Map.Entry pair;
            for(Iterator it = combination.getValues().entrySet().iterator(); it.hasNext(); str = str + " " + pair.getKey() + " - " + pair.getValue() + "<br>") {
                pair = (Map.Entry)it.next();
            }
        }

        return str;
    }
}
