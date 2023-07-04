package Model;

public interface Effect {
    public typeOfEffect produceEffect();

    public enum typeOfEffect{
        NOTIFICATION,
        EMAIL,
        SOUND,
    }

}
