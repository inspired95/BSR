package client;

import com.catchex.models.Operation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Repository implements Serializable {
    private Set<Operation> operations;

    public Repository(){
        operations = new HashSet<>();
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations( Set<Operation> operations ) {
        this.operations = this.operations;
    }

    public void addOperation(Operation operation){
        this.operations.add(operation);
    }

    public void addOperations(Set<Operation> operations){
        this.operations.addAll(operations);
    }
}
