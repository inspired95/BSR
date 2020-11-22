package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import client.view.model.IntervalTreeItem;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TreeTableColumn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class IntervalColumn extends RepositoryColumn<String>{

    private DateTimeFormatter intervalTreeItemFormatter;

    public IntervalColumn( DateTimeFormatter intervalTreeItemFormatter ) {
        super("Interval");
        this.intervalTreeItemFormatter = intervalTreeItemFormatter;
    }

    @Override
    void init() {
        super.init();
        setPrefWidth(150);
        setComparator();
        setSortType(TreeTableColumn.SortType.ASCENDING);
    }

    @Override
    void setCellValueFactory() {
        setCellValueFactory(
                p -> {
                    if (p.getValue().getValue() instanceof IntervalTreeItem){
                        LocalDate date = p.getValue().getValue().getDate();
                        return new ReadOnlyObjectWrapper<>(date.format(intervalTreeItemFormatter));
                    }
                    return new ReadOnlyObjectWrapper<>("");
                });
    }

    private void setComparator(){
        setComparator(( s, t1 ) -> {
            if (s.isEmpty() || t1.isEmpty()) {
                return 0;
            }
            LocalDate localDate1 = LocalDate.parse("01 " + s, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US));
            LocalDate localDate2 = LocalDate.parse("01 " + t1, DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US));
            return localDate1.compareTo(localDate2);
        });
    }
}
