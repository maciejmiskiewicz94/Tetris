package processing;

/**
 * Created by Borys on 11/15/16.
 */
public class ProcessingUnit extends Thread{
    private int id;
    public ProcessingUnit(int id){
        this.id=id;
    }
    public void run() {
        System.out.println("ExtendsThread : id : " + id);
    }
}
