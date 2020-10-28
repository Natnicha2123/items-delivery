package th.ku.itemsdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import th.ku.itemsdelivery.model.ItemImport;

@Repository
public interface ItemImportRepository extends JpaRepository<ItemImport, Integer> {
}
