package client.view.model;

import com.catchex.models.Operation;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractTreeItem implements Serializable {

    protected Operation operation;
    protected LocalDate date;
    protected SimpleStringProperty ID;
    protected SimpleStringProperty type;
    protected SimpleDoubleProperty amount;
    protected SimpleStringProperty desc;
    protected SimpleStringProperty category;
    protected SimpleStringProperty fileName;
    protected SimpleStringProperty bankName;

    public AbstractTreeItem(){

    }

    public AbstractTreeItem( Operation operation )
    {
        this.operation = operation;
        this.date = operation.getRawOperation().getDate();
        this.ID = new SimpleStringProperty(operation.getRawOperation().getID());
        this.type = new SimpleStringProperty(operation.getType().toString());
        this.amount = new SimpleDoubleProperty(operation.getRawOperation().getAmount());
        this.desc = new SimpleStringProperty(operation.getRawOperation().getDesc());
        this.category = new SimpleStringProperty(operation.getCategory().getCategoryName());
        this.fileName = new SimpleStringProperty(operation.getRawOperation().getFileName());
        this.bankName = new SimpleStringProperty(operation.getRawOperation().getBank());
    }

    public LocalDate getDate() {
        return date;
    }

    public String getID() {
        return ID.get();
    }

    public String getType() {
        return type.get();
    }


    public double getAmount() {
        return amount.get();
    }

    public String getDesc() {
        return desc.get();
    }

    public String getCategory() {
        return category.get();
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public String getBankName() {
        return bankName.get();
    }

    public SimpleStringProperty bankNameProperty() {
        return bankName;
    }

    public void increaseAmount( double amount ) {
        Double currentValue = this.amount.getValue();
        this.amount = new SimpleDoubleProperty(currentValue + amount);
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof AbstractTreeItem)) return false;
        AbstractTreeItem that = (AbstractTreeItem) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(ID, that.ID) &&
                Objects.equals(type, that.type) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, ID, type, amount, desc, category);
    }
}
