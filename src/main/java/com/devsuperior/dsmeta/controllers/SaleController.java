package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinReportDTO>> getReport(
			@RequestParam(value = "minDate", required = false)String minDate,
			@RequestParam(value = "maxDate", required = false)String maxDate,
			@RequestParam(value = "name", required = false)String name,
			Pageable pageable) {

		Page<SaleMinReportDTO> result = service.findReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleMinSummaryDTO>> getSummary(
			@RequestParam(value = "minDate", required = false)String minDate,
			@RequestParam(value = "maxDate", required = false)String maxDate) {
		List<SaleMinSummaryDTO> result = service.findSummary(minDate, maxDate);
		return ResponseEntity.ok(result);
	}
}
