package com.pga.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ReadFiles {

	public static String read(String path_file) throws IOException {
		
		Resource resource = new ClassPathResource(path_file);
		InputStream in = resource.getInputStream();
		
		String email_base = IOUtils.toString(in,"UTF-8");
		
		return email_base;
	}
	
//	public static File getFile(String C) throws IOException {
//
//		Resource resource = new ClassPathResource(path_file);
//		InputStream in = resource.getInputStream();
//		return null;
//	}
	
    public static File getFile(String path_file) throws IOException {
    	
    	Resource resource = new ClassPathResource(path_file);
    	InputStream in = resource.getInputStream();
        final String PREFIX = "logo";
        final String SUFFIX = ".png";
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }
	
    public static URL getURL(String path_file) throws IOException {
    	
    	Resource resource = new ClassPathResource(path_file);

        return resource.getURL();
    }
	
}
