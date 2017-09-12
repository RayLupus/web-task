package mytools.ijtlparser;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 * TODO Comment of SampleDataParser
 * 
 * @author xiaochuan.luxc
 */
public class ResParser {

    public static Double       DATA_RANGE = 0.1;

    protected JmeterSampleData targetData;
    protected String           sampleName;

    public ResParser(JmeterSampleData targetData) {
        this.targetData = targetData;
        sampleName = targetData.getSampleName();
    }

    public Map<String, Double> runParser() {
        Map<String, Double> resDataChartMap = new LinkedHashMap<String, Double>();

        Map<String, Object> parserResult = targetData.getParserResult();
        Map<Integer, Long> responseTimeMap = targetData.getResponseTimeMap();
        Map<String, BigInteger> cacheDataMap = targetData.getCacheDataMap();

        BigInteger totalCount = cacheDataMap.get("totalCount");
        BigInteger errorTotalCount = cacheDataMap.get("errorTotalCount");
        Integer[] responseTimeArray = responseTimeMap.keySet().toArray(new Integer[0]);
        Arrays.sort(responseTimeArray);
        DefaultCategoryDataset responseTimeDataSet = new DefaultCategoryDataset();
        Integer start = 0;
        Double percent = 0.0;
        Boolean done = true;
        for (int i = 0; i < responseTimeArray.length; i++) {
            Integer responseTimeKey = responseTimeArray[i];
            Double currentPercent = responseTimeMap.get(responseTimeKey).doubleValue() / totalCount.doubleValue();
            if (currentPercent > DATA_RANGE) {
                if (percent > 0) {
                    if (start == responseTimeArray[i - 1]) {
                        resDataChartMap.put(start + "ms", percent);
                    } else {
                        resDataChartMap.put(start + "~" + responseTimeArray[i - 1] + "ms", percent);
                    }
                    percent = 0d;
                    done = true;
                }
                resDataChartMap.put(responseTimeKey + "ms", currentPercent);
                continue;
            }
            if (percent > DATA_RANGE) {
                resDataChartMap.put(start + "~" + responseTimeKey + "ms", percent);
                percent = 0d;
                done = true;
                continue;
            }
            if (done) {
                start = responseTimeKey;
                percent += currentPercent;
                done = false;
            } else {
                percent += currentPercent;
            }
        }
        if (percent > 0) {
            resDataChartMap.put(start + "~" + responseTimeArray[responseTimeArray.length - 1] + "ms", percent);
            percent = 0d;
            done = true;
        }
        for (String key : resDataChartMap.keySet()) {
            responseTimeDataSet.addValue(resDataChartMap.get(key), targetData.getSampleName(), key);
        }
        Double avgResTime = cacheDataMap.get("totalTime").doubleValue() / totalCount.doubleValue();
        Double errorAvgResTime = cacheDataMap.get("errorTotalTime").doubleValue() / errorTotalCount.doubleValue();
        Double squareDev = 0d;
        for (Integer responseTimeKey : responseTimeArray) {
            squareDev += Math.pow(Math.abs(responseTimeKey - avgResTime), 2) * responseTimeMap.get(responseTimeKey);
        }
        Double stdDevRes = Math.sqrt(squareDev / totalCount.doubleValue());
        parserResult.put("minRes", responseTimeArray[0]);
        parserResult.put("maxRes", responseTimeArray[responseTimeArray.length - 1]);
        parserResult.put("avgRes", avgResTime);
        parserResult.put("totalCount", totalCount);
        parserResult.put("errorTotalCount", errorTotalCount);
        parserResult.put("errorPercent", 100 * errorTotalCount.doubleValue() / totalCount.doubleValue());
        parserResult.put("stdDevRes", stdDevRes);
        parserResult.put("errorAvgResTime", errorAvgResTime);
        parserResult.put("resDataChartMap", resDataChartMap);

        return resDataChartMap;
    }
}
