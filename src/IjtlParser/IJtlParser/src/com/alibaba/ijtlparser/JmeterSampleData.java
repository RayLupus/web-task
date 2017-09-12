/**
 * Project: IJtlParser File Created at 2010-7-30 $Id$ Copyright 2008 Alibaba.com Croporation Limited. All rights
 * reserved. This software is the confidential and proprietary information of Alibaba Company.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.ijtlparser;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于存放Sample的统计数据
 * 
 * @author xiaochuan.luxc
 */
public class JmeterSampleData {

    protected String                  sampleName;
    protected Map<Integer, Long>      responseTimeMap = new HashMap<Integer, Long>();
    protected Map<String, BigInteger> cacheDataMap    = new HashMap<String, BigInteger>();
    protected Map<Long, Double>       tpsMap          = new HashMap<Long, Double>();

    protected Map<String, Object>     parserResult    = new HashMap<String, Object>();

    public JmeterSampleData(String sampleName, String startTimeStamp) {
        this.sampleName = sampleName;
        cacheDataMap.put("totalCount", BigInteger.ZERO);
        cacheDataMap.put("totalTime", BigInteger.ZERO);
        cacheDataMap.put("errorTotalCount", BigInteger.ZERO);
        cacheDataMap.put("errorTotalTime", BigInteger.ZERO);
        cacheDataMap.put("lastTimestamp", BigInteger.valueOf(Long.valueOf(startTimeStamp)));
        cacheDataMap.put("currentTimestamp", BigInteger.valueOf(Long.valueOf(startTimeStamp)));
        cacheDataMap.put("currentCount", BigInteger.ZERO);
    }

    /**
     * @return the sampleName
     */
    public String getSampleName() {
        return sampleName;
    }

    /**
     * @param sampleName the sampleName to set
     */
    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    /**
     * @return the responseTimeMap
     */
    public Map<Integer, Long> getResponseTimeMap() {
        return responseTimeMap;
    }

    /**
     * @param responseTimeMap the responseTimeMap to set
     */
    public void setResponseTimeMap(Map<Integer, Long> responseTimeMap) {
        this.responseTimeMap = responseTimeMap;
    }

    /**
     * @return the cacheDataMap
     */
    public Map<String, BigInteger> getCacheDataMap() {
        return cacheDataMap;
    }

    /**
     * @param cacheDataMap the cacheDataMap to set
     */
    public void setCacheDataMap(Map<String, BigInteger> cacheDataMap) {
        this.cacheDataMap = cacheDataMap;
    }

    /**
     * @return the tpsMap
     */
    public Map<Long, Double> getTpsMap() {
        return tpsMap;
    }

    /**
     * @param tpsMap the tpsMap to set
     */
    public void setTpsMap(Map<Long, Double> tpsMap) {
        this.tpsMap = tpsMap;
    }

    /**
     * @return the parserResult
     */
    public Map<String, Object> getParserResult() {
        return parserResult;
    }

    /**
     * @param parserResult the parserResult to set
     */
    public void setParserResult(Map<String, Object> parserResult) {
        this.parserResult = parserResult;
    }

}
