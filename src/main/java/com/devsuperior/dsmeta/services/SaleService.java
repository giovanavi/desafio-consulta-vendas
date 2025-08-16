package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleMinReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinReportDTO> findReport(String minDateStr, String maxDateStr, String name, Pageable pageable){
		List<LocalDate> dates = resolveDates(minDateStr, maxDateStr);

//Se o nome não for informado, considerar o texto vazio.
		String nameFilter;
		if (name == null || name.isBlank()){
			nameFilter = "";
		}else {
			nameFilter = name;
		}

		return repository.searchReport(dates.get(0), dates.get(1), nameFilter, pageable);
	}

	public List<SaleMinSummaryDTO> findSummary(String minDateStr, String maxDateStr){
		List<LocalDate> dates = resolveDates(minDateStr, maxDateStr);

		return repository.searchSummary(dates.get(0), dates.get(1));
	}

	public List<LocalDate> resolveDates(String minDateStr, String maxDateStr){
//Se a data final não for informada, considerar a data atual do sistema.
		LocalDate maxDate;
		if (maxDateStr == null || maxDateStr.isBlank()) {
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}else{
			maxDate = LocalDate.parse(maxDateStr);
		}

//Se a data inicial não for informada, considerar a data de 1 ano antes da data final.
		LocalDate minDate;
		if (minDateStr == null || minDateStr.isBlank()){
			minDate = maxDate.minusYears(1L);
		}else {
			minDate = LocalDate.parse(minDateStr);
		}

		List<LocalDate> list = new ArrayList<>();
		list.add(minDate);
		list.add(maxDate);

		return list;
	}

}
