package traffic_id.demo.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traffic_id.demo.model.Application;
import traffic_id.demo.repository.ApplicationRepository;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelReportService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public void generateExcelReport(HttpServletResponse response) throws IOException {
        List<Application> applications = applicationRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet mainSheet =  workbook.createSheet("Заявления");

        String[] headers = {
                "Название",
                "Информация",
                "Время поступления",
                "Время нарушения",
                "Район",
                "Адрес",
                "Статус",
                "Модератор",
                "Комментарий модератора",
                "Время проверки"
        };

        HSSFRow rowHeadersColumns = mainSheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = rowHeadersColumns.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < applications.size(); i++) {
            HSSFRow row = mainSheet.createRow(i + 1);
            Application application = applications.get(i);

            row.createCell(0).setCellValue(application.getTitle());
            row.createCell(1).setCellValue(application.getInformation());
            row.createCell(2).setCellValue(application.getDateArrive());
            row.createCell(3).setCellValue(application.getDateViolation());
            row.createCell(4).setCellValue(application.getDistrict().getDistrictName());
            row.createCell(5).setCellValue(application.getAddress());
            row.createCell(6).setCellValue(application.getStatus().getStatusName());
            if (application.getModerator() != null)
                row.createCell(7).setCellValue(application.getModerator().getUser().getName());
            else
                row.createCell(7).setCellValue("");
            row.createCell(8).setCellValue(application.getCommentary());
            row.createCell(9).setCellValue(application.getDateCheck());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
