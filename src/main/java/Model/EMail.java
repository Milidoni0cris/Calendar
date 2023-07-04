package Model;

public class EMail implements Effect {
    private String mail;

    public typeOfEffect produceEffect(){
        return typeOfEffect.EMAIL;
    }

}

