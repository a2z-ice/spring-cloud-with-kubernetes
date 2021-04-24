package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WriterController {

	@GetMapping(value = "write/{message}")
	public ResponseEntity<Greeting> write(@PathVariable SimpleObj message) {

		System.out.println("called");

		try {
			
			
			FileWriter myWriter = new FileWriter("test/filename.txt");
			myWriter.write("Files in Java might be tricky, but it is fun enough! :" + message.getUuid());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return ResponseEntity.ok(new Greeting(123L, "hello"));
	}
}
