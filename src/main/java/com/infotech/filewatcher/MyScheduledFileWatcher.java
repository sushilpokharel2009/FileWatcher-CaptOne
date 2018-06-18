package com.infotech.filewatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.FileCopyUtils;

import static java.nio.file.StandardWatchEventKinds.*;

public class MyScheduledFileWatcher {
	
	

	public static void main(String[] args) {

		MyScheduledFileWatcher myScheduledFileWatcher = new MyScheduledFileWatcher();
		myScheduledFileWatcher.scheduleFixedRateTask();
	}
	
	@Scheduled(fixedRate = 10000)
	public void scheduleFixedRateTask() {
	    System.out.println(
	      "Fixed rate task - " + System.currentTimeMillis() / 1000);
	    
	    String sourcePath = "/Users/Shubhashree11/Desktopestsource/t/";
		String destinationPath = "/Users/Shubhashree11/Desktop/testdestination/";
	    
		
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			
			Path dir = Paths.get(sourcePath);
			
			dir.register(watcher, ENTRY_CREATE);
			
				WatchKey key = null;
				
				try {
					key = watcher.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				 for (WatchEvent<?> event : key.pollEvents()) {
	                    WatchEvent.Kind<?> kind = event.kind();
	                    
	                    @SuppressWarnings("unchecked")
	                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
	                    Path fileName = ev.context();
	                    
	                    if(kind == ENTRY_CREATE) {
	                    	
	                    	     SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	                    	     
	                    	     Date date = new Date();
	                    	     
	                    	     String dt = sd.format(date); // 15/09/1982 13:30:23
	                    	     
	                    	     // 15_09_1982_13_30_23
	                    	     dt = dt.replace("/", "_").replace(" ", "_").replace(":", "_");
	                    	     
	                    	     File destFile = new File(destinationPath + dt);
	                    	     
	                    	     if(!destFile.exists()) {
	                    	    	 destFile.mkdirs();
	                    	     }
	                    	     
	                    	     File srcFile = new File(sourcePath);
	                    	     
	                    	     FileUtils.copyDirectory(srcFile, destFile);
	                    	     
	                    }
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
