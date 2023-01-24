package com.playwrighthybrid.listners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.playwrighthybrid.extentreports.MyExtentReport;
import com.playwrighthybrid.libraries.PlaywrightFactory;
import com.playwrighthybrid.utils.DataHandlers;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        //BaseClass base = new BaseClass();
        try {
            MyExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                    // .assignCategory(base.getPlatformName())
                     //.assignDevice(base.getDeviceName())
                    .assignAuthor("Automation Team");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        MyExtentReport.getTest().log(Status.PASS, "Test Passed !!!");    }

    @Override
    public void onTestFailure(ITestResult result) {
        // To read data from testng.xml
        Map<String, String> params = new HashMap<String, String>();
        params = result.getTestContext().getCurrentXmlTest().getAllParameters();

        String imagePath = "Screenshots" + File.separator +
                //params.get("platformName") + "_" + params.get("browser") + File.separator +
                new DataHandlers().getDateTime() + File.separator
                + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        MyExtentReport.getTest().fail("Test Failed",
             MediaEntityBuilder.createScreenCaptureFromBase64String(new PlaywrightFactory().takeScreenshot(completeImagePath)).build());
        MyExtentReport.getTest().log(Status.INFO, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        MyExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //  ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        //ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            MyExtentReport.getReporter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}