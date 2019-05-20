package Logic.Container;

import Logic.Exeption.MoveExeption;
import Logic.Exeption.RemoveExeption;

public class Container<T> {

    public void add(T o){
        //write()
    }
    public void remove(T o) throws RemoveExeption {

    }
    public void remove(int index) throws RemoveExeption {
        try {

        }catch (IndexOutOfBoundsException e){
            throw new RemoveExeption();
        }
    }
    public boolean search(T o){
        return false;
    }
    public void move(Container source, Container destination) throws MoveExeption{
        try {
            destination.add(source);
        }catch (ClassCastException e){
            throw new MoveExeption("Unsupported type");
        }catch (NullPointerException e){
            throw new MoveExeption("Element does not exist");
        }catch (IllegalArgumentException e){
            throw new MoveExeption("Argument prevented move");
        }
        //loeschen noch machen
    }
}
