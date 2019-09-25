package lab1.model;

/**
 * Class, that represents characteristics of SmartPhone
 * Can be created using pattern Builder
 * Example: SmartPhone smartPhone = SmartPhone.newBuilder()
 *                 .setColor(new Color(255, 0, 0))
 *                 .setRam(2048)
 *                 .build();
 */
public class SmartPhone {

    // TODO: Конкретно вибрати щось зі смартфонами. (Які саме поля і методи краще в нього, чи треба наслідування)
    // TODO: Processor, Graphic Processor, Camera, Resolution, OS, Battery, Inheritance?

    // TODO: equals(), hashCode(), toString(), some collection

    // мережа магазинів, скільки старих і шукати
    // назва , дата виробництва, color - enum, пам'ять, getDescription, ціна, діагональ, OS
    // сортування по ціні, по даті, різних порядках, по заданому критерію виводимо список наших телефонів (колір, память від до, конкрет, модель)
    // Map або Class

    public enum Color {
        BLACK,
        WHITE,
        BLUE,
        RED,
        GOLD
    }

    private String name;
    private Color color;
    private int ram;

    private SmartPhone() {
        // Private constructor to deny creating new instance outside by constructor
    }

    public Color getColor() {
        return color;
    }

    /**
     *
     * @return RAM capacity in MegaBytes
     */
    public int getRam() {
        return ram;
    }

    public static class Builder {

        SmartPhone smartPhone;

        public Builder() {
            smartPhone = new SmartPhone();
        }

        public Builder setColor(Color color) {
            smartPhone.color = color;
            return this;
        }

        /**
         *
         * @param ram capacity in MegaBytes
         */
        public Builder setRam(int ram) {
            smartPhone.ram = ram;
            return this;
        }

        /**
         * Use it after calling all setters
         * @return instance of SmartPhone
         */
        public SmartPhone build() {
            return smartPhone;
        }

    }

}