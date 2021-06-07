public class BombCell extends Cell {
    @Override
    public boolean open() {
        boolean ret =  super.open();

        return ret;
    }

    @Override
    protected String getCellString() {
        return "B";
    }
}
