package Model;

public interface Visitor {
     void visitEvent(Event event);
     void visitInfiniteEvent(InfiniteEvent event);
    void visitTask(Task task);

}
