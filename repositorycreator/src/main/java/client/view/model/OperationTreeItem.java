package client.view.model;

import com.catchex.models.Operation;

import java.io.Serializable;

public class OperationTreeItem extends AbstractTreeItem implements Serializable {
    public OperationTreeItem(){
        super();
    }

    public OperationTreeItem( Operation operation ) {
        super(operation);
    }

    public void setDesc( String desc ) {
        this.desc.set(desc);
    }

    public Operation getOperation(){
        return this.operation;
    }
}
