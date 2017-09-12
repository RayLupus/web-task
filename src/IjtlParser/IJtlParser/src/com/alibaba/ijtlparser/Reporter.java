/**
 * Project: IJtlParser File Created at 2010-7-30 $Id$ Copyright 2008 Alibaba.com Croporation Limited. All rights
 * reserved. This software is the confidential and proprietary information of Alibaba Company.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.ijtlparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO Comment of Reporter
 * 
 * @author xiaochuan.luxc
 */
public class Reporter {

    private static DecimalFormat            reportDataFormat = new DecimalFormat("#####0.00");
    protected Map<String, JmeterSampleData> sampleDataMap;

    public Reporter(Map<String, JmeterSampleData> sampleDataMap) {
        this.sampleDataMap = sampleDataMap;
    }

    @SuppressWarnings("unchecked")
    public void reportFile(String reportDir) {
        String cssString = "TABLE.rt_tb TD {\nBORDER-BOTTOM: #e6e6e6 1px solid;\n}\n\nTABLE.rt_tb TD {\nPADDING-RIGHT: 12px;\nPADDING-LEFT: 12px;\nPADDING-BOTTOM: 4px;\nPADDING-TOP: 4px;\nTEXT-ALIGN: left;\n}\n\nTABLE.rt_tb TH {\nPADDING-RIGHT: 12px;\nPADDING-LEFT: 12px;\nPADDING-BOTTOM: 4px;\nPADDING-TOP: 4px;\nTEXT-ALIGN: center;\n}\n\nTABLE.rt_tb CAPTION {\nPADDING-RIGHT: 12px;\nPADDING-LEFT: 12px;\nPADDING-BOTTOM: 4px;\nPADDING-TOP: 4px;\nTEXT-ALIGN: left;\n}\n\nTABLE.rt_tb {\nMARGIN: 0px 0px 8px;\nWIDTH: 800;\nBORDER-BOTTOM: #6b86b3 3px solid;\n}\n\nTABLE.rt_tb TR {\nBACKGROUND: #f1ffff;\nHEIGHT:20px;\n}\n\nTABLE.rt_tb caption{\nFONT-WEIGHT:bold;\nPADDING:6px 0px; \nCOLOR:#3d580b; \nFONT-SIZE:20px;\n}\n\nTABLE.rt_tb TH {\nFONT-WEIGHT: bold;\nBACKGROUND:#e1e1e1;\nBORDER-BOTTOM: #6b86b3 1px solid;\n}\n\nTABLE.rt_tb TD.Header {\nFONT-WEIGHT: bold; \nBORDER-BOTTOM: #6b86b3 1px solid;\nBACKGROUND: #ffffff;\n}\n\nTABLE.rt_tb TR.Header TD {\nFONT-WEIGHT: bold;\nBACKGROUND: #ffffff;\nBORDER-BOTTOM: #6b86b3 1px solid;\n}\n\nTABLE.rt_tb TR.Alt {\nBACKGROUND: #ffffff;\n}\n\nTABLE.tps_tb TD {\nBORDER-BOTTOM: #e6e6e6 1px solid;\n}\n\nTABLE.tps_tb TD {\nPADDING-RIGHT: 12px;\nPADDING-LEFT: 12px;\nPADDING-BOTTOM: 4px;\nPADDING-TOP: 4px;\nTEXT-ALIGN: left\n}\n\nTABLE.tps_tb TH {\nPADDING-RIGHT: 12px;\nPADDING-LEFT: 12px;\nPADDING-BOTTOM: 4px;\nPADDING-TOP: 4px;\nTEXT-ALIGN: center\n}\n\nTABLE.tps_tb CAPTION {\nPADDING-RIGHT: 12px;\nPADDING-LEFT: 12px;\nPADDING-BOTTOM: 4px;\nPADDING-TOP: 4px;\nTEXT-ALIGN: left;\n}\n\nTABLE.tps_tb {\nMARGIN: 0px 0px 8px;\nWIDTH: 800;\nBORDER-BOTTOM: #6b86b3 3px solid;\n}\n\nTABLE.tps_tb TR {\nBACKGROUND: #fff3ff;\nHEIGHT:20px;\n}\n\nTABLE.tps_tb caption{\nFONT-WEIGHT:bold;\nPADDING:6px 0px; \nCOLOR:#3d580b; \nFONT-SIZE:20px;\n}\n\nTABLE.tps_tb TH {\nFONT-WEIGHT: bold;\nBACKGROUND:#e1e1e1;\nBORDER-BOTTOM: #6b86b3 1px solid;\n}\n\nTABLE.tps_tb TD.Header {\nFONT-WEIGHT: bold; \nBORDER-BOTTOM: #6b86b3 1px solid;\nBACKGROUND: #ffffff;\n}\n\nTABLE.tps_tb TR.Header TD {\nFONT-WEIGHT: bold;\nBACKGROUND: #ffffff;\nBORDER-BOTTOM: #6b86b3 1px solid;\n}\n\nTABLE.tps_tb TR.Alt {\nBACKGROUND: #ffffff;\n}";
        StringBuilder reportString = new StringBuilder("");
        reportString.append("<link id='MainCss' type='text/css' rel='stylesheet' href='jtltc' />Response Time(ms)<table id='rt' class='rt_tb' ><thead><th scope='col'>ACTION NAME</th><th scope='col'>Minimum</th><th scope='col'>Maximum</th><th scope='col'>Average</th><th scope='col'>Std.Dev</th></thead><tbody id='tbody'>");
        for (String sampleName : sampleDataMap.keySet()) {
            Map<String, Object> parserResult = sampleDataMap.get(sampleName).getParserResult();
            reportString.append("<tr><td>");
            reportString.append(sampleName);
            reportString.append("</td><td>");
            reportString.append(parserResult.get("minRes"));
            reportString.append("</td><td>");
            reportString.append(parserResult.get("maxRes"));
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("avgRes")));
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("stdDevRes")));
            reportString.append("</td></tr>");
        }
        reportString.append("</tbody></table><p>Transaction per Second<table id='tps' class='tps_tb' ><thead><th scope='col'>ACTION NAME</th><th scope='col'>Minimum</th><th scope='col'>Maximum</th><th scope='col'>Average</th><th scope='col'>Std.Dev</th></thead><tbody id='tbody'>");
        for (String sampleName : sampleDataMap.keySet()) {
            Map<String, Object> parserResult = sampleDataMap.get(sampleName).getParserResult();
            reportString.append("<tr><td>");
            reportString.append(sampleName);
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("minTps")));
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("maxTps")));
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("avgTps")));
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("stdDevTps")));
            reportString.append("</td></tr>");
        }
        reportString.append("</tbody></table><p>Request Count<table id='tps' class='tps_tb' ><thead><th scope='col'>ACTION NAME</th><th scope='col'>TotalCount</th><th scope='col'>ErrorCount</th><th scope='col'>ErrorPercent(%)</th><th scope='col'>ErrorAvgResTime</th></thead><tbody id='tbody'>");
        for (String sampleName : sampleDataMap.keySet()) {
            Map<String, Object> parserResult = sampleDataMap.get(sampleName).getParserResult();
            reportString.append("<tr><td>");
            reportString.append(sampleName);
            reportString.append("</td><td>");
            reportString.append(parserResult.get("totalCount"));
            reportString.append("</td><td>");
            reportString.append(parserResult.get("errorTotalCount"));
            reportString.append("</td><td>");
            reportString.append(reportDataFormat.format(parserResult.get("errorPercent")));
            reportString.append("</td><td>");
            Double errAvg = (Double) parserResult.get("errorAvgResTime");
            if (errAvg > 0) {
                reportString.append(reportDataFormat.format(errAvg));
            } else {
                reportString.append(parserResult.get("errorAvgResTime"));
            }
            reportString.append("</td></tr>");
        }

        reportString.append("</tbody></table><p>Response Time Distribution:");
        for (String sampleName : sampleDataMap.keySet()) {
            Map<String, Object> parserResult = sampleDataMap.get(sampleName).getParserResult();
            reportString.append("<table id='tps' class='tps_tb' ><thead><th scope='col'>ACTION NAME</th>");
            LinkedHashMap<String, Double> lhm = (LinkedHashMap<String, Double>) parserResult.get("resDataChartMap");
            for (String key : lhm.keySet()) {
                reportString.append("<th scope='col'>" + key + "</th>");
            }
            reportString.append("</thead><tbody id='tbody'><tr>");
            reportString.append("<td>" + sampleName + "</td>");
            for (String key : lhm.keySet()) {
                reportString.append("<td>" + reportDataFormat.format((lhm.get(key) * 100)) + "%</td>");
            }
            reportString.append("</td></tr>");
        }
        reportString.append("</tbody></table>");
        for (String sampleName : sampleDataMap.keySet()) {
            reportString.append("<img src='" + ParserUtils.toPicName(sampleName) + "_res.png' /><img src='");
            reportString.append(ParserUtils.toPicName(sampleName) + "_tps.png' />");
        }
        reportString.append("<hr/><br/>by icbu.qa.team");
        File fileReport = new File(reportDir + "report.html");
        try {
            PrintWriter out = new PrintWriter(
                                              new BufferedWriter(
                                                                 new OutputStreamWriter(
                                                                                        new FileOutputStream(fileReport))));
            out.print(reportString);
            out.close();
        } catch (IOException e) {
            System.out.println(fileReport.getAbsolutePath() + " is not available");
            e.printStackTrace();
        }
        File cssFile = new File(reportDir + "jtltc");
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cssFile))));
            out.print(cssString);
            out.close();
        } catch (IOException e) {
            System.out.println(cssFile.getAbsolutePath() + " is not available");
            e.printStackTrace();
        }
        Runtime run = Runtime.getRuntime();
        try {
            run.exec("tar cvf " + reportDir.substring(0, reportDir.length() - 1) + ".tar.gz " + reportDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
