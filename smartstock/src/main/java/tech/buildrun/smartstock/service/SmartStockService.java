package tech.buildrun.smartstock.service;

import org.springframework.stereotype.Service;
import tech.buildrun.smartstock.domain.CsvStockItem;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;

    public SmartStockService(ReportService reportService, PurchaseSectorService purchaseSectorService) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
    }

    public void start(String reporthPath) {

        try {
            var items = reportService.readStockReport(reporthPath);

            items.forEach(item -> {

                if (item.getQuantity() < item.getReorderThreshold()) {

                    var reorderQuantity = calculateReorderQuantity(item);

                    purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold() * 0.2));
    }
}
