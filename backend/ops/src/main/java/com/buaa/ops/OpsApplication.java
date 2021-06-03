package com.buaa.ops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

@SpringBootApplication
public class OpsApplication {

	private static String logsPath;

	@Value("${file.logs-path}")
	public void setLogPath(String value) {
		logsPath = value;
	}

	public static void main(String[] args) {
		SpringApplication.run(OpsApplication.class, args);
		try {
			File logDir = new File(logsPath);
			if (!logDir.exists() && !logDir.mkdirs())
				throw new IOException();
			PrintStream ps = new PrintStream(new FileOutputStream(logsPath + "/log.txt"));
			System.setErr(ps);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
