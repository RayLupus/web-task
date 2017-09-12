package mytools.ijtlparser;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;

/**
 * TODO Comment of TpsParser
 * 
 * @author xiaochuan.luxc
 */
public class TpsParser {

    public static Long         TPS_PERIOD = 1l;

    protected JmeterSampleData targetData;
    protected String           sampleName;

    public TpsParser(JmeterSampleData targetData) {
        this.targetData = targetData;
        sampleName = targetData.getSampleName();
    }

    public Long[] runParser() {
        Map<String, Object> parserResult = targetData.getParserResult();
        Map<Long, Double> tpsMap = targetData.getTpsMap();
        Map<String, BigInteger> cacheDataMap = targetData.getCacheDataMap();
        Double lastData = cacheDataMap.get("currentTimestamp").doubleValue() - cacheDataMap.get("lastTimestamp").doubleValue();
        if (!cacheDataMap.get("currentCount").equals(BigInteger.ZERO)) {
            if (lastData > 0) {
                Double tps = (cacheDataMap.get("currentCount").doubleValue() / lastData) * 1000;
                tpsMap.put(cacheDataMap.get("currentTimestamp").longValue(), tps);
            } else {
                cacheDataMap.put("totalCount", cacheDataMap.get("totalCount").add(BigInteger.valueOf(cacheDataMap.get("currentCount").longValue())));
            }
        }
        Long[] timestampArray = tpsMap.keySet().toArray(new Long[0]);
        Double[] tpsArray = tpsMap.values().toArray(new Double[0]);
        Arrays.sort(timestampArray);
        Arrays.sort(tpsArray);
        Double jtlTime = (timestampArray[timestampArray.length - 1].doubleValue() - timestampArray[0].doubleValue()) / 1000;
        Double avgTps = cacheDataMap.get("totalCount").doubleValue() / jtlTime;
        Double squareDev = 0d;
        for (Double tps : tpsArray) {
            squareDev += Math.pow(Math.abs(tps - avgTps), 2);
        }
        Double stdDevTps = Math.sqrt(squareDev / tpsArray.length);
        parserResult.put("minTps", tpsArray[0]);
        parserResult.put("maxTps", tpsArray[tpsArray.length - 1]);
        parserResult.put("avgTps", avgTps);
        parserResult.put("stdDevTps", stdDevTps);
        return timestampArray;
    }
}
