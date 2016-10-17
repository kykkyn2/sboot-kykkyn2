package com.adwitt.ppp.controller;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Created by kykkyn2 on 2016-09-30.
 */
@RestController
public class ExportController {

	ObjectMapper JACKSON = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@RequestMapping(value = "/csv", method = RequestMethod.GET)
	public ResponseEntity getExportCsv() {

		List<String> aaa = new ArrayList<>();
		aaa.add("11111");
		aaa.add("22222");
		aaa.add("33333");

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=MS949");
		header.add("Content-Disposition", "attachment; filename=\"" + "total.csv" + "\"");

		return new ResponseEntity<>(String.join(",", aaa), header, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/csv2", method = RequestMethod.GET)
	public ResponseEntity getExportCsv2(HttpServletResponse response) throws IOException {

		List<String> aaa = new ArrayList<>();
		aaa.add("11111");
		aaa.add("22222");
		aaa.add("33333");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		out.write(String.join(",", aaa).getBytes());
		byte[] bytes = baos.toByteArray();

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=MS949");
		header.add("Content-Disposition", "attachment; filename=\"" + "totalzzz.csv" + "\"");

		return new ResponseEntity<>(bytes, header, HttpStatus.CREATED);


	}


	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public ResponseEntity getExportJson() throws IOException {

		List<String> list = new ArrayList<>();
		list.add("ㅂㅂㅂ");
		list.add("ㅈㅈㅈ");
		list.add("ㅂㅂㅂ");
		list.add("ㅈㅈㅈ");

		byte[] bytes = JACKSON.writeValueAsBytes(list);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json; charset=UTF-8");
		header.add("Content-Disposition", "attachment; filename=\"" + "totalzzz.json" + "\"");

		return new ResponseEntity<>(bytes, header, HttpStatus.CREATED);

	}

	@ResponseBody
	@RequestMapping(value = "/excel", method = RequestMethod.GET) // , produces = "application/vnd.ms-excel"
	public ResponseEntity getExportExcel() throws IOException {
		//		response.setHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
		//		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=total.xls");
		Workbook workbook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet();


		Row row = sheet.createRow(0);

		row.createCell(0).setCellValue("바보");
		row.createCell(1).setCellValue("멍충이");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		byte[] result = baos.toByteArray();
		baos.flush();
		baos.close();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
		headers.setContentDispositionFormData("attachment", "filename=total.xls");
		return new ResponseEntity<>(result, headers, HttpStatus.OK);
	}


	@RequestMapping(value = "/excel2", method = RequestMethod.GET) // , produces = "application/vnd.ms-excel"
	public ResponseEntity getExportExcel2() throws IOException {

		Workbook workbook = new SXSSFWorkbook();
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("SOMESHEET");


		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("11111111111111111111");
		
		Row row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("22222222222222222222");


		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		byte[] result = baos.toByteArray();
		baos.flush();
		baos.close();

		sheet.untrackAllColumnsForAutoSizing();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/vnd.ms-excel");
		headers.add("Content-Disposition", "attachment; filename=total.xls");

		return new ResponseEntity<>(result, headers, HttpStatus.OK);

	}

}
