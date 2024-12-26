package traffic_id.demo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import traffic_id.demo.service.ExcelReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ExcelReportController {

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws Exception {
        String currentDateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "peoples-inspector_" + currentDateAndTime + ".xls";

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        excelReportService.generateExcelReport(response);
    }
}
