package tech.buildrun.smartstock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.buildrun.smartstock.entity.PurchaseRequestEntity;

public interface PurchaseRequestRepository extends MongoRepository<PurchaseRequestEntity, String> {
}
