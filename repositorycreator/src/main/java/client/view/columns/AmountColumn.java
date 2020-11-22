package client.view.columns;

import client.control.RepositoryCreatorDialogController;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class AmountColumn extends RepositoryColumn<String> {
    public AmountColumn()  {
        super("Amount");
    }

    @Override
    void init() {
        super.init();
        setDefaultWidth();
        setComparator();
    }

    @Override
    void setCellValueFactory() {
        setCellValueFactory(
                p -> {
                    Double amount = p.getValue().getValue().getAmount();
                    if (amount.equals(Double.NaN))  return new ReadOnlyObjectWrapper<>("");
                    return new ReadOnlyObjectWrapper<>(String. format("%.2f", amount));
                });
    }

    void setDefaultWidth() {
        setPrefWidth(100);
    }

    private void setComparator(){
        setComparator(( s, t1 ) -> {
            if (s.isEmpty() || t1.isEmpty()) {
                return 0;
            }
            Double val1 = Double.parseDouble(s);
            Double val2 = Double.parseDouble(t1);
            return val1.compareTo(val2);
        });
    }
}
