package com.healthCare.healthCareDataBase.Controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthCare.healthCareDataBase.Model.MedicamentStock;
import com.healthCare.healthCareDataBase.Repository.MedicamentStockRepository;

@CrossOrigin()
@RestController
@RequestMapping(value="/api/uploadExcelFile")
public class UploadPharmacyMedicamentsFile {
	
	@Autowired
	MedicamentStockRepository medicamentStockRepository;
	
	@PostMapping(value="/import/{id}")
	public ResponseEntity<List<MedicamentStock>> importExcelFile(@RequestParam("file") MultipartFile files,@PathVariable("id") Long id) throws IOException {
        HttpStatus status = HttpStatus.OK;

        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
            	MedicamentStock med = new MedicamentStock();

                XSSFRow row = worksheet.getRow(index);

                if(row.getCell(0).getStringCellValue() != null) {
                	med.setMedicamentName(row.getCell(0).getStringCellValue());
                    med.setPharmacyId(id);
                    medicamentStockRepository.save(med);
                }
            }
        }

        return new ResponseEntity<>(status);
    }

}
