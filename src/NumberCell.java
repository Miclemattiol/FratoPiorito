public class NumberCell extends Cell{
    private int value;

    @Override
    protected String getCellString() {
        return Integer.toString(value);
    }

    public NumberCell(int value){
        super();
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
