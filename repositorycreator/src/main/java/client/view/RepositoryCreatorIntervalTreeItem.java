package client.view;

import com.catchex.models.Operation;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class RepositoryCreatorIntervalTreeItem extends RepositoryCreatorTreeItem {
    public RepositoryCreatorIntervalTreeItem( LocalDate date ) {
        super(Operation.DUMMY_OPERATION);
        this.date = date;
        overrideDefaultNanValue();
    }

    private void overrideDefaultNanValue() {
        this.amount = new SimpleDoubleProperty(0.0);
    }
}
