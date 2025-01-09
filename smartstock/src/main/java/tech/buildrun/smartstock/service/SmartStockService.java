package tech.buildrun.smartstock.service;

import org.springframework.stereotype.Service;
import tech.buildrun.smartstock.domain.CsvStockItem;
import tech.buildrun.smartstock.entity.PurchaseRequestEntity;
import tech.buildrun.smartstock.repository.PurchaseRequestRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SmartStockService {

    private final ReportService reportService;
    private final PurchaseSectorService purchaseSectorService;
    private final PurchaseRequestRepository purchaseRequestRepository;

    public SmartStockService(ReportService reportService, PurchaseSectorService purchaseSectorService, PurchaseRequestRepository purchaseRequestRepository) {
        this.reportService = reportService;
        this.purchaseSectorService = purchaseSectorService;
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public void start(String reporthPath) {

        try {
            var items = reportService.readStockReport(reporthPath);

            items.forEach(item -> {

                if (item.getQuantity() < item.getReorderThreshold()) {

                    var reorderQuantity = calculateReorderQuantity(item);

                    var purchasedWithSuccess = purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);

                    persist(item, reorderQuantity, purchasedWithSuccess);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(CsvStockItem item,
                         Integer reorderQuantity,
                         boolean purchasedWithSuccess) {

        var entity = new PurchaseRequestEntity();
        entity.setItemId(item.getItemId());
        entity.setItemName(item.getItemName());
        entity.setQuantityOnStock(item.getQuantity());
        entity.setReorderThreshold(item.getReorderThreshold());
        entity.setSupplierName(item.getSupplierName());
        entity.setLastStockUpdateTime(LocalDateTime.parse(item.getLastStockUpdateTime()));

        entity.setPurchaseQuantity(reorderQuantity);
        entity.setPurchasedWithSuccess(purchasedWithSuccess);
        entity.setPurchaseDateTime(LocalDateTime.now());

        purchaseRequestRepository.save(entity);
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() + ((int) Math.ceil(item.getReorderThreshold() * 0.2));
    }
}
