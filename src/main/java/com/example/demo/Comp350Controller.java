// src/main/java/com/example/demo/Comp350Controller.java
package com.example.demo;

import edu.gcc.comp350.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Comp350Controller {

    @GetMapping("/runFunction")
    public String runFunction() {
        // Call your function from the edu.gcc.comp350 package
        return "Function has been run";//Main.main();
    }
}