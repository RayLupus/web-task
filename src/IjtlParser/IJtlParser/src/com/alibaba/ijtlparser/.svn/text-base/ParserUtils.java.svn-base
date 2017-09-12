/**
 * Project: IJtlParser File Created at 2010-7-30 $Id$ Copyright 2008 Alibaba.com Croporation Limited. All rights
 * reserved. This software is the confidential and proprietary information of Alibaba Company.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.alibaba.ijtlparser;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

/**
 * TODO Comment of ParserUtils
 * 
 * @author xiaochuan.luxc
 */
public class ParserUtils {

    // 图片属性
    public static Integer RES_PIC_WIDTH  = 900;
    public static Integer RES_PIC_HEIGHT = 400;
    public static Integer TPS_PIC_WIDTH  = 900;
    public static Integer TPS_PIC_HEIGHT = 400;
    // 柱状条参数
    public static Double  BAR_MAX_WIDTH  = 0.04;
    public static Double  BAR_MIN_LENGTH = 0.01;

    public static void doXYLineChart(String title, String xtitle, String ytitle, XYDataset xydataset,
                                     String outputFilePath) throws IOException {
        JFreeChart xyChart = ChartFactory.createXYLineChart(title, xtitle, ytitle, xydataset, PlotOrientation.VERTICAL,
                                                            false, true, false);
        XYPlot plot = (XYPlot) xyChart.getPlot();
        XYItemRenderer r = plot.getRenderer();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);
        File outputJpgFile = new File(outputFilePath);
        ChartUtilities.saveChartAsPNG(outputJpgFile, xyChart, TPS_PIC_WIDTH, TPS_PIC_HEIGHT);
    }

    public static void doXYLineTimeChart(String title, String xtitle, String ytitle, XYDataset xydataset,
                                         String outputFilePath) throws IOException {
        JFreeChart xyChart = ChartFactory.createTimeSeriesChart(title, xtitle, ytitle, xydataset, false, true, false);
        XYPlot plot = (XYPlot) xyChart.getPlot();
        XYItemRenderer r = plot.getRenderer();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);
        File outputJpgFile = new File(outputFilePath);
        ChartUtilities.saveChartAsPNG(outputJpgFile, xyChart, TPS_PIC_WIDTH, TPS_PIC_HEIGHT);
    }

    @SuppressWarnings("deprecation")
    public static void dataBarChart(String title, String xtitle, String ytitle, CategoryDataset categoryDataset,
                                    String outputFilePath) throws IOException {
        JFreeChart barChart = ChartFactory.createBarChart(title, xtitle, ytitle, categoryDataset,
                                                          PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot plot = barChart.getCategoryPlot();
        // 横坐标
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setUpperMargin(0.001);
        domainAxis.setLowerMargin(0.001);
        domainAxis.setCategoryMargin(0.01);
        plot.setDomainAxis(domainAxis);

        BarRenderer customBarRenderer = (BarRenderer) plot.getRenderer();
        customBarRenderer.setMaximumBarWidth(BAR_MAX_WIDTH);
        customBarRenderer.setMinimumBarLength(BAR_MIN_LENGTH);
        customBarRenderer.setSeriesPaint(0, Color.ORANGE);
        // 显示每个柱的数值，并修改该数值的字体属性
        customBarRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        customBarRenderer.setItemLabelPaint(Color.BLACK);// 字体为黑色
        customBarRenderer.setItemLabelsVisible(true);
        plot.setRenderer(customBarRenderer);
        File outputJpgFile = new File(outputFilePath);
        ChartUtilities.saveChartAsPNG(outputJpgFile, barChart, RES_PIC_WIDTH, RES_PIC_HEIGHT);
    }

    /**
     * @param sampleName
     * @return
     */
    public static String toPicName(String sampleName) {
        String picname = sampleName.replaceAll("/", "").replaceAll(":", "");
        if (picname.length() > 32) {
            picname = picname.substring(0, 32);
        }
        return picname;
    }
}
