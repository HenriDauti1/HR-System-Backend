package com.hr_system.controller;

import org.springframework.context.annotation.Role;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/v1/admin")
public class MigrationController {

    @GetMapping("/download-hr-db")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadHrDatabase() {
        try {
            String tempFilePath = System.getProperty("java.io.tmpdir") + "hr_backup.dump";

            ProcessBuilder dump = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\18\\bin\\pg_dump.exe",
                    "-h", "20.56.162.184",
                    "-p", "5432",
                    "-U", "hr_admin_user",
                    "-F", "c",
                    "-b",
                    "-v",
                    "-N", "public",
                    "-f", tempFilePath,
                    "-d", "DetyreKursi"
            );

            dump.environment().put("PGPASSWORD", "DetKursi");
            dump.redirectErrorStream(true);

            Process dumpProcess = dump.start();

            int exitCode = dumpProcess.waitFor();
            if (exitCode != 0) {
                return ResponseEntity.internalServerError().body(null);
            }

            File file = new File(tempFilePath);
            InputStream is = new FileInputStream(file);
            byte[] fileBytes = is.readAllBytes();
            is.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hr_backup.dump");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
