package GUI;

import Model.Event;
import Model.InfiniteEvent;
import Model.Task;
import Model.Visitor;
import javafx.scene.control.Label;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class ViewVisitor implements Visitor {
    abstract public void  visitEvent(Event event);
    abstract public void visitInfiniteEvent(InfiniteEvent event);
    abstract public void visitTask(Task task);


    public static void setLabelDateWithFormat(Label label, String format, LocalDateTime date){
        label.setText(date.format(DateTimeFormatter.ofPattern(format).withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
    }

    public static void setLabelDateWithFormat(Label label, String format1, String format2, String separator, LocalDateTime date1, LocalDateTime date2){
        label.setText(date1.format(DateTimeFormatter.ofPattern(format1).withLocale(new Locale("es"))).toUpperCase(Locale.ROOT)
                + separator + date2.format(DateTimeFormatter.ofPattern(format2).withLocale(new Locale("es"))).toUpperCase(Locale.ROOT));
    }

}

