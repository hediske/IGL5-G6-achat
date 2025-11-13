package tn.esprit.rh.achat.controllers;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.services.IStockService;
import tn.esprit.rh.achat.dtos.StockDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Gestion des stocks")
@RequestMapping("/stock")
@CrossOrigin("*")
public class StockRestController {

	@Autowired
	IStockService stockService;

	// http://localhost:8089/SpringMVC/stock/retrieve-all-stocks
	@GetMapping("/retrieve-all-stocks")
	@ResponseBody
	public List<StockDTO> getStocks() {
		return stockService.retrieveAllStocks().stream().map(this::toDto).collect(Collectors.toList());
	}

	// http://localhost:8089/SpringMVC/stock/retrieve-stock/8
	@GetMapping("/retrieve-stock/{stock-id}")
	@ResponseBody
	public StockDTO retrieveStock(@PathVariable("stock-id") Long stockId) {
		return toDto(stockService.retrieveStock(stockId));
	}

	// http://localhost:8089/SpringMVC/stock/add-stock
	@PostMapping("/add-stock")
	@ResponseBody
	public StockDTO addStock(@RequestBody StockDTO s) {
		return toDto(stockService.addStock(toEntity(s)));
	}

	// http://localhost:8089/SpringMVC/stock/remove-stock/{stock-id}
	@DeleteMapping("/remove-stock/{stock-id}")
	@ResponseBody
	public void removeStock(@PathVariable("stock-id") Long stockId) {
		stockService.deleteStock(stockId);
	}

	// http://localhost:8089/SpringMVC/stock/modify-stock
	@PutMapping("/modify-stock")
	@ResponseBody
	public StockDTO modifyStock(@RequestBody StockDTO stock) {
		return toDto(stockService.updateStock(toEntity(stock)));
	}

	/*
	 * Spring Scheduler : Comparer QteMin tolérée (à ne pas dépasser) avec
	 * Quantité du stock et afficher sur console la liste des produits inférieurs
	 * au stock. La fonction schedulée doit obligatoirement être sans paramètres
	 * et sans retour (void)
	 */
	// @Scheduled(fixedRate = 60000)
	// @Scheduled(fixedDelay = 60000)
	// @Scheduled(cron = "*/60 * * * * *")
	// @GetMapping("/retrieveStatusStock")
	// @ResponseBody
	// public void retrieveStatusStock() {
	//     stockService.retrieveStatusStock();
	// }

	private StockDTO toDto(Stock e) {
		if (e == null) return null;
		return new StockDTO(e.getIdStock(), e.getQte(), e.getQteMin());
	}

	private Stock toEntity(StockDTO d) {
		if (d == null) return null;
		Stock s = new Stock();
		s.setIdStock(d.getIdStock());
		s.setQte(d.getQteStock());
		s.setQteMin(d.getQteMin());
		return s;
	}
}