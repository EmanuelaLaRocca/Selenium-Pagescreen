package org.example;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
    //
    public static String default_path = "/Users/" + System.getProperty("user.name")+ "/Documents/Pagescreens";//Documents if pc is in english version

    public static void main(String[] args) throws IOException {



        String url = "https://www.google.com";//as default
        String dest = default_path;

        //collect args
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--url")&& args[i+1].contains("www.")&& args[i+1].contains("https://")) {
                // if "--url"--> read the next as the url
                url = args[i + 1];
            } else if (args[i].equals("--dest")&& args[i + 1].contains("/")) {
                //if "--dest"--> read the next as the destination path
                dest= args[i + 1];
            }
        }

        Path path = Paths.get(dest);
        //if the directory doesn't exist, it will be created
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Directory is created!");
            } catch (IOException e) {
                System.err.println("Failed to create directory! " + e.getMessage());
            }
        }

        //open ChromeDriver
        WebDriver driver=new ChromeDriver(); //Initiating chromedriver
        driver.get(url);


        //maximize window
        driver.manage().window().maximize();

        //take and save screenshot
        try {
            File myScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE );//WebDriver Exception
            File mydestFile = new File(path+"/screenshot.png"); // Append a file name to the destination path
            FileUtils.copyFile(myScreen, mydestFile);
            System.out.println("Screenshot saved to: " + mydestFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error capturing or saving screenshot: " + e.getMessage());
        } finally {
            driver.quit();
        }

        System.exit(0);

    }

}