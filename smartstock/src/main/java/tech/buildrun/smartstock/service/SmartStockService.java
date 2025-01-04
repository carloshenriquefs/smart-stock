package tech.buildrun.smartstock.service;

import org.springframework.stereotype.Service;
import tech.buildrun.smartstock.domain.CsvStockItem;

import java.io.IOException;

@Service
public class SmartStockService {

    private final ReportService reportService;

    public SmartStockService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void start(String reporthPath) {

        try {
            var items = reportService.readStockReport(reporthPath);

            items.forEach(item -> {

                if (item.getQuantity() < item.getReorderThreshold()) {

                    var reorderQuantity = calculateReorderQuantity(item);
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
