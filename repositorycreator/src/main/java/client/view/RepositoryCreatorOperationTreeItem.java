package client.view;

import com.catchex.models.Operation;

import java.io.Serializable;

public class RepositoryCreatorOperationTreeItem extends RepositoryCreatorTreeItem implements Serializable {
    public RepositoryCreatorOperationTreeItem(){
        super();
    }

    public RepositoryCreatorOperationTreeItem( Operation operation ) {
        super(operation);
    }

    public void setDesc( String desc ) {
        this.desc.set(desc);
    }

    public Operation getOperation(){
        return this.operation;
    }
}
