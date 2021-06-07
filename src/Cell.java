import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Cell {

    public static final String CELLMARKED = "marked";
    public static final String CELLOPENED = "opened";
    protected boolean isOpened = false;
    protected boolean isMarked = false;

    protected PropertyChangeSupport pcs;

    public Cell(){
        pcs = new PropertyChangeSupport(this);
    }

    public boolean open(){
        boolean ret = false;
        if(!isOpened && !isMarked){
            ret = true;
            pcs.firePropertyChange(CELLOPENED, false, true);
            this.isOpened = true;
        }
        return ret;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void mark() {
        boolean oldMark = this.isMarked;
        this.isMarked = !this.isMarked;
        pcs.firePropertyChange(CELLMARKED, oldMark, this.isMarked);
    }

    public void registerPropertyChangeListener(PropertyChangeListener pcl){
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl){
        pcs.removePropertyChangeListener(pcl);
    }

    public String cellString(){
        String ret = "*";
        if(isOpened){
        //if(true){
            ret = getCellString();
        }
        return ret;
    }



    protected abstract String getCellString();
}
