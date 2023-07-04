package Model;

public class Notification implements Effect{
    private String notification;

    public typeOfEffect produceEffect(){
        return typeOfEffect.NOTIFICATION;
    }


}
