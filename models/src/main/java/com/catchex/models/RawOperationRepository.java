package com.catchex.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RawOperationRepository implements Serializable {
    private Map<String, Set<RawOperation>> rawOperations;

    public RawOperationRepository(){
        rawOperations = new HashMap<>();
    }

    public Map<String, Set<RawOperation>> getRawOperations() {
        return rawOperations;
    }

    public void setRawOperations( Map<String, Set<RawOperation>> rawOperations ) {
        this.rawOperations = rawOperations;
    }
}
