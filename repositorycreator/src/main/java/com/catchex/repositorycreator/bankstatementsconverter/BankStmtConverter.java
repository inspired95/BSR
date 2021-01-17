package com.catchex.repositorycreator.bankstatementsconverter;

import com.catchex.models.RawOperation;

import java.util.List;


public interface BankStmtConverter
{
    List<RawOperation> convert( String name, String statement );
}
