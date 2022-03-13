package models;

import java.util.List;

/**
 * Лекарство в аптеке.
 */
public class Medicine {
    public int id;
    public String name;
    public int quantity;
    public int unitPrice;

    /**
     * Конкструктор.
     * @param id ID
     * @param name Имя
     * @param quantity Количество на складе
     * @param unitPrice Цена единицы
     */
    public Medicine(int id, String name, int quantity, int unitPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Возвращает список строковых значений полей класса.
     * @return Список строк
     */
    public List<String> fieldsToStringList() {
        return List.of(
                String.valueOf(this.id),
                this.name,
                String.valueOf(this.quantity),
                String.valueOf(this.unitPrice)
        );
    }
}