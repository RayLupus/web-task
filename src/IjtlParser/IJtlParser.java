/**
 * Project: IJtlParser File Created at 2010-7-24 $Id$ Copyright 2008 Alibaba.com Croporation Limited. All rights
 * reserved. This software is the confidential and proprietary information of Alibaba Company.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.ijtlparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Jmeter JTL 解析工具
 * 
 * @author xiaochuan.luxc
 */
public class IJtlParser {

    private static SimpleDateFormat             dateFormat    = new SimpleDateFormat("HHmmss");
    private static Set<String>                  exceptSet     = new HashSet<String>();
    // sample map
    public static Map<String, JmeterSampleData> sampleDataMap = new LinkedHashMap<String, JmeterSampleData>();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("params introduction:\n1st:jtl file path(required)\n2nd:tps calculate time(optional)[default:"
                               + TpsParser.TPS_PERIOD
                               + "(s)]\n3rd:distributed ratio range,0-1(optional)[default:"
                               + ResParser.DATA_RANGE
                               + "]\n4th:res pic width(optional)[default:"
                               + ParserUtils.RES_PIC_WIDTH
                               + "]\n5th:res pic height(optional)[default:"
                               + ParserUtils.RES_PIC_HEIGHT
                               + "]\n6th:tps pic width(optional)[default:"
                               + ParserUtils.TPS_PIC_WIDTH
                               + "]\n7th:tps pic height(optional)[default:"
                               + ParserUtils.TPS_PIC_HEIGHT + "]");
            System.exit(0);
        }
        analyseArgs(args);
        exceptSampleInit();
        String filepath = args[0];
        String reportDir = filepath + ".report_" + dateFormat.format(new Date(System.currentTimeMillis())) + "/";
        File report = new File(reportDir);
        report.mkdir();
        long startTime = System.currentTimeMillis();
        File file = new File(filepath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        Double tps = null;
        Map<Integer, Long> responseTimeMap = null;
        Map<String, BigInteger> cacheDataMap = null;
        Map<Long, Double> tpsMap = null;
        // 数据搜集
        while ((line = reader.readLine()) != null) {
            String res = getProperty(line, " t");
            String ts = getProperty(line, " ts");
            String suc = getProperty(line, " s");
            String lb = getProperty(line, " lb");
            if (res != null && ts != null && suc != null && lb != null) {
                boolean ignore = false;
                for (String sampleName : exceptSet) {
                    if (lb.equalsIgnoreCase(sampleName)) {
                        ignore = true;
                        break;
                    }
                }
                if (!ignore) {
                    if (!sampleDataMap.containsKey(lb)) {
                        sampleDataMap.put(lb, new JmeterSampleData(lb, ts));
                    }
                    responseTimeMap = sampleDataMap.get(lb).getResponseTimeMap();
                    tpsMap = sampleDataMap.get(lb).getTpsMap();
                    cacheDataMap = sampleDataMap.get(lb).getCacheDataMap();
                    // 搜集响应时间
                    Integer responseTime = Integer.parseInt(res);
                    try {
                        responseTimeMap.put(responseTime, responseTimeMap.get(responseTime) + 1);
                    } catch (Throwable t) {
                        responseTimeMap.put(responseTime, 1l);
                    }
                    // 搜集错误请求数
                    if (!"true".equalsIgnoreCase(suc)) {
                        cacheDataMap.put("errorTotalCount", cacheDataMap.get("errorTotalCount").add(BigInteger.ONE));
                        cacheDataMap.put("errorTotalTime",
                                         cacheDataMap.get("errorTotalTime").add(BigInteger.valueOf(responseTime)));
                    }
                    // 搜集其他统计信息
                    cacheDataMap.put("totalTime", cacheDataMap.get("totalTime").add(BigInteger.valueOf(responseTime)));
                    cacheDataMap.put("totalCount", cacheDataMap.get("totalCount").add(BigInteger.ONE));
                    cacheDataMap.put("currentCount", cacheDataMap.get("currentCount").add(BigInteger.ONE));
                    cacheDataMap.put("currentTimestamp", BigInteger.valueOf(Long.valueOf(ts)));
                    Double period = cacheDataMap.get("currentTimestamp").doubleValue()
                                    - cacheDataMap.get("lastTimestamp").doubleValue();
                    // TPS统计
                    if (period > TpsParser.TPS_PERIOD * 1000) {
                        tps = (cacheDataMap.get("currentCount").doubleValue() / period) * 1000;
                        tpsMap.put(cacheDataMap.get("lastTimestamp").longValue(), tps);
                        cacheDataMap.put("currentCount", BigInteger.ZERO);
                        cacheDataMap.put("lastTimestamp", BigInteger.valueOf(Long.valueOf(ts)));
                    }
                }
            }
        }
        // 对请求进行解析
        for (String sampleName : sampleDataMap.keySet()) {
            // 进行响应时间统计
            ResParser resParser = new ResParser(sampleDataMap.get(sampleName));
            Map<String, Double> resDataChartMap = resParser.runParser();

            // 进行绘图
            DefaultCategoryDataset responseTimeDataSet = new DefaultCategoryDataset();
            for (String key : resDataChartMap.keySet()) {
                responseTimeDataSet.addValue(resDataChartMap.get(key), sampleName, key);
            }
            ParserUtils.dataBarChart(sampleName, "ResponseTime(avg: "
                                                 + sampleDataMap.get(sampleName).getParserResult().get("avgRes")
                                                 + " ms)", "Ratio", responseTimeDataSet,
                                     reportDir + ParserUtils.toPicName(sampleName) + "_res.png");

            // 响应时间的曲线图
            /* ================================================================ */
            // responseTimeMap = sampleDataMap.get(sampleName).getResponseTimeMap();
            // Integer[] responseTimeArray = responseTimeMap.keySet().toArray(new Integer[0]);
            // Arrays.sort(responseTimeArray);
            // BigInteger totalCount = sampleDataMap.get(sampleName).getCacheDataMap().get("totalCount");
            // XYSeries resXySeries = new XYSeries("TPS");
            // for (Integer responseTime : responseTimeMap.keySet()) {
            // double radio = responseTimeMap.get(responseTime).doubleValue() / totalCount.doubleValue();
            // if (radio > 0.001) {
            // resXySeries.add(responseTime.doubleValue(), radio);
            // }
            // }
            // XYSeriesCollection resDataset = new XYSeriesCollection();
            // resDataset.addSeries(resXySeries);
            // ParserUtils.doXYLineChart(sampleName, "ResponseTime", "Ratio", resDataset,
            // reportDir + ParserUtils.toPicName(sampleName) + "_resxy.png");
            /* ================================================================ */

            // 进行TPS统计
            TpsParser tpsParser = new TpsParser(sampleDataMap.get(sampleName));
            Long[] timestampArray = tpsParser.runParser();

            // 进行绘图
            tpsMap = sampleDataMap.get(sampleName).getTpsMap();
            XYSeries tpsXySeries = new XYSeries("TPS");
            for (Long time : timestampArray) {
                tpsXySeries.add(time, tpsMap.get(time));
            }
            XYSeriesCollection tpsDataset = new XYSeriesCollection();
            tpsDataset.addSeries(tpsXySeries);
            ParserUtils.doXYLineTimeChart(sampleName, "Time", "TPS", tpsDataset, reportDir
                                                                                 + ParserUtils.toPicName(sampleName)
                                                                                 + "_tps.png");
        }
        long endTime = System.currentTimeMillis();
        // 进行报告
        Reporter reporter = new Reporter(sampleDataMap);
        reporter.reportFile(reportDir);
        System.out.println("Time-Consuming : " + ((endTime - startTime) / 1000d) + "s");
    }

    /**
     * 
     */
    private static void exceptSampleInit() {
        File file = new File("except");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                exceptSet.add(line);
            }
        } catch (Exception e) {
        }
    }

    private static void analyseArgs(String[] args) {
        try {
            TpsParser.TPS_PERIOD = Long.valueOf(args[1]);
        } catch (Exception e) {
        }
        try {
            ResParser.DATA_RANGE = Double.valueOf(args[2]);
        } catch (Exception e) {
        }
        try {
            ParserUtils.RES_PIC_WIDTH = Integer.valueOf(args[3]);
        } catch (Exception e) {
        }
        try {
            ParserUtils.RES_PIC_HEIGHT = Integer.valueOf(args[4]);
        } catch (Exception e) {
        }
        try {
            ParserUtils.TPS_PIC_HEIGHT = Integer.valueOf(args[5]);
        } catch (Exception e) {
        }
        try {
            ParserUtils.TPS_PIC_HEIGHT = Integer.valueOf(args[6]);
        } catch (Exception e) {
        }
    }

    public static String getProperty(String line, String property) {
        if (line == null || property == null) return null;
        String value = "";
        if (!property.startsWith(" ")) {
            property = " " + property;
        }
        int plength = property.length();
        int index = line.indexOf(property + "=\"");
        int sindex = index + 2 + plength;
        int eindex = line.indexOf('\"', sindex);
        if (index < 0 || eindex < sindex) return null;
        value = line.substring(sindex, eindex);
        return value;
    }
}
