package services;

import models.Medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicineService {
    /**
     * Список лекарств.
     */
    private final List<Medicine> medicines;

    /**
     * Конкструктор.
     */
    public MedicineService() {
        super();
        medicines = new ArrayList<>();
        medicines.add(new Medicine(0, "AAA", 10, 100));
        medicines.add(new Medicine(1, "BBB", 32, 200));
        medicines.add(new Medicine(2, "CCC", 1, 220));
        medicines.add(new Medicine(3, "DDD", 56, 1340));
        medicines.add(new Medicine(4, "EEE", 23, 50));
    }

    /**
     * Возвращает список всех доступных лекарств.
     */
    public List<Medicine> findAll() {
        return medicines;
    }

    /**
     * Возвращает список лекарств, у которых цена за штуку меньше `maxPrice`.
     * @param maxPrice Максимальная цена за штуку.
     */
    public List<Medicine> findByMaxPrice(int maxPrice) {
        return medicines.stream().filter(m -> m.unitPrice < maxPrice).collect(Collectors.toList());
    }
}